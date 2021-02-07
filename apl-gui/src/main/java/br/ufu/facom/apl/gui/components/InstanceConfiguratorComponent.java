package br.ufu.facom.apl.gui.components;

import br.ufu.facom.apl.core.Configurable;
import br.ufu.facom.apl.gui.components.singleton.ConfigurationPanel;
import br.ufu.facom.apl.gui.components.singleton.VariationPanel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class InstanceConfiguratorComponent extends JPanel {

    private final JPanel pnlParameters;
    private final JPanel pnlNumericParameters;
    private final JPanel pnlNominalParameters;
    private final JComboBox<String> cmbInstances;
    private final HashMap<String, JTextField> txtFieldByParameterName;
    private final HashMap<String, JSpinner> spinnerByParameterName;
    private final HashMap<String, Object> instanceByName;

    public InstanceConfiguratorComponent(final String title) {

        this.setLayout(new GridBagLayout());

        this.pnlParameters = new JPanel(new GridBagLayout());
        this.pnlNumericParameters = new JPanel(new GridBagLayout());
        this.pnlNominalParameters = new JPanel(new GridBagLayout());
        this.cmbInstances = new JComboBox<>();
        this.txtFieldByParameterName = new HashMap<>();
        this.spinnerByParameterName = new HashMap<>();
        this.instanceByName = new HashMap<>();

        initializeComponents(title);
        arrangeComponents();
        configureBehavior();
    }

    private void initializeComponents(final String title) {

        TitledBorder border =  BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), title);

        this.setBorder(border);
        this.cmbInstances.setFont(this.cmbInstances.getFont().deriveFont(Font.PLAIN));
        this.cmbInstances.setPrototypeDisplayValue("");

        border =  BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "Nominal Parameters");
        this.pnlNominalParameters.setBorder(border);
        this.pnlNominalParameters.setVisible(false);


        border =  BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "Numeric Parameters");
        this.pnlNumericParameters.setBorder(border);
        this.pnlNumericParameters.setVisible(false);

    }

    private void arrangeComponents() {

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        this.add(this.cmbInstances, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        this.pnlParameters.add(this.pnlNominalParameters, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlParameters.add(this.pnlNumericParameters, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        this.add(this.pnlParameters, c);

    }

    private void configureBehavior() {

        this.cmbInstances.addActionListener((e) -> {

            final String selectedItem = (String) this.cmbInstances.getSelectedItem();

            if (!this.instanceByName.containsKey(selectedItem)) {
                return;
            }

            final Object instance = this.instanceByName.get(selectedItem);

            List<String> numericParameters = null;
            List<String> nominalParameters = null;

            if (instance instanceof Configurable) {

                final Configurable configurable = (Configurable) instance;

                numericParameters = configurable.getNumericParametersNames();
                nominalParameters = configurable.getNominalParametersNames();
            }

            this.setParameters(numericParameters != null ? numericParameters : new ArrayList<>(), true);
            this.setParameters(nominalParameters != null ? nominalParameters : new ArrayList<>(), false);

            if (!this.getNumericParameterValueByName().isEmpty()) {
                this.pnlNumericParameters.setVisible(true);
            }

            if (!this.getNominalParameterValueByName().isEmpty()) {
                this.pnlNominalParameters.setVisible(true);
            }

            VariationPanel.getInstance().setVisible(ConfigurationPanel.getInstance().hasNumericParameters());
            VariationPanel.getInstance().setVariateParametersList();

        });

    }

    public void setCmbInstances(final HashMap<String, Object> instanceByName) {

        this.instanceByName.clear();
        this.cmbInstances.removeAllItems();

        this.instanceByName.putAll(instanceByName);
        this.instanceByName.keySet().forEach(this.cmbInstances::addItem);

    }

    private void setParameters(final List<String> parametersNames, final boolean isNumeric) {

        final GridBagConstraints c = new GridBagConstraints();
        final JPanel panel;
        final Function<String, Component> fieldBuilder;

        if (isNumeric) {

            panel = this.pnlNumericParameters;
            this.spinnerByParameterName.clear();

            fieldBuilder = (fieldName) -> {
                final SpinnerNumberModel model = new SpinnerNumberModel(0.0, -Double.MAX_VALUE, Double.MAX_VALUE,0.1);
                final JSpinner spinnerParameter = new JSpinner(model);
                ((JSpinner.NumberEditor) spinnerParameter.getEditor()).getTextField().setColumns(1);
                this.spinnerByParameterName.put(fieldName, spinnerParameter);
                return spinnerParameter;
            };

        } else {

            panel = this.pnlNominalParameters;
            this.txtFieldByParameterName.clear();

            fieldBuilder = (fieldName) -> {
                final JTextField txtFieldParameter = new JTextField(1);
                this.txtFieldByParameterName.put(fieldName, txtFieldParameter);
                return txtFieldParameter;
            };
        }

        panel.setVisible(!parametersNames.isEmpty());
        panel.removeAll();

        for (int i = 0; i < parametersNames.size(); i++) {

            final JLabel lblParameter = new JLabel(parametersNames.get(i) + ":");
            lblParameter.setFont(lblParameter.getFont().deriveFont(Font.PLAIN));

            c.weightx = 0;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.NORTHEAST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(2, 2, 2, 2);
            panel.add(lblParameter, c);

            final Component component = fieldBuilder.apply(parametersNames.get(i));

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 1;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.NORTHWEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            panel.add(component, c);

        }

    }

    public Object getSelectedInstance() {
        final String selectedItem = (String) this.cmbInstances.getSelectedItem();
        return this.instanceByName.getOrDefault(selectedItem, null);
    }

    public HashMap<String, String> getNominalParameterValueByName() {

        final HashMap<String, String> parameterValueByName = new HashMap<>();
        this.txtFieldByParameterName.forEach((parameterName, txtField) -> {
            parameterValueByName.put(parameterName, txtField.getText());
        });
        return parameterValueByName;

    }

    public HashMap<String, Double> getNumericParameterValueByName() {

        final HashMap<String, Double> parameterValueByName = new HashMap<>();
        this.spinnerByParameterName.forEach((parameterName, spinner) -> {
            parameterValueByName.put(parameterName, ((Double) spinner.getValue()));
        });
        return parameterValueByName;

    }
}
