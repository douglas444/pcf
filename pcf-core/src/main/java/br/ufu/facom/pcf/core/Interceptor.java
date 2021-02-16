package br.ufu.facom.pcf.core;

import java.util.ArrayList;
import java.util.List;

public class Interceptor {

    private final HighLevelCategorizer highLevelCategorizer;
    private final LowLevelCategorizer lowLevelCategorizer;
    private final List<Log> logs;

    public Interceptor(final HighLevelCategorizer highLevelCategorizer, final LowLevelCategorizer lowLevelCategorizer) {
        this.highLevelCategorizer = highLevelCategorizer;
        this.lowLevelCategorizer = lowLevelCategorizer;
        this.logs = new ArrayList<>();
    }

    private boolean validateContext(final Context context) {
        return true;
    }

    public void intercepting(final Context context) {

        if (!validateContext(context)) {
            throw new IllegalArgumentException();
        }

        final Category highLevelCategoryPrediction = this.highLevelCategorizer.categorize(context);
        final CategorizationLabel categorizationLabel;
        final Category lowLevelCategoryPrediction;

        if (highLevelCategoryPrediction == context.getPredictedCategory()) {
            categorizationLabel = CategorizationLabel.RELIABLE;
            lowLevelCategoryPrediction = null;
        } else {
            categorizationLabel = CategorizationLabel.RISKY;
            lowLevelCategoryPrediction = lowLevelCategorizer.categorize(context);
        }

        this.logs.add(new Log(context.getRealCategory(),
                context.getPredictedCategory(),
                highLevelCategoryPrediction,
                lowLevelCategoryPrediction,
                categorizationLabel));
    }

    public List<Log> getLogs() {return logs;}
}
