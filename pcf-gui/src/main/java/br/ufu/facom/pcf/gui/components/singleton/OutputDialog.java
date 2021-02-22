package br.ufu.facom.pcf.gui.components.singleton;

import javax.swing.*;
import java.awt.*;

public class OutputDialog extends JDialog {

    private static OutputDialog instance;

    private OutputDialog(final JFrame frame) {
        super(frame);
        this.setTitle("Output");
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setSize(650, 500);
        this.setResizable(true);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setContentPane(OutputPanel.getInstance());
    }

    public static OutputDialog getInstance() {

        if (instance == null) {
            instance = new OutputDialog(MainFrame.getInstance());
        }
        return instance;

    }

}
