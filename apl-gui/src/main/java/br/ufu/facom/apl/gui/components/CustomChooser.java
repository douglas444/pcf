package br.ufu.facom.apl.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class CustomChooser extends JFileChooser implements AWTEventListener {

    private int lastEventId;
    private Object source;

    public CustomChooser(File file) {

        super(file);

        Toolkit.getDefaultToolkit().addAWTEventListener(this,
                AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK);

    }

    @Override
    public void approveSelection()
    {

        if (((FileListAccessory) getAccessory()).getLstModel().isEmpty()) {
            return;
        }

        File f = getSelectedFile();

        if (lastEventId == KeyEvent.KEY_PRESSED) {
            if (f.exists() && isTraversable(f)) {
                setCurrentDirectory(f);
            }
            return;
        }

        if (lastEventId != MouseEvent.MOUSE_RELEASED || !(source instanceof JButton)) {

            return;
        }

        super.approveSelection();
    }

    @Override
    public void eventDispatched(AWTEvent e) {

        lastEventId = e.getID();
        source = e.getSource();
    }
}

