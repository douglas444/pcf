package br.ufu.facom.pcf.core;

import java.util.ArrayList;
import java.util.List;

public class Interceptor {

    private final HighLevelCategorizer highLevelCategorizer;
    private final LowLevelCategorizer lowLevelCategorizer;
    private final List<Log> logs;

    public Interceptor(final HighLevelCategorizer highLevelCategorizer,
                       final LowLevelCategorizer lowLevelCategorizer) {

        this.highLevelCategorizer = highLevelCategorizer;
        this.lowLevelCategorizer = lowLevelCategorizer;
        this.logs = new ArrayList<>();
    }

    public ResponseContext intercept(final Context context) {

        final Confidence confidence;
        final Category lowLevelCategoryPrediction;
        final ResponseContext lowLevelResult;

        final Category highLevelCategoryPrediction = this.highLevelCategorizer.categorize(context);
        if (highLevelCategoryPrediction == context.getPredictedCategory()) {
            confidence = Confidence.RELIABLE;
            lowLevelCategoryPrediction = null;
            lowLevelResult = null;
        } else {
            confidence = Confidence.UNRELIABLE;
            lowLevelResult = lowLevelCategorizer.categorize(context);
            lowLevelCategoryPrediction = lowLevelResult.getCategory();
        }

        this.logs.add(new Log(context.getRealCategory(),
                context.getPredictedCategory(),
                highLevelCategoryPrediction,
                lowLevelCategoryPrediction,
                confidence));

        if (confidence == Confidence.RELIABLE) {
            return null;
        } else {
            return lowLevelResult;
        }
    }

    public List<Log> getLogs() {return logs;}
}
