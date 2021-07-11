package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.gui.components.ChooserWithReplaceWarning;
import br.ufu.facom.pcf.gui.exception.CustomExceptionMessage;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPanel extends JPanel {

    private static OutputPanel instance;
    private final JTextArea txtArea;

    private OutputPanel() {

        this.txtArea = new JTextArea(10, 1);
        final JScrollPane scrollPane = new JScrollPane();
        final JPopupMenu popUpMenu = new JPopupMenu();

        final Action cut = new DefaultEditorKit.CutAction();
        final Action copy = new DefaultEditorKit.CopyAction();
        final Action paste = new DefaultEditorKit.PasteAction();
        final Action save = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {

                final String interceptable = ConfigurationPanel
                        .getInstance()
                        .getInterceptableConfigurator()
                        .getSelectedItem();

                final String highLevelCategorizer = ConfigurationPanel
                        .getInstance()
                        .getHighLevelCategorizerConfigurator()
                        .getSelectedItem();

                final String lowLevelCategorizer = ConfigurationPanel
                        .getInstance()
                        .getLowLevelCategorizerConfigurator()
                        .getSelectedItem();

                final String variation = VariationPanel.getInstance().isUnique() ? "unique" : "multiple";

                final String fileName = interceptable
                        + "_" + highLevelCategorizer
                        + "_" + lowLevelCategorizer
                        + "_" + variation + ".csv";

                final JFileChooser chooser = new ChooserWithReplaceWarning(System.getProperty("user.dir"));
                chooser.setSelectedFile(new File(fileName));
                chooser.setApproveButtonText("Save");
                final int actionDialog = chooser.showOpenDialog(OutputPanel.getInstance());
                if (actionDialog != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                final File file = new File(chooser.getSelectedFile().toString());
                try (BufferedWriter outFile = new BufferedWriter(new FileWriter(file))) {
                    OutputPanel.getInstance().getTxtArea().write(outFile);
                    JOptionPane.showMessageDialog(null, "File saved", null,
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    final String message = e.getMessage() + CustomExceptionMessage.build(e);
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        final Action selectAll = new TextAction("Select All") {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextComponent component = getFocusedComponent();
                component.selectAll();
                component.requestFocusInWindow();
            }
        };

        cut.putValue(Action.NAME, "Cut");
        copy.putValue(Action.NAME, "Copy");
        paste.putValue(Action.NAME, "Paste");
        save.putValue(Action.NAME, "Save as...");

        popUpMenu.add(paste);
        popUpMenu.add(copy);
        popUpMenu.add(cut);
        popUpMenu.add(selectAll);
        popUpMenu.add(save);

        this.setLayout(new BorderLayout());

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(txtArea);
        txtArea.setComponentPopupMenu(popUpMenu);
        txtArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(FooterPanel.getInstance(), BorderLayout.SOUTH);

    }

    public static OutputPanel getInstance() {

        if (instance == null) {
            instance = new OutputPanel();
        }
        return instance;

    }

    public void clear() {
        this.txtArea.setText("");
    }

    public JTextArea getTxtArea() {
        return txtArea;
    }
}
