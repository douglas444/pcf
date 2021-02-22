package br.ufu.facom.pcf.gui;

import br.ufu.facom.pcf.gui.components.singleton.MainFrame;
import br.ufu.facom.pcf.gui.components.singleton.OutputDialog;

public class Main {

    public static void main(final String[] args)
    {
        MainFrame.getInstance().setVisible(true);
        OutputDialog.getInstance().setVisible(false);
    }
}