package br.ufu.facom.apl.gui.components.singleton;

import br.ufu.facom.apl.core.ActiveLearningStrategy;
import br.ufu.facom.apl.core.MetaCategorizer;
import br.ufu.facom.apl.core.interceptor.Interceptable;
import br.ufu.facom.apl.gui.GUI;
import br.ufu.facom.apl.gui.Service;
import br.ufu.facom.apl.gui.ServiceException;
import br.ufu.facom.apl.gui.components.CustomChooser;
import br.ufu.facom.apl.gui.components.FileListAccessory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class FinderPanel extends JPanel {

    private static FinderPanel instance;

    private final JTextField txtPath;
    private final CustomChooser chooser;
    private final JButton btnSearch;

    private FinderPanel() {

        final JLabel lblClasspath = new JLabel("Classpath");

        this.btnSearch = new JButton("...");
        this.txtPath = new JTextField(1);
        this.chooser = new CustomChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        this.setLayout(new GridBagLayout());
        lblClasspath.setFont(lblClasspath.getFont().deriveFont(Font.PLAIN));

        this.chooser.setAccessory(new FileListAccessory(chooser));
        this.chooser.setDialogTitle("Select one or more JAR files or/and classpath folders");
        this.chooser.setMultiSelectionEnabled(true);
        this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.chooser.setAcceptAllFileFilterUsed(false);
        this.chooser.setFileHidingEnabled(false);
        this.chooser.setFileFilter(new FileNameExtensionFilter("Classpath folders; JAR files;", "jar"));

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
    }

    public static FinderPanel getInstance() {

        if (instance == null) {
            instance = new FinderPanel();
        }
        return instance;

    }

    static {

        FinderPanel.getInstance().getBtnSearch().addActionListener((event) -> {

            if (FinderPanel.getInstance().getChooser()
                    .showOpenDialog(GUI.getInstance()) == JFileChooser.APPROVE_OPTION) {

                final FileListAccessory accessory = (FileListAccessory) FinderPanel.getInstance()
                        .getChooser().getAccessory();
                final Enumeration<File> enumeration = accessory.getLstModel().elements();

                final List<File> files = new ArrayList<>();

                while (enumeration.hasMoreElements()) {
                    final File file = enumeration.nextElement();
                    files.add(file);
                }

                try {

                    final HashMap<Class<?>, HashMap<String, Object>> instancesMapByClass =
                            Service.digestClasspathArray(files.toArray(new File[]{}));

                    ConfigurationPanel.getInstance().getInterceptableConfigurator()
                            .setCmbInstances(instancesMapByClass.get(Interceptable.class));
                    ConfigurationPanel.getInstance().getMetaCategorizerConfigurator()
                            .setCmbInstances(instancesMapByClass.get(MetaCategorizer.class));
                    ConfigurationPanel.getInstance().getActiveLearningStrategyConfigurator()
                            .setCmbInstances(instancesMapByClass.get(ActiveLearningStrategy.class));

                    VariationPanel.getInstance().setVisible(false);

                    if (ConfigurationPanel.getInstance().hasNumericParameters()) {
                        VariationPanel.getInstance().setVisible(true);
                        VariationPanel.getInstance().setVariateParametersList();
                    }

                    final StringBuilder builder = new StringBuilder();

                    for (final File file : files) {
                        builder.append(file.getAbsolutePath()).append("; ");
                    }

                    FinderPanel.getInstance().getTxtPath().setText(builder.toString());

                } catch (ServiceException e) {

                    final String message = e.getMessage() + "\n    " + ExceptionUtils.getRootCauseMessage(e);
                    JOptionPane.showMessageDialog(GUI.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        });
    }

    public JTextField getTxtPath() {
        return txtPath;
    }

    public CustomChooser getChooser() {
        return chooser;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }
}
