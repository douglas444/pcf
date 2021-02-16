package br.ufu.facom.pcf.gui;

import br.ufu.facom.pcf.gui.components.singleton.GUI;

public class Main {

    public static void main(final String[] args)
    {
        final GUI view = GUI.getInstance();
        view.setVisible(true);
    }
}