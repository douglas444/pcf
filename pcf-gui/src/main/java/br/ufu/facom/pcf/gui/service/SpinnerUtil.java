package br.ufu.facom.pcf.gui.service;

import br.ufu.facom.pcf.gui.components.singleton.MainFrame;
import br.ufu.facom.pcf.gui.exception.CustomExceptionMessage;

import javax.swing.*;

public class SpinnerUtil {

    public static void commitSpinner(final JSpinner spinnerIncrement, final String fieldName) {
        try {
            spinnerIncrement.commitEdit();
        } catch (Exception e) {

            final String message = "Cannot validate field " + fieldName
                    + ". Reverting to previous value. "
                    + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);

            JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                    "Error", JOptionPane.ERROR_MESSAGE);

            spinnerIncrement.setValue(spinnerIncrement.getPreviousValue());
        }
    }
}
