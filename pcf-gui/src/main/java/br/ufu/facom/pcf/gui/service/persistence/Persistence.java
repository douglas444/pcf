package br.ufu.facom.pcf.gui.service.persistence;

import br.ufu.facom.pcf.gui.components.ChooserWithReplaceWarning;
import br.ufu.facom.pcf.gui.components.singleton.ConfigurationPanel;
import br.ufu.facom.pcf.gui.components.singleton.FinderPanel;
import br.ufu.facom.pcf.gui.components.singleton.MainFrame;
import br.ufu.facom.pcf.gui.components.singleton.VariationPanel;
import br.ufu.facom.pcf.gui.exception.ServiceException;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Persistence {

    public static void reset() {
        FinderPanel.getInstance().reset();
        ConfigurationPanel.getInstance().reset();
        VariationPanel.getInstance().reset();
    }

    public static void load() throws ServiceException {

        final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setDialogTitle("Load");

        if (fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {

            try {

                final File f = fileChooser.getSelectedFile();
                final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                final XMLConfiguration configuration = (XMLConfiguration) jaxbUnmarshaller.unmarshal(f);

                FinderPanel.getInstance().load(configuration);
                ConfigurationPanel.getInstance().load(configuration);
                VariationPanel.getInstance().load(configuration);

            } catch (Exception e) {
                throw new ServiceException("Error while loading.", e);
            }
        }
    }

    public static void save() throws ServiceException {

        final XMLConfiguration configuration = new XMLConfiguration();

        FinderPanel.getInstance().save(configuration);
        ConfigurationPanel.getInstance().save(configuration);
        VariationPanel.getInstance().save(configuration);

        try{

            final JFileChooser fileChooser = new ChooserWithReplaceWarning(System.getProperty("user.dir"));
            fileChooser.setSelectedFile(new File("pcf-config.xml"));
            fileChooser.setDialogTitle("Save");

            if (fileChooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {

                final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.marshal(configuration, fileChooser.getSelectedFile());

                JOptionPane.showMessageDialog(null, "File saved", null,
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            throw new ServiceException("Error while loading.", e);
        }
    }

}
