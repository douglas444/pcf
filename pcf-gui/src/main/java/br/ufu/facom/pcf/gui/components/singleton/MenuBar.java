package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.core.HighLevelCategorizer;
import br.ufu.facom.pcf.core.Interceptable;
import br.ufu.facom.pcf.core.LowLevelCategorizer;
import br.ufu.facom.pcf.gui.components.TextAreaOutputStream;
import br.ufu.facom.pcf.gui.exception.ServiceException;
import br.ufu.facom.pcf.gui.exception.CustomExceptionMessage;
import br.ufu.facom.pcf.gui.service.ExecutionController;
import br.ufu.facom.pcf.gui.service.WebPageOpener;
import br.ufu.facom.pcf.gui.service.persistence.Persistence;

import javax.swing.*;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuBar extends JMenuBar {

    private static MenuBar instance;

    private final JMenuItem itemRun;
    private final JMenuItem itemStop;
    private final JMenuItem itemNew;
    private final JMenuItem itemLoad;
    private final JMenuItem itemSave;
    private final JMenuItem itemOpenREADME;

    private Interceptable interceptable;

    private MenuBar() {

        this.itemRun = new JMenuItem("Run...");
        this.itemStop = new JMenuItem("Stop");
        this.itemNew = new JMenuItem("New");
        this.itemLoad = new JMenuItem("Load");
        this.itemSave = new JMenuItem("Save");
        this.itemOpenREADME = new JMenuItem("Open README");
        JMenu menuFile = new JMenu("File");
        JMenu menuAbout = new JMenu("About");
        JMenu menuRun = new JMenu("Run");

        menuFile.add(this.itemNew);
        menuFile.add(this.itemLoad);
        menuFile.add(this.itemSave);
        menuRun.add(this.itemRun);
        menuRun.add(this.itemStop);
        menuAbout.add(this.itemOpenREADME);
        this.add(menuFile);
        this.add(menuRun);
        this.add(menuAbout);

        this.configureBehavior();

    }

    public static MenuBar getInstance() {

        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;

    }

    private void configureBehavior() {

        this.itemOpenREADME.addActionListener((event) -> {
            try {
                final URL url = new URL("https://github.com/douglas444/pcf/blob/main/README.md");
                WebPageOpener.openWebpage(url);
            } catch (MalformedURLException | ServiceException e) {
                final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.itemLoad.addActionListener((event) -> {
            try {
                Persistence.load();
            } catch (ServiceException e) {
                final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.itemSave.addActionListener((event) -> {
            try {
                Persistence.save();
            } catch (ServiceException e) {
                final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.itemNew.addActionListener((event) -> Persistence.reset());

        this.itemStop.addActionListener((event) -> {
            try {
                ExecutionController.stop();
            } catch (ServiceException e) {
                final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.itemRun.addActionListener((event) -> {

            OutputPanel.getInstance().clear();
            try {
                ExecutionController.stop();
            } catch (ServiceException e) {
                final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            this.interceptable = (Interceptable) ConfigurationPanel
                    .getInstance()
                    .getInterceptableConfigurator()
                    .configureAndGet();

            final Thread thread = new Thread(() -> {

                FooterPanel.getInstance().setVisible(true);

                final HighLevelCategorizer highLevelCategorizer =
                        (HighLevelCategorizer) ConfigurationPanel
                                .getInstance()
                                .getHighLevelCategorizerConfigurator()
                                .configureAndGet();

                final LowLevelCategorizer lowLevelCategorizer =
                        (LowLevelCategorizer) ConfigurationPanel
                                .getInstance()
                                .getLowLevelCategorizerConfigurator()
                                .configureAndGet();

                if (VariationPanel.getInstance().isUnique()) {

                    PrintStream originalStdOut = System.out;
                    PrintStream originalStdErr = System.out;

                    System.setOut(
                            new PrintStream(
                                    new TextAreaOutputStream(
                                            OutputPanel.getInstance().getTxtArea())));

                    System.setErr(
                            new PrintStream(
                                    new TextAreaOutputStream(
                                            OutputPanel.getInstance().getTxtArea())));

                    try {

                        ExecutionController.execute(
                                this.interceptable,
                                highLevelCategorizer,
                                lowLevelCategorizer);

                    } catch (ServiceException e) {
                        final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                        JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.setOut(originalStdOut);
                    System.setErr(originalStdErr);

                } else {

                    PrintStream originalStdErr = System.out;

                    System.setErr(
                            new PrintStream(
                                    new TextAreaOutputStream(
                                            OutputPanel.getInstance().getTxtArea())));
                    try {

                        ExecutionController.execute(
                                this.interceptable,
                                highLevelCategorizer,
                                lowLevelCategorizer,
                                VariationPanel.getInstance().getTimes(),
                                VariationPanel.getInstance().getIncrement(),
                                VariationPanel.getInstance().getParameter(),
                                VariationPanel.getInstance().getConfigurable());

                    } catch (ServiceException e) {
                        final String message = e.getMessage() + CustomExceptionMessage.buildIgnoringPCFStackTrace(e);
                        JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    System.setErr(originalStdErr);
                }
                FooterPanel.getInstance().setVisible(false);
            });

            thread.setDaemon(true);
            OutputDialog.getInstance().setVisible(true);
            thread.start();

        });
    }

}
