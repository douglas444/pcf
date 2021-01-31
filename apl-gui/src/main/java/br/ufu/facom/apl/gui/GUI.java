package br.ufu.facom.apl.gui;

import br.ufu.facom.apl.gui.components.singleton.MainPanel;
import br.ufu.facom.apl.gui.components.singleton.MenuBar;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static GUI instance;

    private GUI() {

        this.configureComponents();
        this.arrangeComponents();
    }

    public static GUI getInstance() {

        if (instance == null) {
            instance = new GUI();
        }
        return instance;

    }

    private void configureComponents() {

        this.setTitle("Active Pattern Learning GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 500);
        this.setResizable(true);
        this.getContentPane().setLayout(new GridBagLayout());

    }

    private void arrangeComponents() {
        this.setContentPane(MainPanel.getInstance());
        this.setJMenuBar(MenuBar.getInstance());
    }

}
