package br.ufu.facom.pcf.gui.service;

import br.ufu.facom.pcf.core.*;
import br.ufu.facom.pcf.gui.components.singleton.ConfigurationPanel;
import br.ufu.facom.pcf.gui.components.singleton.OutputPanel;
import br.ufu.facom.pcf.gui.exception.ServiceException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutionController {

    private static final String lineFormat = "\n%1$9s, %2$9s, %3$9s, %4$9s, %5$9s, %6$9s, %7$9s, " +
            "%8$9s, %9$9s, %10$9s, %11$9s, %12$9s, %13$9s, %14$9s, %15$9s, %16$9s";

    public static void stop() throws ServiceException {

        try {
            final Interceptable interceptable = (Interceptable) ConfigurationPanel.getInstance()
                    .getInterceptableConfigurator().get();
            if (interceptable != null) {
                interceptable.stop();
            }
        } catch (Exception exception) {
            throw new ServiceException("Error while interrupting execution.", exception);
        }

    }

    public static void execute(final Interceptable interceptable,
                               final HighLevelCategorizer highLevelCategorizer,
                               final LowLevelCategorizer lowLevelCategorizer) throws ServiceException {

        try {
            final Interceptor interceptor = new Interceptor(highLevelCategorizer, lowLevelCategorizer);

            if (interceptable.execute(interceptor)) {
                final EvaluationSummary evaluationSummary = new EvaluationSummary(interceptor.getLogs());
                System.out.println(evaluationSummary.toString());
            } else {
                System.out.println("Application execution interrupted!");
            }
        } catch (Exception exception) {
            throw new ServiceException("Error while executing.", exception);
        }
    }

    public static void execute(final Interceptable interceptable,
                               final HighLevelCategorizer highLevelCategorizer,
                               final LowLevelCategorizer lowLevelCategorizer,
                               final int times,
                               final double increment,
                               final String parameter,
                               final Configurable configurable) throws ServiceException {

        try {
            final List<String> headers = new ArrayList<>();
            headers.add("v_param");
            headers.addAll(EvaluationSummary.getHeaders());
            OutputPanel.getInstance().getTxtArea().append(String.format(lineFormat, headers.toArray()));

            for (int i = 0; i < times; ++i) {

                final Interceptor interceptor = new Interceptor(highLevelCategorizer, lowLevelCategorizer);
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
        } catch (Exception exception) {
            throw new ServiceException("Error while executing.", exception);
        }
    }

}
