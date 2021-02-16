package br.ufu.facom.pcf.core;

public class Application {

    public static void execute(final Interceptable interceptable,
                               final HighLevelCategorizer highLevelCategorizer,
                               final LowLevelCategorizer lowLevelCategorizer) {

        final Interceptor interceptor = new Interceptor(highLevelCategorizer, lowLevelCategorizer);
        interceptable.execute(interceptor);
        final ResultSummary resultSummary = new ResultSummary(interceptor.getLogs());
        System.out.println(resultSummary.toString());

    }

}
