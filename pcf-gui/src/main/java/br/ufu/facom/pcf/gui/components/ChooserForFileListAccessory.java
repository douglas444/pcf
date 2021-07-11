package br.ufu.facom.pcf.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class ChooserForFileListAccessory extends JFileChooser implements AWTEventListener {

    private int lastEventId;
    private Object source;

    public ChooserForFileListAccessory(String file) {

        super(file);
        Toolkit.getDefaultToolkit().addAWTEventListener(this,
                AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK);

    }

    @Override
    public void approveSelection() {

        if (((FileListAccessory) getAccessory()).getLstModel().isEmpty()) {
            return;
        }

        final File f = getSelectedFile();

        if (this.lastEventId == KeyEvent.KEY_PRESSED) {
            if (f.exists() && isTraversable(f)) {
                setCurrentDirectory(f);
            }
            return;
        }

        if (this.lastEventId != MouseEvent.MOUSE_RELEASED || !(this.source instanceof JButton)) {
            return;
        }

        super.approveSelection();
    }

    @Override
    public void eventDispatched(AWTEvent e) {
        this.lastEventId = e.getID();
        this.source = e.getSource();
    }
}

