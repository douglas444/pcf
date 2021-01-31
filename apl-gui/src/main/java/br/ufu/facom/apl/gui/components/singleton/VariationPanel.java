package br.ufu.facom.apl.gui.components.singleton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class VariationPanel extends JPanel {

    private static VariationPanel instance;

    private final JRadioButton rbUnique;
    private final JRadioButton rbMultiple;
    private final JPanel pnlVariateParameter;
    private final JPanel pnlVariationBorder;
    private final ButtonGroup bgRadioParameter;
    private final JSpinner spinnerIncrement;
    private final JSpinner spinnerTimes;

    private VariationPanel() {

        this.rbUnique = new JRadioButton("Unique");
        this.rbMultiple = new JRadioButton("Multiple");
        this.pnlVariateParameter = new JPanel(new GridBagLayout());
        this.pnlVariationBorder = new JPanel(new GridBagLayout());
        this.bgRadioParameter = new ButtonGroup();
        this.spinnerIncrement = new JSpinner(new SpinnerNumberModel(0.0, -Double.MAX_VALUE, Double.MAX_VALUE,0.1));
        this.spinnerTimes = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1));

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
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Variate parameter");

        this.pnlVariateParameter.setBorder(border);

        c.weightx = 0.666;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        pnlExecution.add(this.pnlVariateParameter, c);

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
        c.insets = new Insets(0, 0, 0, 0);
        pnlExecution.add(pnlVariationBorder, c);

        this.pnlVariateParameter.setVisible(false);
        this.pnlVariationBorder.setVisible(false);
        this.setVisible(false);
        this.rbUnique.setSelected(true);
    }

    public static VariationPanel getInstance() {

        if (instance == null) {
            instance = new VariationPanel();
        }
        return instance;

    }

    public void setVariateParametersList() {

        this.pnlVariateParameter.removeAll();

        final Enumeration<AbstractButton> enumeration = this.bgRadioParameter.getElements();
        while (enumeration.hasMoreElements()) {
            this.bgRadioParameter.remove(enumeration.nextElement());
        }

        final List<String> interceptableParametersNames = new ArrayList<>(ConfigurationPanel.getInstance()
                .getInterceptableConfigurator().getNumericParameterValueByName().keySet());

        final List<String> metaCategorizerParametersNames = new ArrayList<>(ConfigurationPanel.getInstance()
                .getMetaCategorizerConfigurator().getNumericParameterValueByName().keySet());

        final List<String> activeLearningStrategyParametersNames = new ArrayList<>(ConfigurationPanel.getInstance()
                .getActiveLearningStrategyConfigurator().getNumericParameterValueByName().keySet());

        final GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariateParameter.add(setVariateParametersList(interceptableParametersNames,
                this.bgRadioParameter, "Interceptor"), c);

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariateParameter.add(setVariateParametersList(metaCategorizerParametersNames,
                this.bgRadioParameter, "MetaCategorizer"), c);

        c.weightx = 0.333;
        c.weighty = 0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 0, 0, 0);
        this.pnlVariateParameter.add(setVariateParametersList(activeLearningStrategyParametersNames,
                this.bgRadioParameter, "Active Learning Strategy"), c);


    }

    private static JPanel setVariateParametersList(final List<String> parametersNames,
                                                 final ButtonGroup buttonGroup,
                                                 final String title) {

        final GridBagConstraints c = new GridBagConstraints();
        final JPanel pnlRadioSection = new JPanel(new GridBagLayout());

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), title);

        pnlRadioSection.setBorder(border);

        for (int i = 0; i < parametersNames.size(); i++) {

            final JRadioButton radio = new JRadioButton(parametersNames.get(i));
            radio.setFont(radio.getFont().deriveFont(Font.PLAIN));

            buttonGroup.add(radio);
            if (buttonGroup.getSelection() == null) {
                radio.setSelected(true);
            }

            c.weightx = 0;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(0, 0, 0, 0);
            pnlRadioSection.add(radio, c);
        }
        return pnlRadioSection;

    }

    public JRadioButton getRbUnique() {
        return rbUnique;
    }

    public JRadioButton getRbMultiple() {
        return rbMultiple;
    }

    public JPanel getPnlVariateParameter() {
        return pnlVariateParameter;
    }

    public JPanel getPnlVariationBorder() {
        return pnlVariationBorder;
    }

    public ButtonGroup getBgRadioParameter() {
        return bgRadioParameter;
    }

    public JSpinner getSpinnerIncrement() {
        return spinnerIncrement;
    }

    public JSpinner getSpinnerTimes() {
        return spinnerTimes;
    }

    static {

        final ActionListener typeExecutionChange = (e) -> {
            if (VariationPanel.getInstance().getRbUnique().isSelected()) {
                VariationPanel.getInstance().getPnlVariateParameter().setVisible(false);
                VariationPanel.getInstance().getPnlVariationBorder().setVisible(false);
            } else {
                VariationPanel.getInstance().getPnlVariateParameter().setVisible(true);
                VariationPanel.getInstance().getPnlVariationBorder().setVisible(true);
            }
        };

        VariationPanel.getInstance().getRbUnique().addActionListener(typeExecutionChange);
        VariationPanel.getInstance().getRbMultiple().addActionListener(typeExecutionChange);
    }

}
