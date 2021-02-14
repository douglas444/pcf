package br.ufu.facom.pmc.gui.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nominalParameterValue", propOrder = {
    "parameterName",
    "value"
})
public class NominalParameterValue {

    @XmlElement(required = true)
    protected String parameterName;
    @XmlElement(required = true)
    protected String value;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String value) {
        this.parameterName = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
