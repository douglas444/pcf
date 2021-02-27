package br.ufu.facom.pcf.core;

public class Log {


    private Category realCategory;
    private Category basePredictedCategory;
    private Category highLevelPredictedCategory;
    private Category lowLevelPredictedCategory;
    private Confidence confidence;

    public Log(Category realCategory, Category basePredictedCategory, Category highLevelPredictedCategory,
               Category lowLevelPredictedCategory, Confidence confidence) {
        this.realCategory = realCategory;
        this.basePredictedCategory = basePredictedCategory;
        this.highLevelPredictedCategory = highLevelPredictedCategory;
        this.lowLevelPredictedCategory = lowLevelPredictedCategory;
        this.confidence = confidence;
    }

    public Category getRealCategory() {
        return realCategory;
    }

    public void setRealCategory(Category realCategory) {
        this.realCategory = realCategory;
    }

    public Category getBasePredictedCategory() {
        return basePredictedCategory;
    }

    public void setBasePredictedCategory(Category basePredictedCategory) {
        this.basePredictedCategory = basePredictedCategory;
    }

    public Category getHighLevelPredictedCategory() {
        return highLevelPredictedCategory;
    }

    public void setHighLevelPredictedCategory(Category highLevelPredictedCategory) {
        this.highLevelPredictedCategory = highLevelPredictedCategory;
    }

    public Category getLowLevelPredictedCategory() {
        return lowLevelPredictedCategory;
    }

    public void setLowLevelPredictedCategory(Category lowLevelPredictedCategory) {
        this.lowLevelPredictedCategory = lowLevelPredictedCategory;
    }

    public Confidence getConfidence() {
        return confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }
}
