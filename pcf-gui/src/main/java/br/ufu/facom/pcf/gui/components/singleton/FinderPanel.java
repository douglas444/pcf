package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.core.HighLevelCategorizer;
import br.ufu.facom.pcf.core.Interceptable;
import br.ufu.facom.pcf.core.LowLevelCategorizer;
import br.ufu.facom.pcf.gui.components.ChooserForFileListAccessory;
import br.ufu.facom.pcf.gui.components.FileListAccessory;
import br.ufu.facom.pcf.gui.exception.ServiceException;
import br.ufu.facom.pcf.gui.service.ClassLoaderService;
import br.ufu.facom.pcf.gui.service.CustomExceptionMessage;
import br.ufu.facom.pcf.gui.service.persistence.Persistent;
import br.ufu.facom.pcf.gui.service.persistence.XMLConfiguration;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class FinderPanel extends JPanel implements Persistent {

    private static FinderPanel instance;

    private final JTextField txtPath;
    private final ChooserForFileListAccessory chooser;
    private final JButton btnSearch;

    private FinderPanel() {

        final JLabel lblClasspath = new JLabel("Classpath");

        this.btnSearch = new JButton("...");
        this.txtPath = new JTextField(1);
        this.txtPath.setEnabled(false);
        this.chooser = new ChooserForFileListAccessory(
                FileSystemView.getFileSystemView().getHomeDirectory());

        this.setLayout(new GridBagLayout());
        lblClasspath.setFont(lblClasspath.getFont().deriveFont(Font.PLAIN));

        this.chooser.setAccessory(new FileListAccessory(chooser));
        this.chooser.setDialogTitle("Select one or more JAR files or/and classpath folders");
        this.chooser.setMultiSelectionEnabled(true);
        this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.chooser.setAcceptAllFileFilterUsed(false);
        this.chooser.setFileHidingEnabled(false);
        this.chooser.setFileFilter(
                new FileNameExtensionFilter("Classpath folders; JAR files;", "jar"));

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 5, 10, 5);
        this.add(lblClasspath, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 10, 5);
        this.add(this.txtPath, c);

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 5, 10, 5);
        this.add(btnSearch, c);

        this.configureBehavior();
    }

    public static FinderPanel getInstance() {

        if (instance == null) {
            instance = new FinderPanel();
        }
        return instance;

    }

    @Override
    public void reset() {
        this.txtPath.setText("");
        ((FileListAccessory) this.chooser.getAccessory()).getLstModel().removeAllElements();
    }

    @Override
    public void load(XMLConfiguration configuration) {

        final List<File> paths = configuration.getPaths()
                .stream()
                .map(File::new)
                .collect(Collectors.toList());

        this.configurePaths(paths);

    }

    @Override
    public void save(XMLConfiguration configuration) {

        final List<String> paths = Arrays.stream(this.txtPath.getText().split(";").clone())
                .map(path -> path.replace(" ", ""))
                .filter(path -> !path.isEmpty())
                .collect(Collectors.toList());

        configuration.getPaths().addAll(paths);

    }

    private void configureBehavior() {

        this.btnSearch.addActionListener((event) -> {

            if (this.chooser.showOpenDialog(MainFrame.getInstance())
                    == JFileChooser.APPROVE_OPTION) {

                final FileListAccessory accessory = (FileListAccessory) this.chooser.getAccessory();
                final Enumeration<File> enumeration = accessory.getLstModel().elements();
                final List<File> files = new ArrayList<>();

                while (enumeration.hasMoreElements()) {
                    final File file = enumeration.nextElement();
                    files.add(file);
                }

                this.configurePaths(files);
            }

        });

    }

    private void configurePaths(final List<File> paths) {

        try {

            final HashMap<Class<?>, HashMap<String, Object>> instancesMapByClass =
                    ClassLoaderService.digestClasspathArray(paths.toArray(new File[]{}));

            ConfigurationPanel.getInstance().getInterceptableConfigurator()
                    .setCmbInstances(instancesMapByClass.get(Interceptable.class));
            ConfigurationPanel.getInstance().getHighLevelCategorizerConfigurator()
                    .setCmbInstances(instancesMapByClass.get(HighLevelCategorizer.class));
            ConfigurationPanel.getInstance().getLowLevelCategorizerConfigurator()
                    .setCmbInstances(instancesMapByClass.get(LowLevelCategorizer.class));

            final StringBuilder builder = new StringBuilder();

            for (final File file : paths) {
                builder.append(file.getAbsolutePath()).append("; ");
            }

            this.txtPath.setText(builder.toString());

        } catch (ServiceException e) {

            final String message = e.getMessage() + "\n    "
                    + CustomExceptionMessage.build(e);

            JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
