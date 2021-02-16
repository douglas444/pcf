package br.ufu.facom.pcf.core;

import java.util.List;

public class ResultSummary {

    private final int trueKnown;
    private final int falseKnown;
    private final int trueNovelty;
    private final int falseNovelty;

    private final int trueUnreliable;
    private final int falseUnreliable;
    private final int trueReliable;
    private final int falseReliable;

    private final int recovered;
    private final int corrupted;
    private final int uncorrupted;

    public ResultSummary(final List<Log> results) {

        if (results.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.trueUnreliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.TRUE_RISKY)
                .count();

        this.falseUnreliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RISKY)
                .count();

        this.trueReliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.TRUE_RELIABLE)
                .count();

        this.falseReliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RELIABLE)
                .count();

        this.trueKnown = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getHighLevelPredictedCategory())
                .filter(result -> result.getHighLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.falseKnown = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getHighLevelPredictedCategory())
                .filter(result -> result.getHighLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.trueNovelty = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getHighLevelPredictedCategory())
                .filter(result -> result.getHighLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.falseNovelty = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getHighLevelPredictedCategory())
                .filter(result -> result.getHighLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.recovered = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.TRUE_RISKY)
                .filter(result -> result.getRealCategory() == result.getLowLevelPredictedCategory())
                .count();

        this.corrupted = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RISKY)
                .filter(result -> result.getRealCategory() != result.getLowLevelPredictedCategory())
                .count();

        this.uncorrupted = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RISKY)
                .filter(result -> result.getRealCategory() == result.getLowLevelPredictedCategory())
                .count();

    }

    public double calculatePrecision() {
        return this.trueUnreliable / (double) (this.trueUnreliable + this.falseUnreliable);
    }

    public double calculateRecall() {
        return this.trueUnreliable / (double) (this.trueUnreliable + this.falseReliable);
    }

    public double calculateF1() {
        final double recall = calculateRecall();
        final double precision = calculatePrecision();
        return 2 * recall * precision / (recall + precision);
    }

    public int calculateTotal() {
        return this.trueUnreliable + this.falseUnreliable + this.trueReliable + this.falseReliable;
    }

    public int calculatePositives() {
        return this.trueUnreliable + this.falseReliable;
    }


    public String ReliabilityClassifierConfusionMatrix() {
        return "Confusion Matrix{" +
                "trueUnreliable=" + this.trueUnreliable +
                ", falseUnreliable=" + this.falseUnreliable +
                ", trueReliable=" + this.trueReliable +
                ", falseReliable=" + this.falseReliable +
                "}";
    }

    public String highLevelCategorizerConfusionMatrix() {
        return "Confusion Matrix{" +
                "trueKnown=" + this.trueKnown +
                ", falseKnown=" + this.falseKnown +
                ", trueNovelty=" + this.trueNovelty +
                ", falseNovelty=" + this.falseNovelty +
                "}";
    }

    public double calculateAccuracyForNoveltyPrediction_indicator() {
        return this.trueNovelty / (double) (this.trueNovelty + this.falseKnown);
    }

    public double calculateAccuracyForKnownPrediction_indicator() {
        return this.trueKnown / (double) (this.trueKnown + this.falseNovelty);
    }

    public double calculateLowLevelStrategyAccuracy() {
        return (this.recovered + this.uncorrupted) / (double) (this.trueUnreliable + this.falseUnreliable);
    }

    public double calculateErrorRecovery() {
        return this.recovered / (double) (this.trueUnreliable + this.falseReliable);
    }

    public double calculateErrorIntroduction() {
        return this.corrupted / (double) (this.trueUnreliable + this.falseReliable);
    }

    @Override
    public String toString() {

        return
                ">>> Results for the Reliability Classifier:" +
                "\nNumber of interceptions: " + calculateTotal() +
                "\nTrue Unreliable: " + calculatePositives() +
                "\nPrecision: " + calculatePrecision() +
                "\nRecall: " + calculateRecall() +
                "\nF1: " + calculateF1() +
                "\n" + ReliabilityClassifierConfusionMatrix() +
                "\n\n>>> Results for the High-Level Categorizer:" +
                "\nAccuracy for known patterns: " + calculateAccuracyForKnownPrediction_indicator() +
                "\nAccuracy for novel patterns: " + calculateAccuracyForNoveltyPrediction_indicator() +
                "\n" + highLevelCategorizerConfusionMatrix() +
                "\n\n>>> Results for the Low-Level Categorizer:" +
                "\nAccuracy: " + calculateLowLevelStrategyAccuracy() +
                "\nConsultations: " + (this.trueUnreliable + this.falseUnreliable) +
                "\n\nFinal measures:" +
                "\nError Recovery: " + calculateErrorRecovery() +
                "\nError Introduction: " + calculateErrorIntroduction();



    }
}
