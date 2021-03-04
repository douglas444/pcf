package br.ufu.facom.pcf.gui.service.persistence;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "paths",
        "interceptableClassName",
        "highLevelCategorizerClassName",
        "lowLevelCategorizerClassName",
        "interceptableNominalParameters",
        "interceptableNumericParameters",
        "highLevelCategorizerNominalParameters",
        "highLevelCategorizerNumericParameters",
        "lowLevelCategorizerNominalParameters",
        "lowLevelCategorizerNumericParameters",
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
    protected String highLevelCategorizerClassName;
    @XmlElement(required = true)
    protected String lowLevelCategorizerClassName;
    @XmlElement(required = true)
    protected List<NominalParameterValue> interceptableNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> interceptableNumericParameters;
    @XmlElement(required = true)
    protected List<NominalParameterValue> highLevelCategorizerNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> highLevelCategorizerNumericParameters;
    @XmlElement(required = true)
    protected List<NominalParameterValue> lowLevelCategorizerNominalParameters;
    @XmlElement(required = true)
    protected List<NumericParameterValue> lowLevelCategorizerNumericParameters;
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
                ", highLevelCategorizerClassName='" + highLevelCategorizerClassName + '\'' +
                ", lowLevelCategorizerClassName='" + lowLevelCategorizerClassName + '\'' +
                ", interceptableNominalParameters=" + interceptableNominalParameters +
                ", interceptableNumericParameters=" + interceptableNumericParameters +
                ", highLevelCategorizerNominalParameters=" + highLevelCategorizerNominalParameters +
                ", highLevelCategorizerNumericParameters=" + highLevelCategorizerNumericParameters +
                ", lowLevelCategorizerNominalParameters=" + lowLevelCategorizerNominalParameters +
                ", lowLevelCategorizerNumericParameters=" + lowLevelCategorizerNumericParameters +
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

    public String getHighLevelCategorizerClassName() {
        return highLevelCategorizerClassName;
    }

    public void setHighLevelCategorizerClassName(String value) {
        this.highLevelCategorizerClassName = value;
    }

    public String getLowLevelCategorizerClassName() {
        return lowLevelCategorizerClassName;
    }

    public void setLowLevelCategorizerClassName(String value) {
        this.lowLevelCategorizerClassName = value;
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

    public List<NominalParameterValue> getHighLevelCategorizerNominalParameters() {
        if (highLevelCategorizerNominalParameters == null) {
            highLevelCategorizerNominalParameters = new ArrayList<NominalParameterValue>();
        }
        return this.highLevelCategorizerNominalParameters;
    }

    public List<NumericParameterValue> getHighLevelCategorizerNumericParameters() {
        if (highLevelCategorizerNumericParameters == null) {
            highLevelCategorizerNumericParameters = new ArrayList<NumericParameterValue>();
        }
        return this.highLevelCategorizerNumericParameters;
    }

    public List<NominalParameterValue> getLowLevelCategorizerNominalParameters() {
        if (lowLevelCategorizerNominalParameters == null) {
            lowLevelCategorizerNominalParameters = new ArrayList<NominalParameterValue>();
        }
        return this.lowLevelCategorizerNominalParameters;
    }

    public List<NumericParameterValue> getLowLevelCategorizerNumericParameters() {
        if (lowLevelCategorizerNumericParameters == null) {
            lowLevelCategorizerNumericParameters = new ArrayList<NumericParameterValue>();
        }
        return this.lowLevelCategorizerNumericParameters;
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

