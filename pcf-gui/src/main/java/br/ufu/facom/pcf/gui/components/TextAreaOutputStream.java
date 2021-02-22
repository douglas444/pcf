package br.ufu.facom.pcf.gui.components;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {
    public static final int DEFAULT_BUFFER_SIZE = 1;

    JTextArea mText;
    byte[] mBuf;
    int mLocation;
    public TextAreaOutputStream(JTextArea component) {
        this(component, DEFAULT_BUFFER_SIZE);
    }

    public TextAreaOutputStream(JTextArea component, int bufferSize) {
        mText = component;
        if (bufferSize < 1) bufferSize = 1;
        mBuf = new byte[bufferSize];
        mLocation = 0;
    }
    @Override
    public void write(int arg0) throws IOException {
        mBuf[mLocation++] = (byte)arg0;
        if (mLocation == mBuf.length) {
            flush();
        }
    }

    public void flush() {
        mText.append(new String(mBuf, 0, mLocation));
        mText.setCaretPosition(mText.getDocument().getLength());
        mLocation = 0;
    }
}