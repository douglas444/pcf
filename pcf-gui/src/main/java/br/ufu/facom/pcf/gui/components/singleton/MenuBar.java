package br.ufu.facom.pcf.gui.components.singleton;

import br.ufu.facom.pcf.core.*;
import br.ufu.facom.pcf.gui.components.TextAreaOutputStream;
import br.ufu.facom.pcf.gui.persistence.XMLConfiguration;
import br.ufu.facom.pcf.gui.components.ChooserWithReplaceWarning;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuBar extends JMenuBar {

    private static MenuBar instance;

    private static final String lineFormat = "\n%1$9s, %2$9s, %3$9s, %4$9s, %5$9s, %6$9s, %7$9s, " +
            "%8$9s, %9$9s, %10$9s, %11$9s, %12$9s";

    private final JMenuItem itemRun;
    private final JMenuItem itemNew;
    private final JMenuItem itemLoad;
    private final JMenuItem itemSave;
    private final JMenuItem itemExit;
    private final JMenu menuFile;
    private final JMenu menuHelp;
    private final JMenu menuRun;

    private Interceptable interceptable;

    private MenuBar() {

        this.itemRun = new JMenuItem("Run...");
        this.itemNew = new JMenuItem("New");
        this.itemLoad = new JMenuItem("Load");
        this.itemSave = new JMenuItem("Save");
        this.itemExit = new JMenuItem("Exit");
        this.menuFile = new JMenu("File");
        this.menuHelp = new JMenu("Help");
        this.menuRun = new JMenu("Run");

        menuFile.add(itemNew);
        menuFile.add(itemLoad);
        menuFile.add(itemSave);
        menuFile.add(itemExit);
        menuRun.add(itemRun);
        this.add(menuFile);
        this.add(menuRun);
        this.add(menuHelp);

        this.configureBehavior();

    }

    public static MenuBar getInstance() {

        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;

    }

    private void configureBehavior() {

        this.itemExit.addActionListener((event) -> {

        });

        this.itemLoad.addActionListener((event) -> {

            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load");

            if (fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {

                try {

                    final File f = fileChooser.getSelectedFile();
                    final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                    final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    final XMLConfiguration configuration = (XMLConfiguration) jaxbUnmarshaller.unmarshal(f);

                    FinderPanel.getInstance().load(configuration);
                    ConfigurationPanel.getInstance().load(configuration);
                    VariationPanel.getInstance().load(configuration);

                } catch (Exception e) {
                    final String message = e.getMessage() + "\n    " + ExceptionUtils.getRootCauseMessage(e);
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.itemSave.addActionListener((event) -> {

            final XMLConfiguration configuration = new XMLConfiguration();

            FinderPanel.getInstance().save(configuration);
            ConfigurationPanel.getInstance().save(configuration);
            VariationPanel.getInstance().save(configuration);

            try{

                final JFileChooser fileChooser = new ChooserWithReplaceWarning();
                fileChooser.setSelectedFile(new File("pcf-config.xml"));
                fileChooser.setDialogTitle("Save");

                if (fileChooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {

                    final JAXBContext jaxbContext = JAXBContext.newInstance(XMLConfiguration.class);
                    final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    jaxbMarshaller.marshal(configuration, fileChooser.getSelectedFile());

                    JOptionPane.showMessageDialog(null, "File saved", null,
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                final String message = e.getMessage() + "\n    " + ExceptionUtils.getRootCauseMessage(e);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        this.itemNew.addActionListener((event) -> {

            FinderPanel.getInstance().reset();
            ConfigurationPanel.getInstance().reset();
            VariationPanel.getInstance().reset();

        });

        this.itemRun.addActionListener((event) -> {

            OutputPanel.getInstance().clear();

            if (this.interceptable != null) {
                this.interceptable.stop();
            }

            this.interceptable = (Interceptable) ConfigurationPanel.getInstance()
                    .getInterceptableConfigurator().configureAndGet();

            final Thread thread = new Thread(() -> {

                FooterPanel.getInstance().setVisible(true);

                final HighLevelCategorizer highLevelCategorizer = (HighLevelCategorizer) ConfigurationPanel.getInstance()
                        .getHighLevelCategorizerConfigurator().configureAndGet();

                final LowLevelCategorizer lowLevelCategorizer = (LowLevelCategorizer) ConfigurationPanel.getInstance()
                        .getLowLevelCategorizerConfigurator().configureAndGet();

                if (VariationPanel.getInstance().isUnique()) {

                    PrintStream originalStdOut = System.out;
                    PrintStream originalStdErr = System.out;

                    System.setOut(new PrintStream(new TextAreaOutputStream(OutputPanel.getInstance().getTxtArea())));
                    System.setErr(new PrintStream(new TextAreaOutputStream(OutputPanel.getInstance().getTxtArea())));

                    execute(this.interceptable, highLevelCategorizer, lowLevelCategorizer);

                    System.setOut(originalStdOut);
                    System.setErr(originalStdErr);

                } else {

                    PrintStream originalStdErr = System.out;

                    System.setErr(new PrintStream(new TextAreaOutputStream(OutputPanel.getInstance().getTxtArea())));

                    execute(
                            this.interceptable,
                            highLevelCategorizer,
                            lowLevelCategorizer,
                            VariationPanel.getInstance().getTimes(),
                            VariationPanel.getInstance().getIncrement(),
                            VariationPanel.getInstance().getParameter(),
                            VariationPanel.getInstance().getConfigurable());

                    System.setErr(originalStdErr);

                }
                FooterPanel.getInstance().setVisible(false);

            });

            thread.setDaemon(true);
            OutputDialog.getInstance().setVisible(true);
            thread.start();
        });

        this.menuFile.addActionListener((event) -> {
        });

        this.menuRun.addActionListener((event) -> {
        });

    }

    public static void execute(final Interceptable interceptable,
                               final HighLevelCategorizer highLevelCategorizer,
                               final LowLevelCategorizer lowLevelCategorizer) {

        final Interceptor interceptor = new Interceptor(highLevelCategorizer, lowLevelCategorizer);

        if (interceptable.execute(interceptor)) {
            final EvaluationSummary evaluationSummary = new EvaluationSummary(interceptor.getLogs());
            System.out.println(evaluationSummary.toString());
        } else {
            System.out.println("Application execution interrupted!");
        }

    }

    public static void execute(final Interceptable interceptable,
                               final HighLevelCategorizer highLevelCategorizer,
                               final LowLevelCategorizer lowLevelCategorizer,
                               final int times,
                               final double increment,
                               final String parameter,
                               final Configurable configurable) {

        final Interceptor interceptor = new Interceptor(highLevelCategorizer, lowLevelCategorizer);
        final List<String> headers = new ArrayList<>();
        headers.add("v_param");
        headers.addAll(EvaluationSummary.getHeaders());
        OutputPanel.getInstance().getTxtArea().append(String.format(lineFormat, headers.toArray()));

        for (int i = 0; i < times; ++i) {

            if (interceptable.execute(interceptor)) {

                final double parameterValue = configurable.getNumericParameters().get(parameter);
                final EvaluationSummary evaluationSummary = new EvaluationSummary(interceptor.getLogs());
                List<Double> values = new ArrayList<>();
                values.add(parameterValue);
                values.addAll(evaluationSummary.getValues());
                final DecimalFormat df = new DecimalFormat("#.0000");
                values = values.stream().map(value -> {
                    if (!value.isNaN() && !value.isInfinite()) {
                        return new Double(df.format(value));
                    }
                    return value;
                }).collect(Collectors.toList());
                OutputPanel.getInstance().getTxtArea().append(String.format(lineFormat, values.toArray()));
                configurable.getNumericParameters().put(parameter,parameterValue + increment);

            } else {
                OutputPanel.getInstance().getTxtArea().append("\nApplication execution interrupted!");
                break;
            }
        }

    }
}
