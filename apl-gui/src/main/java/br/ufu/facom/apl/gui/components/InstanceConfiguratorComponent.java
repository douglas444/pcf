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
    private final JComboBox<String> cmbInstances;
    private final HashMap<String, JTextField> txtFieldsByParameterName;
    private final HashMap<String, JTextField> txtFieldsIncrementByParameterName;
    private final HashMap<String, Object> instanceByName;

    public InstanceConfiguratorComponent(final String title) {

        this.setLayout(new GridBagLayout());

        this.pnlParameters = new JPanel(new GridBagLayout());
        this.cmbInstances = new JComboBox<>();
        this.txtFieldsByParameterName = new HashMap<>();
        this.txtFieldsIncrementByParameterName = new HashMap<>();
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

    }

    private void arrangeComponents() {

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        this.add(this.cmbInstances, c);

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
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

            if (instance instanceof Configurable) {

                final Configurable configurable = (Configurable) instance;
                final List<String> parametersNames = configurable.getParametersNames();
                this.setParameters(parametersNames);

            }
        });

    }

    public void setCmbInstances(final HashMap<String, Object> instanceByName) {

        this.instanceByName.putAll(instanceByName);
        this.instanceByName.keySet().forEach(this.cmbInstances::addItem);

    }

    private void setParameters(final List<String> parametersNames) {

        this.pnlParameters.setVisible(!parametersNames.isEmpty());
        final GridBagConstraints c = new GridBagConstraints();

        this.txtFieldsByParameterName.clear();
        this.pnlParameters.removeAll();

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
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlParameters.add(lblParameter, c);

            final JTextField txtFieldParameter = new JTextField(1);
            this.txtFieldsByParameterName.put(parametersNames.get(i) , txtFieldParameter);

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 1;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlParameters.add(txtFieldParameter, c);



            final JLabel lblIncrement = new JLabel("Inc.: ");
            lblIncrement.setFont(lblParameter.getFont().deriveFont(Font.PLAIN));

            c.weightx = 0;
            c.weighty = 0;
            c.gridx = 2;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlParameters.add(lblIncrement, c);

            final JTextField txtFieldIncrement = new JTextField(1);
            this.txtFieldsIncrementByParameterName.put(parametersNames.get(i) , txtFieldIncrement);

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 3;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(2, 2, 2, 2);
            this.pnlParameters.add(txtFieldIncrement, c);
        }

    }

    public Object getSelectedInstance() {
        final String selectedItem = (String) this.cmbInstances.getSelectedItem();
        return this.instanceByName.getOrDefault(selectedItem, null);
    }

    public HashMap<String, String> getParameterValueByName() {

        final HashMap<String, String> parameterValueByName = new HashMap<>();
        this.txtFieldsByParameterName.forEach((parameterName, txtField) -> {
            parameterValueByName.put(parameterName, txtField.getText());
        });
        return parameterValueByName;

    }

    public HashMap<String, String> getParameterIncrementByName() {

        final HashMap<String, String> parameterIncrementByName = new HashMap<>();
        this.txtFieldsIncrementByParameterName.forEach((parameterName, txtField) -> {
            parameterIncrementByName.put(parameterName, txtField.getText());
        });
        return parameterIncrementByName;

    }
}
