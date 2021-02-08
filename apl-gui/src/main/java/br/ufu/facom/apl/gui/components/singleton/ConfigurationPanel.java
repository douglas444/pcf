package br.ufu.facom.apl.gui.components.singleton;

import br.ufu.facom.apl.gui.persistence.NominalParameterValue;
import br.ufu.facom.apl.gui.persistence.NumericParameterValue;
import br.ufu.facom.apl.gui.persistence.Persistent;
import br.ufu.facom.apl.gui.persistence.XMLConfiguration;
import br.ufu.facom.apl.gui.components.InstanceConfiguratorComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ConfigurationPanel extends JPanel implements Persistent {

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

    @Override
    public void reset() {
        this.interceptableConfigurator.reset();
        this.metaCategorizerConfigurator.reset();
        this.activeLearningStrategyConfigurator.reset();
    }

    @Override
    public void load(XMLConfiguration configuration) {

        this.interceptableConfigurator.setSelectedItem(configuration.getInterceptableClassName());
        this.metaCategorizerConfigurator.setSelectedItem(configuration.getMetaCategorizerClassName());
        this.activeLearningStrategyConfigurator.setSelectedItem(configuration.getActiveLearningStrategyClassName());

        loadConfiguratorParametersState(this.interceptableConfigurator,
                configuration.getInterceptableNominalParameters(),
                configuration.getInterceptableNumericParameters());
        loadConfiguratorParametersState(this.metaCategorizerConfigurator,
                configuration.getMetaCategorizerNominalParameters(),
                configuration.getMetaCategorizerNumericParameters());
        loadConfiguratorParametersState(this.activeLearningStrategyConfigurator,
                configuration.getActiveLearningStrategyNominalParameters(),
                configuration.getActiveLearningStrategyNumericParameters());
    }

    @Override
    public void save(XMLConfiguration configuration) {

        configuration.setInterceptableClassName(this.interceptableConfigurator.getSelectedItem());
        configuration.setMetaCategorizerClassName(this.metaCategorizerConfigurator.getSelectedItem());
        configuration.setActiveLearningStrategyClassName(this.activeLearningStrategyConfigurator.getSelectedItem());

        saveConfiguratorParametersState(this.interceptableConfigurator,
                configuration.getInterceptableNominalParameters(),
                configuration.getInterceptableNumericParameters());
        saveConfiguratorParametersState(this.metaCategorizerConfigurator,
                configuration.getMetaCategorizerNominalParameters(),
                configuration.getMetaCategorizerNumericParameters());
        saveConfiguratorParametersState(this.activeLearningStrategyConfigurator,
                configuration.getActiveLearningStrategyNominalParameters(),
                configuration.getActiveLearningStrategyNumericParameters());


    }

    private static void loadConfiguratorParametersState(final InstanceConfiguratorComponent configurator,
                                                        final List<NominalParameterValue> nominalParameterValues,
                                                        final List<NumericParameterValue> numericParameterValues) {

        final HashMap<String, String> valueByNominalParameterName = new HashMap<>();
        final HashMap<String, Double> valueByNumericParameterName = new HashMap<>();

        nominalParameterValues.forEach(nominalParameterValue -> {
            valueByNominalParameterName.put(nominalParameterValue.getParameterName(),
                    nominalParameterValue.getValue());
        });
        numericParameterValues.forEach(numericParameterValue -> {
            valueByNumericParameterName.put(numericParameterValue.getParameterName(),
                    numericParameterValue.getValue());
        });

        configurator.load(valueByNominalParameterName, valueByNumericParameterName);
    }

    private static void saveConfiguratorParametersState(final InstanceConfiguratorComponent configurator,
                                                        final List<NominalParameterValue> nominalParameters,
                                                        final List<NumericParameterValue> numericParameters) {

        configurator.getNominalParameterValueByName().forEach((key, value) -> {
            final NominalParameterValue nominalParameterValue = new NominalParameterValue();
            nominalParameterValue.setParameterName(key);
            nominalParameterValue.setValue(value);
            nominalParameters.add(nominalParameterValue);
        });

        configurator.getNumericParameterValueByName().forEach((key, value) -> {
            final NumericParameterValue numericParameterValue = new NumericParameterValue();
            numericParameterValue.setParameterName(key);
            numericParameterValue.setValue(value);
            numericParameters.add(numericParameterValue);
        });

    }
}
