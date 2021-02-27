package br.ufu.facom.pcf.gui.components.singleton;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OutputDialog extends JDialog {

    private static OutputDialog instance;

    private OutputDialog(final JFrame frame) {
        super(frame);
        this.setTitle("Output");
        this.setSize(650, 500);
        this.setResizable(true);
        this.setContentPane(OutputPanel.getInstance());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OutputDialog.getInstance().setVisible(false);
                MenuBar.stop();
            }
        });
    }

    public static OutputDialog getInstance() {

        if (instance == null) {
            instance = new OutputDialog(MainFrame.getInstance());
        }
        return instance;

    }


}
