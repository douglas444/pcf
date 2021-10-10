package br.ufu.facom.pcf.core;

import java.util.Arrays;
import java.util.List;

public class EvaluationSummary {

    private final double meanLabelPurityKnown;
    private final double meanLabelPurityNovelty;

    private final double meanCategoryPurityKnown;
    private final double meanCategoryPurityNovelty;

    private final int highLevelTrueKnown;
    private final int highLevelFalseKnown;
    private final int highLevelTrueNovelty;
    private final int highLevelFalseNovelty;

    private final int baseTrueKnown;
    private final int baseFalseKnown;
    private final int baseTrueNovelty;
    private final int baseFalseNovelty;

    private final int lowLevelTrueKnown;
    private final int lowLevelFalseKnown;
    private final int lowLevelTrueNovelty;
    private final int lowLevelFalseNovelty;

    private final int queryTruePositive;
    private final int queryFalsePositive;
    private final int queryTrueNegative;
    private final int queryFalseNegative;

    private final int recoveredNovelty;
    private final int recoveredKnown;
    private final int unrecoveredNovelty;
    private final int unrecoveredKnown;

    private final int corruptedNovelty;
    private final int corruptedKnown;
    private final int uncorruptedNovelty;
    private final int uncorruptedKnown;

    public EvaluationSummary(final List<Log> logs) {

        if (logs.isEmpty()) {
            throw new IllegalArgumentException("Cannot calculate measures. No log was registered.");
        }

        this.queryTruePositive = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .count();

        this.queryFalsePositive = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .count();

        this.queryTrueNegative = (int) logs.stream()
                .filter(log -> log.getConfidence() == Confidence.RELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .count();

        this.queryFalseNegative = (int) logs.stream()
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

        this.baseTrueKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getBasePredictedCategory() == Category.KNOWN)
                .count();

        this.baseFalseKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getBasePredictedCategory() == Category.KNOWN)
                .count();

        this.baseTrueNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getBasePredictedCategory() == Category.NOVELTY)
                .count();

        this.baseFalseNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getBasePredictedCategory() == Category.NOVELTY)
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

        this.recoveredKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .count();

        this.recoveredNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .count();

        this.unrecoveredKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.unrecoveredNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() != log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.corruptedKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.corruptedNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() != log.getLowLevelPredictedCategory())
                .count();

        this.uncorruptedKnown = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .count();

        this.uncorruptedNovelty = (int) logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .filter(log -> log.getConfidence() == Confidence.UNRELIABLE)
                .filter(log -> log.getRealCategory() == log.getBasePredictedCategory())
                .filter(log -> log.getRealCategory() == log.getLowLevelPredictedCategory())
                .count();

        this.meanLabelPurityKnown = logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .mapToDouble(Log::getLabelPurity)
                .reduce(0.0, Double::sum) / this.highLevelCategorizerConsultationsKnown();

        this.meanLabelPurityNovelty = logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .mapToDouble(Log::getLabelPurity)
                .reduce(0.0, Double::sum) / this.highLevelCategorizerConsultationsNovelty();

        this.meanCategoryPurityKnown = logs.stream()
                .filter(log -> log.getRealCategory() == Category.KNOWN)
                .mapToDouble(Log::getCategoryPurity)
                .reduce(0.0, Double::sum) / this.highLevelCategorizerConsultationsKnown();

        this.meanCategoryPurityNovelty = logs.stream()
                .filter(log -> log.getRealCategory() == Category.NOVELTY)
                .mapToDouble(Log::getCategoryPurity)
                .reduce(0.0, Double::sum) / this.highLevelCategorizerConsultationsNovelty();
    }

    public double calculateQueryingPrecision() {
        return this.queryTruePositive / (double) (this.queryTruePositive + this.queryFalsePositive);
    }

    public double calculateQueryingSensitivity() {
        return this.queryTruePositive / (double) (this.queryTruePositive + this.queryFalseNegative);
    }

    public double calculateQueryingF1() {
        final double recall = calculateQueryingSensitivity();
        final double precision = calculateQueryingPrecision();
        return 2 * recall * precision / (recall + precision);
    }

    public String clusterQueryingConfusionMatrixToString() {
        return "{" +
                "truePositive=" + this.queryTruePositive +
                ", falsePositive=" + this.queryFalsePositive +
                ", trueNegative=" + this.queryTrueNegative +
                ", falseNegative=" + this.queryFalseNegative +
                "}";
    }

    public String baseCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.baseTrueKnown +
                ", falseKnown=" + this.baseFalseKnown +
                ", trueNovelty=" + this.baseTrueNovelty +
                ", falseNovelty=" + this.baseFalseNovelty +
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

    public String frameworkConfusionMatrixToString() {
        return "{" +
                "recovered=" + this.recoveredKnown + this.recoveredNovelty +
                ", unrecovered=" + this.unrecoveredKnown + this.unrecoveredNovelty +
                ", corrupted=" + this.corruptedKnown + this.corruptedNovelty +
                ", uncorrupted=" + this.uncorruptedKnown + this.uncorruptedNovelty +
                "}";
    }

    public double calculateBaseCategorizerSensitivity() {
        return this.baseTrueNovelty / (double) (this.baseTrueNovelty + this.baseFalseKnown);
    }

    public double calculateBaseCategorizerSpecificity() {
        return this.baseTrueKnown / (double) (this.baseTrueKnown + this.baseFalseNovelty);
    }

    public double calculateBaseCategorizerAccuracy() {
        return (this.baseTrueKnown + this.baseTrueNovelty) /
                (double) (this.baseTrueKnown
                        + this.baseFalseKnown
                        + this.baseTrueNovelty
                        + this.baseFalseNovelty);
    }

    public double calculateHighLevelCategorizerSensitivity() {
        return this.highLevelTrueNovelty / (double) (this.highLevelTrueNovelty + this.highLevelFalseKnown);
    }

    public double calculateHighLevelCategorizerSpecificity() {
        return this.highLevelTrueKnown / (double) (this.highLevelTrueKnown + this.highLevelFalseNovelty);
    }

    public double calculateHighLevelCategorizerAccuracy() {
        return (this.highLevelTrueKnown + this.highLevelTrueNovelty) /
                (double) (this.highLevelTrueKnown
                        + this.highLevelFalseKnown
                        + this.highLevelTrueNovelty
                        + this.highLevelFalseNovelty);
    }

    public double calculateLowLevelCategorizerSensitivity() {
        return this.lowLevelTrueNovelty / (double) (this.lowLevelTrueNovelty + this.lowLevelFalseKnown);
    }

    public double calculateLowLevelCategorizerSpecificity() {
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
        return (this.recoveredKnown + this.recoveredNovelty) / (double) (this.baseFalseKnown + this.baseFalseNovelty);
    }

    public double calculateErrorIntroduction() {
        return (this.corruptedKnown + this.corruptedNovelty) / (double) (this.baseTrueKnown + this.baseTrueNovelty);
    }

    public int highLevelCategorizerConsultationsNovelty() {
        return this.highLevelTrueNovelty + this.highLevelFalseKnown;
    }

    public double calculateFinalCategorizerSensitivity() {
        return (this.baseTrueNovelty + (this.recoveredNovelty - this.corruptedNovelty))
                / (double) (this.baseTrueNovelty + this.baseFalseKnown);
    }

    public double calculateFinalCategorizerSpecificity() {
        return (this.baseTrueKnown + (this.recoveredKnown - this.corruptedKnown))
                / (double) (this.baseTrueKnown + this.baseFalseNovelty);
    }

    public int highLevelCategorizerConsultationsKnown() {
        return this.highLevelTrueKnown + this.highLevelFalseNovelty;
    }

    public int lowLevelCategorizerConsultationsNovelty() {
        return this.lowLevelTrueNovelty + this.lowLevelFalseKnown;
    }

    public int lowLevelCategorizerConsultationsKnown() {
        return this.lowLevelTrueKnown + this.lowLevelFalseNovelty;
    }

    public double getMeanLabelPurityKnown() {
        return meanLabelPurityKnown;
    }

    public double getMeanLabelPurityNovelty() {
        return meanLabelPurityNovelty;
    }

    public double getMeanCategoryPurityKnown() {
        return meanCategoryPurityKnown;
    }

    public double getMeanCategoryPurityNovelty() {
        return meanCategoryPurityNovelty;
    }

    @Override
    public String toString() {

        return
                "\nCLUSTERS" +
                "\nMean label purity known: " + getMeanLabelPurityKnown() +
                "\nMean label purity novelty: " + getMeanLabelPurityNovelty() +
                "\nMean category purity known: " + getMeanCategoryPurityKnown() +
                "\nMean category purity novelty: " + getMeanCategoryPurityNovelty() +
                "\nCLUSTER QUERYING" +
                "\nConfusion matrix: " +
                        clusterQueryingConfusionMatrixToString() +
                "\nPrecision (precision): " +
                        calculateQueryingPrecision() +
                "\nSensitivity (sensitivi): " +
                        calculateQueryingSensitivity() +
                "\nF1 (_______f1): " +
                        calculateQueryingF1() +
                "\n\nBASE CATEGORIZER" +
                "\nConfusion matrix: " +
                        baseCategorizerConfusionMatrixToString() +
                "\nAccuracy: " +
                        calculateBaseCategorizerAccuracy() +
                "\nSpecificity: " +
                        calculateBaseCategorizerSpecificity() +
                "\nSensitivity: " +
                        calculateBaseCategorizerSensitivity() +
                "\n\nHIGH-LEVEL CATEGORIZER" +
                "\nConfusion matrix: " +
                        highLevelCategorizerConfusionMatrixToString() +
                "\nConsulted known patterns (hi_cons_k): " +
                        highLevelCategorizerConsultationsKnown() +
                "\nConsulted novelty patterns (hi_cons_n): " +
                        highLevelCategorizerConsultationsNovelty() +
                "\nAccuracy (___hi_acc): " +
                        calculateHighLevelCategorizerAccuracy() +
                "\nSpecificity (hi_specif): " +
                        calculateHighLevelCategorizerSpecificity() +
                "\nSensitivity (hi_sensit): " +
                        calculateHighLevelCategorizerSensitivity() +
                "\n\nLOW-LEVEL CATEGORIZER" +
                "\nCategorization confusion matrix: " +
                        lowLevelCategorizerConfusionMatrixToString() +
                "\nRecovery confusion matrix: " +
                        frameworkConfusionMatrixToString() +
                "\nConsulted known patterns (lo_cons_k): " +
                        lowLevelCategorizerConsultationsKnown() +
                "\nConsulted novelty patterns (lo_cons_n): " +
                        lowLevelCategorizerConsultationsNovelty() +
                "\nAccuracy (___lo_acc): " +
                        calculateLowLevelCategorizerAccuracy() +
                "\nSpecificity (lo_specif): " +
                        calculateLowLevelCategorizerSpecificity() +
                "\nSensitivity (lo_sensit): " +
                        calculateLowLevelCategorizerSensitivity() +
                "\n\nFRAMEWORK IMPACT" +
                "\nError Recovery (__err_rec): " +
                        calculateErrorRecovery() +
                "\nError Introduction (err_intro): " +
                        calculateErrorIntroduction() +
                "\nFinal Categorization Specificity: " +
                        calculateFinalCategorizerSpecificity() +
                "\nFinal Categorization Sensitivity: " +
                        calculateFinalCategorizerSensitivity();
    }

    public List<Double> getValues() {

        return Arrays.asList(
                calculateQueryingPrecision(),
                calculateQueryingSensitivity(),
                calculateQueryingF1(),
                (double) highLevelCategorizerConsultationsKnown(),
                (double) highLevelCategorizerConsultationsNovelty(),
                calculateHighLevelCategorizerAccuracy(),
                calculateHighLevelCategorizerSpecificity(),
                calculateHighLevelCategorizerSensitivity(),
                (double) lowLevelCategorizerConsultationsKnown(),
                (double) lowLevelCategorizerConsultationsNovelty(),
                calculateLowLevelCategorizerAccuracy(),
                calculateLowLevelCategorizerSpecificity(),
                calculateLowLevelCategorizerSensitivity(),
                calculateErrorRecovery(),
                calculateErrorIntroduction());
    }

    public static List<String> getHeaders() {

        return Arrays.asList(
                "precision",
                "sensitivi",
                "       f1",
                "hi_cons_k",
                "hi_cons_n",
                "   hi_acc",
                "hi_specif",
                "hi_sensit",
                "lo_cons_k",
                "lo_cons_n",
                "   lo_acc",
                "lo_specif",
                "lo_sensit",
                "  err_rec",
                "err_intro");
    }
}
