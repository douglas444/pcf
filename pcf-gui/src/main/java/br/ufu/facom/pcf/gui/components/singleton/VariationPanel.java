package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.core.Configurable;
import br.ufu.facom.pcf.core.HighLevelCategorizer;
import br.ufu.facom.pcf.core.Interceptable;
import br.ufu.facom.pcf.core.LowLevelCategorizer;
import br.ufu.facom.pcf.gui.service.SpinnerUtil;
import br.ufu.facom.pcf.gui.service.persistence.Persistent;
import br.ufu.facom.pcf.gui.service.persistence.XMLConfiguration;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VariationPanel extends JPanel implements Persistent {

    private static VariationPanel instance;

    private final JRadioButton rbUnique;
    private final JRadioButton rbMultiple;
    private final JPanel pnlVariableParameter;
    private final JPanel pnlVariationBorder;
    private final ButtonGroup bgRadioParameter;
    private final JSpinner spinnerIncrement;
    private final JSpinner spinnerTimes;
    private final List<JRadioButton> radioButtonInterceptableParameters;
    private final List<JRadioButton> radioButtonHighLevelCategorizerParameters;
    private final List<JRadioButton> radioButtonLowLevelCategorizerParameters;

    private VariationPanel() {

        this.radioButtonInterceptableParameters = new ArrayList<>();
        this.radioButtonHighLevelCategorizerParameters = new ArrayList<>();
        this.radioButtonLowLevelCategorizerParameters = new ArrayList<>();
        this.rbUnique = new JRadioButton("Unique");
        this.rbMultiple = new JRadioButton("Multiple");
        this.pnlVariableParameter = new JPanel(new GridBagLayout());
        this.pnlVariationBorder = new JPanel(new GridBagLayout());
        this.bgRadioParameter = new ButtonGroup();

        this.spinnerIncrement = new JSpinner(
                new SpinnerNumberModel(0.0, -Double.MAX_VALUE, Double.MAX_VALUE,0.1));

        this.spinnerTimes = new JSpinner(
                new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1));

        ((JSpinner.NumberEditor) spinnerIncrement.getEditor()).getTextField().setColumns(1);
        ((JSpinner.NumberEditor) spinnerTimes.getEditor()).getTextField().setColumns(1);

        this.setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Execution");

        final JPanel pnlExecution = new JPanel(new GridBagLayout());
        pnlExecution.setBorder(border);

        border = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Variation");

        pnlVariationBorder.setBorder(border);

        final ButtonGroup bgRadioExecutionType = new ButtonGroup();
        bgRadioExecutionType.add(this.rbUnique);
        bgRadioExecutionType.add(this.rbMultiple);

        final JPanel pnlExecutionType = new JPanel(new GridBagLayout());

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 20, 0, 0);
        pnlExecutionType.add(this.rbUnique, c);

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 20, 0, 0);
        pnlExecutionType.add(this.rbMultiple, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 0, 10, 0);
        pnlExecution.add(pnlExecutionType, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(pnlExecution, c);

        border = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Variable parameter");

        this.pnlVariableParameter.setBorder(border);

        c.weightx = 0.666;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        pnlExecution.add(this.pnlVariableParameter, c);

        final JLabel lblIncrement = new JLabel("Increment:");
        lblIncrement.setFont(lblIncrement.getFont().deriveFont(Font.PLAIN));

        final JLabel lblTimes = new JLabel("Times:");
        lblTimes.setFont(lblTimes.getFont().deriveFont(Font.PLAIN));

        final JPanel pnlVariation = new JPanel(new GridBagLayout());

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(2, 2, 2, 2);
        pnlVariation.add(lblIncrement, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 2, 2, 2);
        pnlVariation.add(this.spinnerIncrement, c);

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(2, 2, 2, 2);
        pnlVariation.add(lblTimes, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 2, 2, 2);
        pnlVariation.add(this.spinnerTimes, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        pnlVariationBorder.add(pnlVariation, c);

        c.weightx = 0.333;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        pnlExecution.add(pnlVariationBorder, c);

        this.pnlVariableParameter.setVisible(false);
        this.pnlVariationBorder.setVisible(false);
        this.setVisible(false);
        this.rbUnique.setSelected(true);

        this.configureBehavior();
    }

    public static VariationPanel getInstance() {

        if (instance == null) {
            instance = new VariationPanel();
        }
        return instance;

    }

    public void setVariableParametersList() {

        this.pnlVariableParameter.removeAll();
        this.pnlVariableParameter.revalidate();
        this.pnlVariableParameter.repaint();

        this.radioButtonInterceptableParameters.forEach(this.bgRadioParameter::remove);
        this.radioButtonInterceptableParameters.clear();
        this.radioButtonHighLevelCategorizerParameters.forEach(this.bgRadioParameter::remove);
        this.radioButtonHighLevelCategorizerParameters.clear();
        this.radioButtonLowLevelCategorizerParameters.forEach(this.bgRadioParameter::remove);
        this.radioButtonLowLevelCategorizerParameters.clear();

        final List<String> interceptableParametersNames = new ArrayList<>(
                ConfigurationPanel
                        .getInstance()
                        .getInterceptableConfigurator()
                        .getNumericParameterValueByName()
                        .keySet());

        final List<String> highLevelCategorizerParametersNames = new ArrayList<>(
                ConfigurationPanel
                        .getInstance()
                        .getHighLevelCategorizerConfigurator()
                        .getNumericParameterValueByName()
                        .keySet());

        final List<String> lowLevelCategorizerParametersNames = new ArrayList<>(
                ConfigurationPanel
                        .getInstance()
                        .getLowLevelCategorizerConfigurator()
                        .getNumericParameterValueByName().keySet());

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariableParameter.add(
                setVariableParametersList(
                        interceptableParametersNames,
                        Interceptable.class), c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariableParameter.add(
                setVariableParametersList(
                        highLevelCategorizerParametersNames,
                        HighLevelCategorizer.class), c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariableParameter.add(
                setVariableParametersList(
                        lowLevelCategorizerParametersNames,
                        LowLevelCategorizer.class), c);

        if (!this.radioButtonInterceptableParameters.isEmpty()) {
            this.radioButtonInterceptableParameters.get(0).setSelected(true);
        } else if (!this.radioButtonHighLevelCategorizerParameters.isEmpty()) {
            this.radioButtonHighLevelCategorizerParameters.get(0).setSelected(true);
        } else if (!this.radioButtonLowLevelCategorizerParameters.isEmpty()) {
            this.radioButtonLowLevelCategorizerParameters.get(0).setSelected(true);
        }

    }

    private JPanel setVariableParametersList(final List<String> parametersNames, final Class<?> clazz) {

        final GridBagConstraints c = new GridBagConstraints();
        final JPanel pnlRadioSection = new JPanel(new GridBagLayout());

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), clazz.getSimpleName());

        pnlRadioSection.setBorder(border);

        for (int i = 0; i < parametersNames.size(); i++) {

            final JRadioButton radio = new JRadioButton(parametersNames.get(i));
            radio.setFont(radio.getFont().deriveFont(Font.PLAIN));

            this.bgRadioParameter.add(radio);

            if (clazz.equals(Interceptable.class)) {
                this.radioButtonInterceptableParameters.add(radio);
            } else if (clazz.equals(HighLevelCategorizer.class)) {
                this.radioButtonHighLevelCategorizerParameters.add(radio);
            } else {
                this.radioButtonLowLevelCategorizerParameters.add(radio);
            }

            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(0, 0, 0, 0);
            pnlRadioSection.add(radio, c);

        }

        pnlRadioSection.setVisible(!parametersNames.isEmpty());

        return pnlRadioSection;

    }

    @Override
    public void reset() {

        this.setVisible(false);
        this.pnlVariableParameter.setVisible(false);
        this.pnlVariationBorder.setVisible(false);
        this.rbUnique.setSelected(true);
        this.rbMultiple.setSelected(false);
        this.radioButtonInterceptableParameters.clear();
        this.radioButtonHighLevelCategorizerParameters.clear();
        this.radioButtonLowLevelCategorizerParameters.clear();
        this.spinnerIncrement.setValue(0);
        this.spinnerTimes.setValue(0);

    }

    @Override
    public void load(XMLConfiguration configuration) {

        this.rbUnique.setSelected(!configuration.isMultipleExecutions());
        this.rbMultiple.setSelected(configuration.isMultipleExecutions());
        this.pnlVariableParameter.setVisible(configuration.isMultipleExecutions());
        this.pnlVariationBorder.setVisible(configuration.isMultipleExecutions());

        final List<JRadioButton> radioButtons;

        if (Interceptable.class.getSimpleName()
                .equals(configuration.getVariableParameterType())) {

            radioButtons = radioButtonInterceptableParameters;

        } else if (HighLevelCategorizer.class.getSimpleName()
                .equals(configuration.getVariableParameterType())) {

            radioButtons = radioButtonHighLevelCategorizerParameters;

        } else {
            radioButtons = radioButtonLowLevelCategorizerParameters;
        }

        radioButtons.stream()
                .filter(radioButton -> radioButton
                        .getText()
                        .equals(configuration.getVariableParameterName()))
                .findAny()
                .ifPresent(radioButton -> radioButton.setSelected(true));

        this.spinnerIncrement.setValue(configuration.getVariationIncrement());
        this.spinnerTimes.setValue(configuration.getVariationTimes());

    }

    @Override
    public void save(XMLConfiguration configuration) {

        configuration.setMultipleExecutions(this.rbMultiple.isSelected());

        this.radioButtonInterceptableParameters.stream()
                .filter(AbstractButton::isSelected).findAny().ifPresent((btnRadio) -> {
                    configuration.setVariableParameterName(btnRadio.getText());
                    configuration.setVariableParameterType(
                            Interceptable.class.getSimpleName());
                });

        this.radioButtonHighLevelCategorizerParameters.stream()
                .filter(AbstractButton::isSelected).findAny().ifPresent((btnRadio) -> {
                    configuration.setVariableParameterName(btnRadio.getText());
                    configuration.setVariableParameterType(
                            HighLevelCategorizer.class.getSimpleName());
                });

        this.radioButtonLowLevelCategorizerParameters.stream()
                .filter(AbstractButton::isSelected).findAny().ifPresent((btnRadio) -> {
                    configuration.setVariableParameterName(btnRadio.getText());
                    configuration.setVariableParameterType(
                            LowLevelCategorizer.class.getSimpleName());
                });

        SpinnerUtil.commitSpinner(this.spinnerIncrement, "increment");
        SpinnerUtil.commitSpinner(this.spinnerTimes, "times");

        configuration.setVariationIncrement((Double) this.spinnerIncrement.getValue());
        configuration.setVariationTimes((Integer) this.spinnerTimes.getValue());

    }

    private void configureBehavior() {

        final ActionListener typeExecutionChange = (e) -> {
            this.pnlVariableParameter.setVisible(!this.rbUnique.isSelected());
            this.pnlVariationBorder.setVisible(!this.rbUnique.isSelected());
        };

        this.rbUnique.addActionListener(typeExecutionChange);
        this.rbMultiple.addActionListener(typeExecutionChange);
    }

    public boolean isUnique() {
        return this.rbUnique.isSelected();
    }

    public double getIncrement() {
        SpinnerUtil.commitSpinner(this.spinnerIncrement, "increment");
        return (Double) this.spinnerIncrement.getValue();
    }

    public int getTimes() {
        SpinnerUtil.commitSpinner(this.spinnerTimes, "times");
        return (Integer) this.spinnerTimes.getValue();
    }

    public String getParameter() {

        final List<JRadioButton> radioButtons = new ArrayList<>();
        radioButtons.addAll(this.radioButtonInterceptableParameters);
        radioButtons.addAll(this.radioButtonHighLevelCategorizerParameters);
        radioButtons.addAll(this.radioButtonLowLevelCategorizerParameters);

        for (JRadioButton radioButton : radioButtons) {
            if (this.bgRadioParameter.isSelected(radioButton.getModel())) {
                return radioButton.getText();
            }
        }

        return radioButtons.get(0).getText();
    }

    public Configurable getConfigurable() {
        for (JRadioButton radioButton : this.radioButtonInterceptableParameters) {
            if (this.bgRadioParameter.isSelected(radioButton.getModel())) {

                return (Configurable) ConfigurationPanel
                        .getInstance()
                        .getInterceptableConfigurator()
                        .get();
            }
        }
        for (JRadioButton radioButton : this.radioButtonHighLevelCategorizerParameters) {
            if (this.bgRadioParameter.isSelected(radioButton.getModel())) {
                return (Configurable) ConfigurationPanel
                        .getInstance()
                        .getHighLevelCategorizerConfigurator()
                        .get();
            }
        }
        for (JRadioButton radioButton : this.radioButtonLowLevelCategorizerParameters) {
            if (this.bgRadioParameter.isSelected(radioButton.getModel())) {
                return (Configurable) ConfigurationPanel
                        .getInstance()
                        .getLowLevelCategorizerConfigurator()
                        .get();
            }
        }
        return null;

    }
}
