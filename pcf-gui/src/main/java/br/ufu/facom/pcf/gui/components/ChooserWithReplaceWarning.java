package br.ufu.facom.pcf.gui.components;

import javax.swing.*;
import java.io.File;

public class ChooserWithReplaceWarning extends JFileChooser {

    public ChooserWithReplaceWarning(final String file) {
        super(file);
    }

    @Override
    public void approveSelection(){

        final File f = getSelectedFile();

        if(f.exists() && getDialogType() == SAVE_DIALOG){

            final int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?",
                    "Existing file",JOptionPane.YES_NO_CANCEL_OPTION);

            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }

        super.approveSelection();
    }
}
