package br.ufu.facom.apl.gui.components;

import br.ufu.facom.apl.core.Configurable;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

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

        TitledBorder borderParameters =  BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),"Parameters");

        border.setTitleJustification(TitledBorder.LEFT);
        borderParameters.setTitleJustification(TitledBorder.LEFT);

        this.setBorder(border);
        this.pnlParameters.setBorder(borderParameters);
        this.cmbInstances.setFont(this.cmbInstances.getFont().deriveFont(Font.PLAIN));
        this.cmbInstances.setPrototypeDisplayValue("");
        this.pnlParameters.setVisible(false);
        this.pnlNominalParameters.setVisible(false);;
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
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        this.pnlParameters.add(this.pnlNominalParameters, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        this.pnlParameters.add(this.pnlNumericParameters, c);

        c.weightx = 0;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
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

            if (instance instanceof Configurable) {

                final Configurable configurable = (Configurable) instance;
                this.setNumericParameters(configurable.getNumericParametersNames());
                this.setNominalParameters(configurable.getNominalParametersNames());

                if (!this.getNumericParameterValueByName().isEmpty()
                        || !this.getNominalParameterValueByName().isEmpty()) {
                    this.pnlParameters.setVisible(true);
                }
            }
        });

    }

    public void setCmbInstances(final HashMap<String, Object> instanceByName) {

        this.instanceByName.clear();
        this.cmbInstances.removeAllItems();

        this.instanceByName.putAll(instanceByName);
        this.instanceByName.keySet().forEach(this.cmbInstances::addItem);

    }

    private void setNominalParameters(final List<String> parametersNames) {

        this.pnlNominalParameters.setVisible(!parametersNames.isEmpty());
        final GridBagConstraints c = new GridBagConstraints();

        this.txtFieldByParameterName.clear();
        this.pnlNominalParameters.removeAll();

        for (int i = 0; i < parametersNames.size(); i++) {

            final JLabel lblParameter = new JLabel(parametersNames.get(i) + ":");
            lblParameter.setFont(lblParameter.getFont().deriveFont(Font.PLAIN));

            c.weightx = 0;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlNominalParameters.add(lblParameter, c);

            final JTextField txtFieldParameter = new JTextField(1);
            this.txtFieldByParameterName.put(parametersNames.get(i) , txtFieldParameter);

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 1;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlNominalParameters.add(txtFieldParameter, c);

        }

    }


    private void setNumericParameters(final List<String> parametersNames) {

        this.pnlNumericParameters.setVisible(!parametersNames.isEmpty());
        final GridBagConstraints c = new GridBagConstraints();

        this.spinnerByParameterName.clear();
        this.pnlNumericParameters.removeAll();

        for (int i = 0; i < parametersNames.size(); i++) {

            final JLabel lblParameter = new JLabel(parametersNames.get(i) + ":");
            lblParameter.setFont(lblParameter.getFont().deriveFont(Font.PLAIN));

            c.weightx = 0;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlNumericParameters.add(lblParameter, c);

            final SpinnerNumberModel model = new SpinnerNumberModel(0.0, -Double.MAX_VALUE, Double.MAX_VALUE,0.1);
            final JSpinner spinnerParameter = new JSpinner(model);
            ((JSpinner.NumberEditor) spinnerParameter.getEditor()).getTextField().setColumns(1);
            this.spinnerByParameterName.put(parametersNames.get(i) , spinnerParameter);

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 1;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlNumericParameters.add(spinnerParameter, c);

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
