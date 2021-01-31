package br.ufu.facom.apl.gui.components.singleton;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private static MainPanel instance;
    private final double MARGIN = 0.2;

    private MainPanel() {

        final JPanel pnlScrollableContent = new JPanel(new GridBagLayout());
        final JScrollPane scrollPane = new JScrollPane();

        this.setLayout(new GridBagLayout());

        scrollPane.setViewportView(pnlScrollableContent);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        final JPanel pnlDummyRightMargin = new JPanel();
        final JPanel pnlDummyLeftMargin = new JPanel();

        final GridBagConstraints c = new GridBagConstraints();


        c.weightx = MARGIN;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        pnlScrollableContent.add(pnlDummyRightMargin, c);

        c.weightx = MARGIN;
        c.weighty = 0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        pnlScrollableContent.add(pnlDummyLeftMargin, c);

        c.weightx = 1 - MARGIN;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        pnlScrollableContent.add(FinderPanel.getInstance(), c);

        c.weightx = 1 - MARGIN;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        pnlScrollableContent.add(ConfigurationPanel.getInstance(), c);

        c.weightx = 1 - MARGIN;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 10, 0);
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
