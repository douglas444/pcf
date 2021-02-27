package br.ufu.facom.pcf.gui.components;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {

    private static final int DEFAULT_BUFFER_SIZE = 1;

    private final JTextArea mText;
    private final byte[] mBuf;
    private int mLocation;

    public TextAreaOutputStream(JTextArea component) {
        this(component, DEFAULT_BUFFER_SIZE);
    }

    public TextAreaOutputStream(JTextArea component, int bufferSize) {
        this.mText = component;
        if (bufferSize < 1) bufferSize = 1;
        this.mBuf = new byte[bufferSize];
        this.mLocation = 0;
    }

    @Override
    public void write(int arg0) throws IOException {
        this.mBuf[this.mLocation++] = (byte)arg0;
        if (this.mLocation == this.mBuf.length) {
            flush();
        }
    }

    @Override
    public void flush() {
        this.mText.append(new String(this.mBuf, 0, this.mLocation));
        this.mText.setCaretPosition(this.mText.getDocument().getLength());
        this.mLocation = 0;
    }

}