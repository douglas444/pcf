package br.ufu.facom.pcf.gui.components.singleton;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static GUI instance;

    private GUI() {
        this.setTitle("Active Pattern Learning GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 500);
        this.setResizable(true);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setContentPane(MainPanel.getInstance());
        this.setJMenuBar(MenuBar.getInstance());
    }

    public static GUI getInstance() {

        if (instance == null) {
            instance = new GUI();
        }
        return instance;

    }

}
