package br.ufu.facom.apl.gui.components;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.*;

public class FileListAccessory extends JComponent implements PropertyChangeListener {

    private File[] files = null;
    private final DefaultListModel<File> lstModel;
    private final JList<File> lstSelectedFiles;

    public FileListAccessory(final CustomChooser chooser) {

        chooser.addPropertyChangeListener(this);

        this.lstModel = new DefaultListModel<>();
        this.lstSelectedFiles = new JList<>(this.lstModel);
        final JButton btnRemoveItem = new JButton("<");
        final JButton btnAddItem = new JButton(">");
        final JScrollPane pane = new JScrollPane(this.lstSelectedFiles);

        btnRemoveItem.addActionListener(e -> {
            final int selectedIndex = this.lstSelectedFiles.getSelectedIndex();

            if (selectedIndex != -1) {
                this.lstModel.remove(selectedIndex);
                if (selectedIndex == this.lstModel.getSize()) {
                    this.lstSelectedFiles.setSelectedIndex(selectedIndex - 1);
                } else {
                    this.lstSelectedFiles.setSelectedIndex(selectedIndex);
                }
            }
        });

        btnAddItem.addActionListener(e -> {
            if (this.files != null) {
                for (final File file : this.files) {
                    if (!this.lstModel.contains(file)) {
                        this.lstModel.addElement(file);
                    }
                }
            }
        });

        this.lstSelectedFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        final JPanel pnlAddRemove = new JPanel(new GridBagLayout());

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.VERTICAL;
        pnlAddRemove.add(btnAddItem, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.VERTICAL;
        pnlAddRemove.add(btnRemoveItem, constraints);

        setLayout(new BorderLayout());
        add(pnlAddRemove, BorderLayout.WEST);
        add(pane, BorderLayout.EAST);

    }

    public DefaultListModel<File> getLstModel() {
        return lstModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {

        final String prop = e.getPropertyName();

        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            files = null;
        } else if (JFileChooser.SELECTED_FILES_CHANGED_PROPERTY.equals(prop)) {
            files = (File[]) e.getNewValue();
        }
    }
}