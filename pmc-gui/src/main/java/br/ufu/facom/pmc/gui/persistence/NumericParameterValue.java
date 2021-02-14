package br.ufu.facom.pmc.gui.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numericParameterValue", propOrder = {
    "parameterName",
    "value"
})
public class NumericParameterValue {

    @XmlElement(required = true)
    protected String parameterName;
    protected double value;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String value) {
        this.parameterName = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
