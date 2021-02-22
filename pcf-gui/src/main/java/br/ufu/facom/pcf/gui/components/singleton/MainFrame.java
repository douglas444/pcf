package br.ufu.facom.pcf.gui.components.singleton;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;

    private MainFrame() {
        this.setTitle("Active Pattern Learning GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 500);
        this.setResizable(true);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setContentPane(MainPanel.getInstance());
        this.setJMenuBar(MenuBar.getInstance());
    }

    public static MainFrame getInstance() {

        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;

    }

}
