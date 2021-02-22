package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.core.Interceptable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OutputDialog extends JDialog {

    private static OutputDialog instance;

    private OutputDialog(final JFrame frame) {
        super(frame);
        this.setTitle("Output");
        this.setSize(650, 500);
        this.setResizable(true);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setContentPane(OutputPanel.getInstance());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OutputDialog.getInstance().setVisible(false);
                final Interceptable interceptable = (Interceptable) ConfigurationPanel.getInstance()
                        .getInterceptableConfigurator().get();
                if (interceptable != null) {
                    interceptable.stop();
                }
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
