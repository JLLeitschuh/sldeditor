//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.11
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: [TEXT REMOVED by maven-replacer-plugin]
//

package com.sldeditor.common.xml.ui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Configuration for an integer field
 *
 * <p>Java class for XMLFieldConfigInteger complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="XMLFieldConfigInteger"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}XMLFieldConfigData"&gt;
 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}int" default="0" /&gt;
 *       &lt;attribute name="minValue" type="{http://www.w3.org/2001/XMLSchema}int" default="-2147483648" /&gt;
 *       &lt;attribute name="maxValue" type="{http://www.w3.org/2001/XMLSchema}int" default="2147483647" /&gt;
 *       &lt;attribute name="stepSize" type="{http://www.w3.org/2001/XMLSchema}int" default="1" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMLFieldConfigInteger")
public class XMLFieldConfigInteger extends XMLFieldConfigData {

    @XmlAttribute(name = "defaultValue")
    protected Integer defaultValue;

    @XmlAttribute(name = "minValue")
    protected Integer minValue;

    @XmlAttribute(name = "maxValue")
    protected Integer maxValue;

    @XmlAttribute(name = "stepSize")
    protected Integer stepSize;

    /**
     * Gets the value of the defaultValue property.
     *
     * @return possible object is {@link Integer }
     */
    public int getDefaultValue() {
        if (defaultValue == null) {
            return 0;
        } else {
            return defaultValue;
        }
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setDefaultValue(Integer value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the minValue property.
     *
     * @return possible object is {@link Integer }
     */
    public int getMinValue() {
        if (minValue == null) {
            return -2147483648;
        } else {
            return minValue;
        }
    }

    /**
     * Sets the value of the minValue property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setMinValue(Integer value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     *
     * @return possible object is {@link Integer }
     */
    public int getMaxValue() {
        if (maxValue == null) {
            return 2147483647;
        } else {
            return maxValue;
        }
    }

    /**
     * Sets the value of the maxValue property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setMaxValue(Integer value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the stepSize property.
     *
     * @return possible object is {@link Integer }
     */
    public int getStepSize() {
        if (stepSize == null) {
            return 1;
        } else {
            return stepSize;
        }
    }

    /**
     * Sets the value of the stepSize property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setStepSize(Integer value) {
        this.stepSize = value;
    }
}
