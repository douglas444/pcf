package br.ufu.facom.pmc.gui.components.singleton;

import br.ufu.facom.pmc.core.Application;
import br.ufu.facom.pmc.core.HighLevelCategorizer;
import br.ufu.facom.pmc.core.Interceptable;
import br.ufu.facom.pmc.core.LowLevelCategorizer;
import br.ufu.facom.pmc.gui.persistence.XMLConfiguration;
import br.ufu.facom.pmc.gui.components.ChooserWithReplaceWarning;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
public class MenuBar extends JMenuBar {

    private static MenuBar instance;

    private final JMenuItem itemRun;
    private final JMenuItem itemStop;
    private final JMenuItem itemNew;
    private final JMenuItem itemLoad;
    private final JMenuItem itemSave;
    private final JMenuItem itemExit;
    private final JMenu menuFile;
    private final JMenu menuHelp;
    private final JMenu menuRun;

    private MenuBar() {

        this.itemRun = new JMenuItem("Run...");
        this.itemStop = new JMenuItem("Stop");
        this.itemNew = new JMenuItem("New");
        this.itemLoad = new JMenuItem("Load");
        this.itemSave = new JMenuItem("Save");
        this.itemExit = new JMenuItem("Exit");
        this.menuFile = new JMenu("File");
        this.menuHelp = new JMenu("Help");
        this.menuRun = new JMenu("Run");

        menuFile.add(itemNew);
        menuFile.add(itemLoad);
        menuFile.add(itemSave);
        menuFile.add(itemExit);
        menuRun.add(itemRun);
        menuRun.add(itemStop);
        this.add(menuFile);
        this.add(menuRun);
        this.add(menuHelp);

        this.configureBehavior();

    }

    public static MenuBar getInstance() {

        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;

    }

    private void configureBehavior() {

        this.itemExit.addActionListener((event) -> {

        });

        this.itemLoad.addActionListener((event) -> {

            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load");

            if (fileChooser.showOpenDialog(GUI.getInstance()) == JFileChooser.APPROVE_OPTION) {

                try {

                    final File f = fileChooser.getSelectedFile();
                    final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                    final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    final XMLConfiguration configuration = (XMLConfiguration) jaxbUnmarshaller.unmarshal(f);

                    FinderPanel.getInstance().load(configuration);
                    ConfigurationPanel.getInstance().load(configuration);
                    VariationPanel.getInstance().load(configuration);

                } catch (Exception e) {
                    final String message = e.getMessage() + "\n    " + ExceptionUtils.getRootCauseMessage(e);
                    JOptionPane.showMessageDialog(GUI.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.itemSave.addActionListener((event) -> {

            final XMLConfiguration configuration = new XMLConfiguration();

            FinderPanel.getInstance().save(configuration);
            ConfigurationPanel.getInstance().save(configuration);
            VariationPanel.getInstance().save(configuration);

            try{

                final JFileChooser fileChooser = new ChooserWithReplaceWarning();
                fileChooser.setSelectedFile(new File("pmc-config.xml"));
                fileChooser.setDialogTitle("Save");

                if (fileChooser.showSaveDialog(GUI.getInstance()) == JFileChooser.APPROVE_OPTION) {

                    final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                    final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    jaxbMarshaller.marshal(configuration, fileChooser.getSelectedFile());

                    JOptionPane.showMessageDialog(null, "File saved", null,
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                final String message = e.getMessage() + "\n    " + ExceptionUtils.getRootCauseMessage(e);
                JOptionPane.showMessageDialog(GUI.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        this.itemNew.addActionListener((event) -> {

            FinderPanel.getInstance().reset();
            ConfigurationPanel.getInstance().reset();
            VariationPanel.getInstance().reset();

        });

        this.itemRun.addActionListener((event) -> {

            FooterPanel.getInstance().setVisible(true);
            Application.execute(
                    (Interceptable) ConfigurationPanel.getInstance().getInterceptableConfigurator().getSelectedInstance(),
                    (HighLevelCategorizer) ConfigurationPanel.getInstance().getHighLevelCategorizerConfigurator().getSelectedInstance(),
                    (LowLevelCategorizer) ConfigurationPanel.getInstance().getLowLevelCategorizerConfigurator().getSelectedInstance());
            FooterPanel.getInstance().setVisible(false);

        });


        this.itemStop.addActionListener((event) -> {

        });

        this.menuFile.addActionListener((event) -> {
        });

        this.menuRun.addActionListener((event) -> {
        });

    }
}
