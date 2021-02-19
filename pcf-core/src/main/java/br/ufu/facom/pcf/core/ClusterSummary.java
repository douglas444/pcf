package br.ufu.facom.pcf.core;

import java.util.Arrays;
import java.util.Objects;

public abstract class ClusterSummary {
    public abstract double[] calculateCentroidAttributes();
    public abstract double calculateStandardDeviation();
    public abstract Integer getLabel();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterSummary that = (ClusterSummary) o;
        return calculateStandardDeviation() == that.calculateStandardDeviation() &&
                Arrays.equals(calculateCentroidAttributes(), that.calculateCentroidAttributes()) &&
                Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(calculateStandardDeviation(), getLabel());
        result = 31 * result + Arrays.hashCode(calculateCentroidAttributes());
        return result;
    }
}
