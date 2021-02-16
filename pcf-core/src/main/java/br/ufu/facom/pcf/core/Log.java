package br.ufu.facom.pcf.core;

public class Log {


    private Category realCategory;
    private Category basePredictedCategory;
    private Category highLevelPredictedCategory;
    private Category lowLevelPredictedCategory;
    private CategorizationLabel categorizationLabel;

    public Log(Category realCategory, Category basePredictedCategory, Category highLevelPredictedCategory,
               Category lowLevelPredictedCategory, CategorizationLabel categorizationLabel) {
        this.realCategory = realCategory;
        this.basePredictedCategory = basePredictedCategory;
        this.highLevelPredictedCategory = highLevelPredictedCategory;
        this.lowLevelPredictedCategory = lowLevelPredictedCategory;
        this.categorizationLabel = categorizationLabel;
    }

    public ConfusionMatrixEnum getConfusionMatrixEnum() {

        if (this.categorizationLabel == CategorizationLabel.RISKY) {

            if (this.realCategory == this.basePredictedCategory) {
                return ConfusionMatrixEnum.FALSE_RISKY;
            } else {
                return ConfusionMatrixEnum.TRUE_RISKY;
            }

        } else {

            if (this.realCategory == this.basePredictedCategory) {
                return ConfusionMatrixEnum.TRUE_RELIABLE;
            } else {
                return ConfusionMatrixEnum.FALSE_RELIABLE;
            }

        }

    }

    public Category getRealCategory() {
        return realCategory;
    }

    public Log setRealCategory(Category realCategory) {
        this.realCategory = realCategory;
        return this;
    }

    public Category getBasePredictedCategory() {
        return basePredictedCategory;
    }

    public Log setBasePredictedCategory(Category basePredictedCategory) {
        this.basePredictedCategory = basePredictedCategory;
        return this;
    }

    public Category getHighLevelPredictedCategory() {
        return highLevelPredictedCategory;
    }

    public Log setHighLevelPredictedCategory(Category highLevelPredictedCategory) {
        this.highLevelPredictedCategory = highLevelPredictedCategory;
        return this;
    }

    public Category getLowLevelPredictedCategory() {
        return lowLevelPredictedCategory;
    }

    public Log setLowLevelPredictedCategory(Category lowLevelPredictedCategory) {
        this.lowLevelPredictedCategory = lowLevelPredictedCategory;
        return this;
    }

    public CategorizationLabel getCategorizationLabel() {
        return categorizationLabel;
    }

    public Log setCategorizationLabel(CategorizationLabel categorizationLabel) {
        this.categorizationLabel = categorizationLabel;
        return this;
    }
}
