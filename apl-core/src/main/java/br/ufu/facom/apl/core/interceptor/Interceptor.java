package br.ufu.facom.apl.core.interceptor;

import br.ufu.facom.apl.core.ActiveLearningStrategy;
import br.ufu.facom.apl.core.MetaCategorizer;

import java.util.ArrayList;
import java.util.List;

public class Interceptor {

    private MetaCategorizer metaCategorizer;
    private ActiveLearningStrategy activeLearningStrategy;
    private List<Log> logs;

    public Interceptor(final MetaCategorizer metaCategorizer, final ActiveLearningStrategy activeLearningStrategy) {
        this.metaCategorizer = metaCategorizer;
        this.activeLearningStrategy = activeLearningStrategy;
        this.logs = new ArrayList<>();
    }

    public void execute(final Context context) {
        logs.add(new Log());
    }

    public List<Log> getLogs() {return logs;}
}
