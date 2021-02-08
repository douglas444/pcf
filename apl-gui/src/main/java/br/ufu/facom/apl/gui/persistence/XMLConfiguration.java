package br.ufu.facom.apl.gui.persistence;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "paths",
        "interceptableClassName",
        "metaCategorizerClassName",
        "activeLearningStrategyClassName",
        "interceptableNominalParameters",
        "interceptableNumericParameters",
        "metaCategorizerNominalParameters",
        "metaCategorizerNumericParameters",
        "activeLearningStrategyNominalParameters",
        "activeLearningStrategyNumericParameters",
        "multipleExecutions",
        "variableParameterName",
        "variableParameterType",
        "variationIncrement",
        "variationTimes"
})
@XmlRootElement(name = "configuration")
public class XMLConfiguration {

    @XmlElement(required = true)
    protected List<String> paths;
    @XmlElement(required = true)
    protected String interceptableClassName;
    @XmlElement(required = true)
    protected String metaCategorizerClassName;
    @XmlElement(required = true)
    protected String activeLearningStrategyClassName;
    @XmlElement(required = true)
    protected List<NominalParameterValue> interceptableNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> interceptableNumericParameters;
    @XmlElement(required = true)
    protected List<NominalParameterValue> metaCategorizerNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> metaCategorizerNumericParameters;
    @XmlElement(required = true)
    protected List<NominalParameterValue> activeLearningStrategyNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> activeLearningStrategyNumericParameters;
    protected boolean multipleExecutions;
    @XmlElement(required = true, nillable = true)
    protected String variableParameterName;
    @XmlElement(required = true, nillable = true)
    protected String variableParameterType;
    protected double variationIncrement;
    protected int variationTimes;

    @Override
    public String toString() {
        return "XMLConfiguration{" +
                "paths=" + paths +
                ", interceptableClassName='" + interceptableClassName + '\'' +
                ", metaCategorizerClassName='" + metaCategorizerClassName + '\'' +
                ", activeLearningStrategyClassName='" + activeLearningStrategyClassName + '\'' +
                ", interceptableNominalParameters=" + interceptableNominalParameters +
                ", interceptableNumericParameters=" + interceptableNumericParameters +
                ", metaCategorizerNominalParameters=" + metaCategorizerNominalParameters +
                ", metaCategorizerNumericParameters=" + metaCategorizerNumericParameters +
                ", activeLearningStrategyNominalParameters=" + activeLearningStrategyNominalParameters +
                ", activeLearningStrategyNumericParameters=" + activeLearningStrategyNumericParameters +
                ", multipleExecutions=" + multipleExecutions +
                ", variableParameterName='" + variableParameterName + '\'' +
                ", variableParameterType='" + variableParameterType + '\'' +
                ", variationIncrement=" + variationIncrement +
                ", variationTimes=" + variationTimes +
                '}';
    }

    public List<String> getPaths() {
        if (paths == null) {
            paths = new ArrayList<String>();
        }
        return this.paths;
    }

    public String getInterceptableClassName() {
        return interceptableClassName;
    }

    public void setInterceptableClassName(String value) {
        this.interceptableClassName = value;
    }

    public String getMetaCategorizerClassName() {
        return metaCategorizerClassName;
    }

    public void setMetaCategorizerClassName(String value) {
        this.metaCategorizerClassName = value;
    }

    public String getActiveLearningStrategyClassName() {
        return activeLearningStrategyClassName;
    }

    public void setActiveLearningStrategyClassName(String value) {
        this.activeLearningStrategyClassName = value;
    }

    public List<NominalParameterValue> getInterceptableNominalParameters() {
        if (interceptableNominalParameters == null) {
            interceptableNominalParameters = new ArrayList<NominalParameterValue>();
        }
        return this.interceptableNominalParameters;
    }

    public List<NumericParameterValue> getInterceptableNumericParameters() {
        if (interceptableNumericParameters == null) {
            interceptableNumericParameters = new ArrayList<NumericParameterValue>();
        }
        return this.interceptableNumericParameters;
    }

    public List<NominalParameterValue> getMetaCategorizerNominalParameters() {
        if (metaCategorizerNominalParameters == null) {
            metaCategorizerNominalParameters = new ArrayList<NominalParameterValue>();
        }
        return this.metaCategorizerNominalParameters;
    }

    public List<NumericParameterValue> getMetaCategorizerNumericParameters() {
        if (metaCategorizerNumericParameters == null) {
            metaCategorizerNumericParameters = new ArrayList<NumericParameterValue>();
        }
        return this.metaCategorizerNumericParameters;
    }

    public List<NominalParameterValue> getActiveLearningStrategyNominalParameters() {
        if (activeLearningStrategyNominalParameters == null) {
            activeLearningStrategyNominalParameters = new ArrayList<NominalParameterValue>();
        }
        return this.activeLearningStrategyNominalParameters;
    }

    public List<NumericParameterValue> getActiveLearningStrategyNumericParameters() {
        if (activeLearningStrategyNumericParameters == null) {
            activeLearningStrategyNumericParameters = new ArrayList<NumericParameterValue>();
        }
        return this.activeLearningStrategyNumericParameters;
    }

    public boolean isMultipleExecutions() {
        return multipleExecutions;
    }

    public void setMultipleExecutions(boolean value) {
        this.multipleExecutions = value;
    }

    public String getVariableParameterName() {
        return variableParameterName;
    }

    public void setVariableParameterName(String value) {
        this.variableParameterName = value;
    }

    public String getVariableParameterType() {
        return variableParameterType;
    }

    public void setVariableParameterType(String value) {
        this.variableParameterType = value;
    }

    public double getVariationIncrement() {
        return variationIncrement;
    }

    public void setVariationIncrement(double value) {
        this.variationIncrement = value;
    }

    public int getVariationTimes() {
        return variationTimes;
    }

    public void setVariationTimes(int value) {
        this.variationTimes = value;
    }

}

