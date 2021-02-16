package br.ufu.facom.pcf.core;

public abstract class ClusterSummary {
    public abstract double[] calculateCentroidAttributes();
    public abstract double calculateStandardDeviation();
    public abstract Integer getLabel();
}
