package br.ufu.facom.pmc.gui.components.singleton;

import br.ufu.facom.pmc.gui.components.TextAreaOutputStream;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintStream;

public class OutputPanel extends JPanel {

    private static OutputPanel instance;

    private OutputPanel() {

        final JTextArea txtArea = new JTextArea(10, 1);
        final JScrollPane scrollPane = new JScrollPane();
        final JPopupMenu popUpMenu = new JPopupMenu();

        final Action cut = new DefaultEditorKit.CutAction();
        final Action copy = new DefaultEditorKit.CopyAction();
        final Action paste = new DefaultEditorKit.PasteAction();
        final Action save = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("");
            }
        };

        final Action selectAll = new TextAction("Select All") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextComponent component = getFocusedComponent();
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

        final TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Output");
        border.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(border);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(txtArea);
        txtArea.setComponentPopupMenu(popUpMenu);

        this.add(scrollPane, BorderLayout.CENTER);
        System.setOut(new PrintStream(new TextAreaOutputStream(txtArea)));

    }

    public static OutputPanel getInstance() {

        if (instance == null) {
            instance = new OutputPanel();
        }
        return instance;

    }
}
