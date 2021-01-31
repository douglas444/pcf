package br.ufu.facom.apl.gui.components.singleton;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private static MainPanel instance;

    private MainPanel() {

        final JPanel pnlScrollableContent = new JPanel(new GridBagLayout());
        final JScrollPane scrollPane = new JScrollPane();

        this.setLayout(new GridBagLayout());

        scrollPane.setViewportView(pnlScrollableContent);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 40, 0, 40);
        pnlScrollableContent.add(FinderPanel.getInstance(), c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 40, 0, 40);
        pnlScrollableContent.add(ConfiguratorPanel.getInstance(), c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(20, 40, 10, 40);
        pnlScrollableContent.add(OutputPanel.getInstance(), c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        this.add(scrollPane, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 10, 5);
        this.add(FooterPanel.getInstance(), c);
    }

    public static MainPanel getInstance() {

        if (instance == null) {
            instance = new MainPanel();
        }
        return instance;

    }
}
