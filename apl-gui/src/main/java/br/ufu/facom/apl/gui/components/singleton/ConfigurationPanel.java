package br.ufu.facom.apl.gui.components.singleton;

import br.ufu.facom.apl.gui.components.InstanceConfiguratorComponent;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {

    private static ConfigurationPanel instance;

    private final InstanceConfiguratorComponent interceptableConfigurator;
    private final InstanceConfiguratorComponent metaCategorizerConfigurator;
    private final InstanceConfiguratorComponent activeLearningStrategyConfigurator;

    private ConfigurationPanel() {

        this.interceptableConfigurator = new InstanceConfiguratorComponent("Interceptable");
        this.metaCategorizerConfigurator = new InstanceConfiguratorComponent("MetaCategorizer");
        this.activeLearningStrategyConfigurator = new InstanceConfiguratorComponent("ActiveLearningStrategy");

        this.setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(this.interceptableConfigurator, c);

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(this.metaCategorizerConfigurator, c);

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(this.activeLearningStrategyConfigurator, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        this.add(VariationPanel.getInstance(), c);


    }

    public static ConfigurationPanel getInstance() {

        if (instance == null) {
            instance = new ConfigurationPanel();
        }
        return instance;

    }

    public boolean hasNumericParameters() {
        return !this.activeLearningStrategyConfigurator.getNumericParameterValueByName().isEmpty()
                || !this.interceptableConfigurator.getNumericParameterValueByName().isEmpty()
                || !this.metaCategorizerConfigurator.getNumericParameterValueByName().isEmpty();
    }

    public InstanceConfiguratorComponent getInterceptableConfigurator() {
        return interceptableConfigurator;
    }

    public InstanceConfiguratorComponent getMetaCategorizerConfigurator() {
        return metaCategorizerConfigurator;
    }

    public InstanceConfiguratorComponent getActiveLearningStrategyConfigurator() {
        return activeLearningStrategyConfigurator;
    }
}
