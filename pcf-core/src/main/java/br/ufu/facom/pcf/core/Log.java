package br.ufu.facom.pcf.core;

public class Log {

    private double labelPurity;
    private double categoryPurity;
    private Category realCategory;
    private Category basePredictedCategory;
    private Category highLevelPredictedCategory;
    private Category lowLevelPredictedCategory;
    private Confidence confidence;

    public Log(final double labelPurity,
               final double categoryPurity,
               final Category realCategory,
               final Category basePredictedCategory,
               final Category highLevelPredictedCategory,
               final Category lowLevelPredictedCategory,
               final Confidence confidence) {
        this.labelPurity = labelPurity;
        this.categoryPurity = categoryPurity;
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

    public double getLabelPurity() {
        return labelPurity;
    }

    public void setLabelPurity(double labelPurity) {
        this.labelPurity = labelPurity;
    }

    public double getCategoryPurity() {
        return categoryPurity;
    }

    public void setCategoryPurity(double categoryPurity) {
        this.categoryPurity = categoryPurity;
    }
}
