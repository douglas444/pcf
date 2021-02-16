package br.ufu.facom.pcf.core;

import java.util.List;
import java.util.Set;

public class Context {

    private Category realCategory;
    private Category predictedCategory;
    private ClusterSummary patternClusterSummary;
    private List<double[]> samplesAttributes;
    private List<Integer> samplesLabels;
    private List<ClusterSummary> clusterSummaries;
    private Set<Integer> knownLabels;

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

    public List<Integer> getSamplesLabels() {
        return samplesLabels;
    }

    public Context setSamplesLabels(List<Integer> samplesLabels) {
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

    public ClusterSummary getPatternClusterSummary() {
        return patternClusterSummary;
    }

    public Context setPatternClusterSummary(ClusterSummary patternClusterSummary) {
        this.patternClusterSummary = patternClusterSummary;
        return this;
    }

    public Set<Integer> getKnownLabels() {
        return knownLabels;
    }

    public Context setKnownLabels(Set<Integer> knownLabels) {
        this.knownLabels = knownLabels;
        return this;
    }
}
