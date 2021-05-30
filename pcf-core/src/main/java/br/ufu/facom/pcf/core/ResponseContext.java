package br.ufu.facom.pcf.core;

public class ResponseContext {
    private Category category;
    private double[][] labeledSamplesAttributes;
    private int[] labeledSamplesLabels;

    public ResponseContext(Category category, double[][] labeledSamplesAttributes, int[] labeledSamplesLabels) {
        this.category = category;
        this.labeledSamplesAttributes = labeledSamplesAttributes;
        this.labeledSamplesLabels = labeledSamplesLabels;
    }

    public Category getCategory() {
        return category;
    }

    public ResponseContext setCategory(Category category) {
        this.category = category;
        return this;
    }

    public double[][] getLabeledSamplesAttributes() {
        return labeledSamplesAttributes;
    }

    public ResponseContext setLabeledSamplesAttributes(double[][] labeledSamplesAttributes) {
        this.labeledSamplesAttributes = labeledSamplesAttributes;
        return this;
    }

    public int[] getLabeledSamplesLabels() {
        return labeledSamplesLabels;
    }

    public ResponseContext setLabeledSamplesLabels(int[] labeledSamplesLabels) {
        this.labeledSamplesLabels = labeledSamplesLabels;
        return this;
    }
}
