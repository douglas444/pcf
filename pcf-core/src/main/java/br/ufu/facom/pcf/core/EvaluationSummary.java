package br.ufu.facom.pcf.core;

import java.util.Arrays;
import java.util.List;

public class EvaluationSummary {

    private final int highLevelTrueKnown;
    private final int highLevelFalseKnown;
    private final int highLevelTrueNovelty;
    private final int highLevelFalseNovelty;

    private final int lowLevelTrueKnown;
    private final int lowLevelFalseKnown;
    private final int lowLevelTrueNovelty;
    private final int lowLevelFalseNovelty;

    private final int trueUnreliable;
    private final int falseUnreliable;
    private final int trueReliable;
    private final int falseReliable;

    private final int recovered;
    private final int unrecovered;
    private final int corrupted;
    private final int uncorrupted;

    public EvaluationSummary(final List<Log> logs) {

        if (logs.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.trueUnreliable = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .count();

        this.falseUnreliable = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .count();

        this.trueReliable = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.RELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .count();

        this.falseReliable = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.RELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .count();

        this.highLevelTrueKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getHighLevelPredictedCategory())
                .filter(log -> log.getHighLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.highLevelFalseKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getHighLevelPredictedCategory())
                .filter(log -> log.getHighLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.highLevelTrueNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getHighLevelPredictedCategory())
                .filter(log -> log.getHighLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.highLevelFalseNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getHighLevelPredictedCategory())
                .filter(log -> log.getHighLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.lowLevelTrueKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .filter(log -> log.getLowLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.lowLevelFalseKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .filter(log -> log.getLowLevelPredictedCategory() == Category.KNOWN)
                .count();

        this.lowLevelTrueNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .filter(log -> log.getLowLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.lowLevelFalseNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .filter(log -> log.getLowLevelPredictedCategory() == Category.NOVELTY)
                .count();

        this.recovered = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .count();

        this.unrecovered = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.corrupted = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.uncorrupted = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
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

    public String confidenceMeterConfusionMatrixToString() {
        return "{" +
                "trueUnreliable=" + this.trueUnreliable +
                ", falseUnreliable=" + this.falseUnreliable +
                ", trueReliable=" + this.trueReliable +
                ", falseReliable=" + this.falseReliable +
                "}";
    }

    public String highLevelCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.highLevelTrueKnown +
                ", falseKnown=" + this.highLevelFalseKnown +
                ", trueNovelty=" + this.highLevelTrueNovelty +
                ", falseNovelty=" + this.highLevelFalseNovelty +
                "}";
    }

    public String lowLevelCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.lowLevelTrueKnown +
                ", falseKnown=" + this.lowLevelFalseKnown +
                ", trueNovelty=" + this.lowLevelTrueNovelty +
                ", falseNovelty=" + this.lowLevelFalseNovelty +
                "}";
    }

    public String recoveryConfusionMatrixToString() {
        return "{" +
                "recovered=" + this.recovered +
                ", unrecovered=" + this.unrecovered +
                ", corrupted=" + this.corrupted +
                ", uncorrupted=" + this.uncorrupted +
                "}";
    }

    public double calculateHighLevelCategorizerAccuracyForNoveltyPrediction() {
        return this.highLevelTrueNovelty / (double) (this.highLevelTrueNovelty + this.highLevelFalseKnown);
    }

    public double calculateHighLevelCategorizerAccuracyForKnownPrediction() {
        return this.highLevelTrueKnown / (double) (this.highLevelTrueKnown + this.highLevelFalseNovelty);
    }

    public double calculateHighLevelCategorizerAccuracy() {
        return (this.highLevelTrueKnown + this.highLevelTrueNovelty) /
                (double) (this.highLevelTrueKnown
                        + this.highLevelFalseKnown
                        + this.highLevelTrueNovelty
                        + this.highLevelFalseNovelty);
    }

    public double calculateLowLevelCategorizerAccuracyForNoveltyPrediction() {
        return this.lowLevelTrueNovelty / (double) (this.lowLevelTrueNovelty + this.lowLevelFalseKnown);
    }

    public double calculateLowLevelCategorizerAccuracyForKnownPrediction() {
        return this.lowLevelTrueKnown / (double) (this.lowLevelTrueKnown + this.lowLevelFalseNovelty);
    }

    public double calculateLowLevelCategorizerAccuracy() {
        return (this.lowLevelTrueKnown + this.lowLevelTrueNovelty) /
                (double) (this.lowLevelTrueKnown
                        + this.lowLevelFalseKnown
                        + this.lowLevelTrueNovelty
                        + this.lowLevelFalseNovelty);
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
                "\nCONFIDENCE METER" +
                "\nConfusion matrix: " + confidenceMeterConfusionMatrixToString() +
                "\nNumber of interceptions: " + (this.trueUnreliable + this.falseUnreliable
                        + this.trueReliable + this.falseReliable) +
                "\nPrecision: " + calculatePrecision() +
                "\nRecall: " + calculateRecall() +
                "\nF1: " + calculateF1() +
                "\n\nHIGH-LEVEL CATEGORIZER" +
                "\nCategorizer confusion matrix: " + highLevelCategorizerConfusionMatrixToString() +
                "\nAccuracy: " + calculateHighLevelCategorizerAccuracy() +
                "\nAccuracy for known patterns: " + calculateHighLevelCategorizerAccuracyForKnownPrediction() +
                "\nAccuracy for novel patterns: " + calculateHighLevelCategorizerAccuracyForNoveltyPrediction() +
                "\n\nLOW-LEVEL CATEGORIZER" +
                "\nCategorization confusion matrix: " + lowLevelCategorizerConfusionMatrixToString() +
                "\nRecovery confusion matrix: " + recoveryConfusionMatrixToString() +
                "\nAccuracy: " + calculateLowLevelCategorizerAccuracy() +
                "\nAccuracy for known patterns: " + calculateLowLevelCategorizerAccuracyForKnownPrediction() +
                "\nAccuracy for novel patterns: " + calculateLowLevelCategorizerAccuracyForNoveltyPrediction() +
                "\nConsultations: " + (this.trueUnreliable + this.falseUnreliable) +
                "\n\nFINAL MEASURES" +
                "\nError Recovery: " + calculateErrorRecovery() +
                "\nError Introduction: " + calculateErrorIntroduction();
    }

    public List<Double> getValues() {

        return Arrays.asList(
                calculatePrecision(),
                calculateRecall(),
                calculateF1(),
                calculateHighLevelCategorizerAccuracy(),
                calculateHighLevelCategorizerAccuracyForKnownPrediction(),
                calculateHighLevelCategorizerAccuracyForNoveltyPrediction(),
                calculateLowLevelCategorizerAccuracy(),
                calculateLowLevelCategorizerAccuracyForKnownPrediction(),
                calculateLowLevelCategorizerAccuracyForNoveltyPrediction(),
                (double) (this.trueUnreliable + this.falseUnreliable),
                calculateErrorRecovery(),
                calculateErrorIntroduction());
    }

    public static List<String> getHeaders() {

        return Arrays.asList("precision",
                "recall",
                "f1",
                "hi_acc",
                "hi_acc_k",
                "hi_acc_n",
                "lo_acc",
                "lo_acc_k",
                "lo_acc_n",
                "consult",
                "err_rec",
                "err_intro");
    }
}
