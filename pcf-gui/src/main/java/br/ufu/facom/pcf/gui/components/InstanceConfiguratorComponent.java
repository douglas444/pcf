package br.ufu.facom.pcf.gui.components;

import br.ufu.facom.pcf.core.Configurable;
import br.ufu.facom.pcf.gui.components.singleton.ConfigurationPanel;
import br.ufu.facom.pcf.gui.components.singleton.MainFrame;
import br.ufu.facom.pcf.gui.components.singleton.VariationPanel;
import br.ufu.facom.pcf.gui.exception.CustomExceptionMessage;
import br.ufu.facom.pcf.gui.service.SpinnerUtil;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

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
                try {
                    numericParameters = new ArrayList<>(configurable.getNumericParameters().keySet());
                    nominalParameters = new ArrayList<>(configurable.getNominalParameters().keySet());
                } catch (Exception exception) {

                    final String message = "Error while calling getNumericParameters or " +
                            "getNominalParameters from Configurable: "
                            + exception.getMessage() + CustomExceptionMessage.build(exception);

                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (numericParameters != null) {
                this.setParameters(numericParameters, true);
                final Configurable configurable = (Configurable) instance;
                try {
                    configurable.getNumericParameters().forEach((key, value) ->
                            this.spinnerByParameterName.get(key).setValue(value));
                } catch (Exception exception) {

                    final String message = "Error while calling getNumericParameters "
                            + "or getNominalParameters from Configurable: "
                            + exception.getMessage()
                            + CustomExceptionMessage.build(exception);

                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (nominalParameters != null) {
                this.setParameters(nominalParameters, false);
                final Configurable configurable = (Configurable) instance;

                try {
                    configurable.getNominalParameters().forEach((key, value) ->
                            this.txtFieldByParameterName.get(key).setText(value));
                } catch (Exception exception) {

                    final String message = "Error while calling getNumericParameters or "
                            + "getNominalParameters from Configurable: "
                            + exception.getMessage()
                            + CustomExceptionMessage.build(exception);

                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            this.pnlNumericParameters.setVisible(
                    !this.getNumericParameterValueByName().isEmpty());
            this.pnlNominalParameters.setVisible(
                    !this.getNominalParameterValueByName().isEmpty());

            VariationPanel.getInstance().setVisible(
                    ConfigurationPanel
                            .getInstance()
                            .hasNumericParameters());

            VariationPanel.getInstance().setVariableParametersList();

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

                final SpinnerNumberModel model = new SpinnerNumberModel(0.0, -Double.MAX_VALUE,
                        Double.MAX_VALUE,0.1);

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
        panel.revalidate();
        panel.repaint();

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

    public Object get() {

        final String selectedItem = (String) this.cmbInstances.getSelectedItem();
        return this.instanceByName.getOrDefault(selectedItem, null);
    }

    public Object configureAndGet() {

        final String selectedItem = (String) this.cmbInstances.getSelectedItem();
        final Object selectedInstance = this.instanceByName.getOrDefault(selectedItem, null);

        if (selectedInstance instanceof Configurable) {
            try {
                ((Configurable) selectedInstance)
                        .getNominalParameters()
                        .putAll(this.getNominalParameterValueByName());
                ((Configurable) selectedInstance)
                        .getNumericParameters()
                        .putAll(this.getNumericParameterValueByName());
            } catch (Exception exception) {

                final String message = "Error while calling getNumericParameters or "
                        + "getNominalParameters from Configurable: "
                        + exception.getMessage()
                        + CustomExceptionMessage.build(exception);

                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return selectedInstance;
    }

    public HashMap<String, String> getNominalParameterValueByName() {

        final HashMap<String, String> parameterValueByName = new HashMap<>();
        this.txtFieldByParameterName.forEach((parameterName, txtField) ->
                parameterValueByName.put(parameterName, txtField.getText()));
        return parameterValueByName;

    }

    public HashMap<String, Double> getNumericParameterValueByName() {

        final HashMap<String, Double> parameterValueByName = new HashMap<>();
        this.spinnerByParameterName.forEach((parameterName, spinner) -> {
            SpinnerUtil.commitSpinner(spinner, parameterName);
            parameterValueByName.put(parameterName, ((Double) spinner.getValue()));
        });
        return parameterValueByName;

    }

    public String getSelectedItem() {
        return (String) this.cmbInstances.getSelectedItem();
    }

    public void setSelectedItem(final String selectedItem) {
        this.cmbInstances.setSelectedItem(selectedItem);
    }

    public void reset() {

        this.pnlNumericParameters.setVisible(false);
        this.pnlNominalParameters.setVisible(false);

        this.pnlNumericParameters.removeAll();
        this.pnlNumericParameters.revalidate();
        this.pnlNumericParameters.repaint();

        this.pnlNominalParameters.removeAll();
        this.pnlNominalParameters.revalidate();
        this.pnlNominalParameters.repaint();

        this.cmbInstances.removeAllItems();
        this.txtFieldByParameterName.clear();
        this.spinnerByParameterName.clear();
        this.instanceByName.clear();
    }

    public void load(final HashMap<String, String> valueByNominalParameterName,
                     final HashMap<String, Double> valueByNumericParameterName) {

        valueByNominalParameterName.forEach((key, value) -> {
            if (this.txtFieldByParameterName.containsKey(key)) {
                this.txtFieldByParameterName.get(key).setText(value);
            }
        });

        valueByNumericParameterName.forEach((key, value) -> {
            if (this.spinnerByParameterName.containsKey(key)) {
                this.spinnerByParameterName.get(key).setValue(value);
            }
        });

    }
}
