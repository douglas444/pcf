package br.ufu.facom.pcf.gui.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class DependencyFinder {

    public static Set<Class<?>> getDependencies(final Class<?> classToCheck)
            throws IOException, ClassNotFoundException {
        Class<?> adjustedClassToCheck = adjustSourceClassIfArray(classToCheck);
        if (adjustedClassToCheck.isPrimitive()) {
            return Collections.emptySet();
        }
        return mapClassNamesToClasses(
                adjustedClassToCheck,
                getDependenciesFromClassBytes(readClassBytes(adjustedClassToCheck)));
    }

    private static Class<?> adjustSourceClassIfArray(final Class<?> sourceClass) {
        Class<?> adjustedSourceClass = sourceClass;
        while (adjustedSourceClass.isArray()) {
            adjustedSourceClass = sourceClass.getComponentType();
        }
        return adjustedSourceClass;
    }

    private static Set<Class<?>> mapClassNamesToClasses(Class<?> from,
                                                        Set<String> names) throws ClassNotFoundException {
        ClassLoader cl = from.getClassLoader();
        Set<Class<?>> classes = new HashSet<>(names.size());

        for (String name : names) {
            classes.add(Class.forName(name, false, cl));
        }
        classes.remove(from);// remove self-reference
        return classes;
    }

    private static ByteBuffer readClassBytes(Class<?> from) throws IOException {
        Buffer readBuf = new Buffer();
        try (InputStream is = from.getResourceAsStream(from.getSimpleName()
                + ".class")) {
            int byteCountFromLastRead = 0;
            do {
                readBuf.read += byteCountFromLastRead;
                adjustBufferSize(readBuf, is);
                byteCountFromLastRead = is.read(readBuf.buf, readBuf.read,
                        readBuf.buf.length - readBuf.read);
            } while (byteCountFromLastRead > 0);
        }
        return readBuf.toByteBuffer();
    }

    private static void adjustBufferSize(Buffer readBuf, InputStream is)
            throws IOException {
        int bufferSize = Math.max(is.available() + 100, 100);
        if (readBuf.buf == null) {
            readBuf.buf = new byte[bufferSize];
        } else if (readBuf.buf.length - readBuf.read < bufferSize) {
            System.arraycopy(readBuf.buf, 0,
                    readBuf.buf = new byte[readBuf.read + bufferSize], 0,
                    readBuf.read);
        }
    }

    private static Set<String> getDependenciesFromClassBytes(ByteBuffer readBuffer) {
        verifyMagicFileTypeHeader(readBuffer);
        final int constantPoolItemCount = getConstantPoolItemCount(readBuffer);
        ConstantPoolItemFlags flags = new ConstantPoolItemFlags(constantPoolItemCount);
        flagConstantPoolItemsAsDependencies(readBuffer, constantPoolItemCount, flags);
        return extractClassNamesFromConstantsBasedOnFlags(readBuffer,
                constantPoolItemCount, flags);
    }

    private static void flagConstantPoolItemsAsDependencies(ByteBuffer readBuffer,
                                                            final int constantPoolItemCount, ConstantPoolItemFlags flags) {
        for (int c = 1; c < constantPoolItemCount; c++) {
            c = readOneConstantPoolItemAndSetFlagIfClassOrNamedType(readBuffer,
                    flags, c);
        }
        skipPastAccessFlagsThisClassAndSuperClass(readBuffer);
        skipInterfaces(readBuffer);
        flagFieldsAndMethodsAsNamedTypes(readBuffer, flags.isNamedType);
    }

    private static int getConstantPoolItemCount(ByteBuffer readBuffer) {
        setCursorToConstantPoolCountPosition(readBuffer);
        return readBuffer.getChar();
    }

    private static void skipInterfaces(ByteBuffer readBuffer) {
        readBuffer.position(readBuffer.getChar() * 2 + readBuffer.position());
    }

    private static void skipPastAccessFlagsThisClassAndSuperClass(
            ByteBuffer readBuffer) {
        skipBytes(readBuffer, 6);
    }

    private static HashSet<String> extractClassNamesFromConstantsBasedOnFlags(
            ByteBuffer readBuffer, final int numberOfConstants, ConstantPoolItemFlags flags) throws AssertionError {
        HashSet<String> names = new HashSet<>();
        returnBufferToStartOfConstantPool(readBuffer);
        for (int constantPoolIndex = 1; constantPoolIndex < numberOfConstants; constantPoolIndex++) {
            switch (readBuffer.get()) {
                case CONSTANT_Utf8:
                    readClassNamesInUTF8Value(readBuffer, flags,
                            names, constantPoolIndex);
                    break;
                case CONSTANT_Integer:
                case CONSTANT_Float:
                case CONSTANT_FieldRef:
                case CONSTANT_MethodRef:
                case CONSTANT_InterfaceMethodRef:
                case CONSTANT_NameAndType:
                case CONSTANT_InvokeDynamic:
                    skipBytes(readBuffer, 4);
                    break;
                case CONSTANT_Long:
                case CONSTANT_Double:
                    skipBytes(readBuffer, 8);
                    constantPoolIndex++; // long or double counts as 2 items
                    break;
                case CONSTANT_String:
                case CONSTANT_Class:
                case CONSTANT_MethodType:
                    skipBytes(readBuffer, 2);
                    break;
                case CONSTANT_MethodHandle:
                    skipBytes(readBuffer, 3);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return names;
    }

    private static void readClassNamesInUTF8Value(ByteBuffer readBuffer,
                                                  ConstantPoolItemFlags flags,
                                                  HashSet<String> dependencyClassNames, int constantNumber) {
        int strSize = readBuffer.getChar(), strStart = readBuffer.position();
        boolean multipleNames = flags.isNamedType.get(constantNumber);
        if (flags.isClass.get(constantNumber)) {
            if (readBuffer.get(readBuffer.position()) == ARRAY_START_CHAR) {
                multipleNames = true;
            } else {
                addClassNameToDependencySet(dependencyClassNames, readBuffer,
                        strStart, strSize);
            }
        }
        if (multipleNames) {
            addClassNamesToDependencySet(dependencyClassNames, readBuffer,
                    strStart, strSize);
        }
        readBuffer.position(strStart + strSize);
    }

    private static void flagFieldsAndMethodsAsNamedTypes(ByteBuffer readBuffer,
                                                         BitSet isNamedType) {
        for (int type = 0; type < 2; type++) { // fields and methods
            int numMember = readBuffer.getChar();
            for (int member = 0; member < numMember; member++) {
                skipBytes(readBuffer, 4);
                isNamedType.set(readBuffer.getChar());
                int numAttr = readBuffer.getChar();
                for (int attr = 0; attr < numAttr; attr++) {
                    skipBytes(readBuffer, 2);
                    readBuffer.position(readBuffer.getInt()
                            + readBuffer.position());
                }
            }
        }
    }

    private static void returnBufferToStartOfConstantPool(ByteBuffer readBuffer) {
        readBuffer.position(10);
    }

    private static int readOneConstantPoolItemAndSetFlagIfClassOrNamedType(
            ByteBuffer readBuffer, ConstantPoolItemFlags flags,
            int currentConstantIndex) {
        switch (readBuffer.get()) {
            case CONSTANT_Utf8:
                skipPastVariableLengthString(readBuffer);
                break;
            case CONSTANT_Integer:
            case CONSTANT_Float:
            case CONSTANT_FieldRef:
            case CONSTANT_MethodRef:
            case CONSTANT_InterfaceMethodRef:
            case CONSTANT_InvokeDynamic:
                skipBytes(readBuffer, 4);
                break;
            case CONSTANT_Long:
            case CONSTANT_Double:
                skipBytes(readBuffer, 8);
                currentConstantIndex++;
                break;
            case CONSTANT_String:
                skipBytes(readBuffer, 2);
                break;
            case CONSTANT_NameAndType:
                skipBytes(readBuffer, 2);// skip name, fall through to flag as a
                // named type:
            case CONSTANT_MethodType:
                flags.isNamedType.set(readBuffer.getChar()); // flag as named type
                break;
            case CONSTANT_Class:
                flags.isClass.set(readBuffer.getChar()); // flag as class
                break;
            case CONSTANT_MethodHandle:
                skipBytes(readBuffer, 3);
                break;
            default:
                throw new IllegalArgumentException("constant pool item type "
                        + (readBuffer.get(readBuffer.position() - 1) & 0xff));
        }
        return currentConstantIndex;
    }

    private static void skipBytes(ByteBuffer readBuffer, int bytesToSkip) {
        readBuffer.position(readBuffer.position() + bytesToSkip);
    }

    private static void skipPastVariableLengthString(ByteBuffer readBuffer) {
        readBuffer.position(readBuffer.getChar() + readBuffer.position());
    }

    private static void setCursorToConstantPoolCountPosition(
            ByteBuffer readBuffer) {
        readBuffer.position(8);
    }

    private static void verifyMagicFileTypeHeader(ByteBuffer readBuffer) {
        if (readBuffer.getInt() != 0xcafebabe) {
            throw new IllegalArgumentException("Not a class file");
        }
    }

    private static void addClassNameToDependencySet(HashSet<String> names,
                                                    ByteBuffer readBuffer, int start, int length) {
        final int end = start + length;
        StringBuilder dst = new StringBuilder(length);
        ascii: {
            for (; start < end; start++) {
                byte b = readBuffer.get(start);
                if (b < 0) {
                    break ascii;
                }
                dst.append((char) (b == '/' ? '.' : b));
            }
            names.add(dst.toString());
            return;
        }
        final int oldLimit = readBuffer.limit(), oldPos = dst.length();
        readBuffer.limit(end).position(start);
        dst.append(StandardCharsets.UTF_8.decode(readBuffer));
        readBuffer.limit(oldLimit);
        for (int pos = oldPos, len = dst.length(); pos < len; pos++) {
            if (dst.charAt(pos) == '/') {
                dst.setCharAt(pos, '.');
            }
        }
        names.add(dst.toString());
    }

    private static void addClassNamesToDependencySet(HashSet<String> names,
                                                     ByteBuffer readBuffer, int start, int length) {
        final int end = start + length;
        for (; start < end; start++) {
            if (readBuffer.get(start) == 'L') {
                int endMarkerPosition = start + 1;
                while (readBuffer.get(endMarkerPosition) != ';') {
                    endMarkerPosition++;
                }
                addClassNameToDependencySet(names, readBuffer, start + 1,
                        calculateLength(start, endMarkerPosition));
                start = endMarkerPosition;
            }
        }
    }

    private static int calculateLength(int start, int endMarkerPosition) {
        return endMarkerPosition - start - 1;
    }

    private static final char ARRAY_START_CHAR = '[';

    // Constant pool data type constants:
    private static final byte CONSTANT_Utf8 = 1, CONSTANT_Integer = 3,
            CONSTANT_Float = 4, CONSTANT_Long = 5, CONSTANT_Double = 6,
            CONSTANT_Class = 7, CONSTANT_String = 8, CONSTANT_FieldRef = 9,
            CONSTANT_MethodRef = 10, CONSTANT_InterfaceMethodRef = 11,
            CONSTANT_NameAndType = 12, CONSTANT_MethodHandle = 15,
            CONSTANT_MethodType = 16, CONSTANT_InvokeDynamic = 18;

    // encapsulate byte buffer with its read count:
    private static class Buffer {
        byte[] buf = null;
        int read = 0;

        // convert to ByteBuffer
        ByteBuffer toByteBuffer() {
            return ByteBuffer.wrap(this.buf, 0, this.read);
        }
    }

    // flags for identifying dependency names in the constant pool
    private static class ConstantPoolItemFlags {
        final BitSet isClass;
        final BitSet isNamedType;

        ConstantPoolItemFlags(int constantPoolItemCount) {
            isClass = new BitSet(constantPoolItemCount);
            isNamedType = new BitSet(constantPoolItemCount);
        }
    }
}