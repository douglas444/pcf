package br.ufu.facom.pmc.core;

import java.util.List;

public class Context {

    private Category realCategory;
    private Category predictedCategory;
    private List<double[]> samplesAttributes;
    private List<Double> samplesLabels;
    private List<ClusterSummary> clusterSummaries;

    public Category getRealCategory() {
        return realCategory;
    }

    public Context setRealCategory(Category realCategory) {
        this.realCategory = realCategory;
        return this;
    }

    public Category getPredictedCategory() {
        return predictedCategory;
    }

    public Context setPredictedCategory(Category predictedCategory) {
        this.predictedCategory = predictedCategory;
        return this;
    }

    public List<double[]> getSamplesAttributes() {
        return samplesAttributes;
    }

    public Context setSamplesAttributes(List<double[]> samplesAttributes) {
        this.samplesAttributes = samplesAttributes;
        return this;
    }

    public List<Double> getSamplesLabels() {
        return samplesLabels;
    }

    public Context setSamplesLabels(List<Double> samplesLabels) {
        this.samplesLabels = samplesLabels;
        return this;
    }

    public List<ClusterSummary> getClusterSummaries() {
        return clusterSummaries;
    }

    public Context setClusterSummaries(List<ClusterSummary> clusterSummaries) {
        this.clusterSummaries = clusterSummaries;
        return this;
    }
}
