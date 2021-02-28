package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.gui.exception.ServiceException;
import br.ufu.facom.pcf.gui.exception.CustomExceptionMessage;
import br.ufu.facom.pcf.gui.service.ExecutionController;

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
            public void windowClosing(WindowEvent event) {
                OutputDialog.getInstance().setVisible(false);
                try {
                    ExecutionController.stop();
                } catch (ServiceException e) {
                    final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
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
