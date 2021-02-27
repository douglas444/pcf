package br.ufu.facom.pcf.core;

import java.util.Arrays;
import java.util.Objects;

public abstract class ClusterSummary {

    public abstract double[] getCentroidAttributes();
    public abstract double getStandardDeviation();
    public abstract Integer getLabel();

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClusterSummary that = (ClusterSummary) o;
        return getStandardDeviation() == that.getStandardDeviation() &&
                Arrays.equals(getCentroidAttributes(), that.getCentroidAttributes()) &&
                Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getStandardDeviation(), getLabel());
        result = 31 * result + Arrays.hashCode(getCentroidAttributes());
        return result;
    }
}
