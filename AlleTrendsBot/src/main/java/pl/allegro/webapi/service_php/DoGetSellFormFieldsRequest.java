/**
 * DoGetSellFormFieldsRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.allegro.webapi.service_php;

public class DoGetSellFormFieldsRequest  implements java.io.Serializable {
    private int countryCode;

    private long localVersion;

    private java.lang.String webapiKey;

    public DoGetSellFormFieldsRequest() {
    }

    public DoGetSellFormFieldsRequest(
           int countryCode,
           long localVersion,
           java.lang.String webapiKey) {
           this.countryCode = countryCode;
           this.localVersion = localVersion;
           this.webapiKey = webapiKey;
    }


    /**
     * Gets the countryCode value for this DoGetSellFormFieldsRequest.
     * 
     * @return countryCode
     */
    public int getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this DoGetSellFormFieldsRequest.
     * 
     * @param countryCode
     */
    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the localVersion value for this DoGetSellFormFieldsRequest.
     * 
     * @return localVersion
     */
    public long getLocalVersion() {
        return localVersion;
    }


    /**
     * Sets the localVersion value for this DoGetSellFormFieldsRequest.
     * 
     * @param localVersion
     */
    public void setLocalVersion(long localVersion) {
        this.localVersion = localVersion;
    }


    /**
     * Gets the webapiKey value for this DoGetSellFormFieldsRequest.
     * 
     * @return webapiKey
     */
    public java.lang.String getWebapiKey() {
        return webapiKey;
    }


    /**
     * Sets the webapiKey value for this DoGetSellFormFieldsRequest.
     * 
     * @param webapiKey
     */
    public void setWebapiKey(java.lang.String webapiKey) {
        this.webapiKey = webapiKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DoGetSellFormFieldsRequest)) return false;
        DoGetSellFormFieldsRequest other = (DoGetSellFormFieldsRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.countryCode == other.getCountryCode() &&
            this.localVersion == other.getLocalVersion() &&
            ((this.webapiKey==null && other.getWebapiKey()==null) || 
             (this.webapiKey!=null &&
              this.webapiKey.equals(other.getWebapiKey())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getCountryCode();
        _hashCode += new Long(getLocalVersion()).hashCode();
        if (getWebapiKey() != null) {
            _hashCode += getWebapiKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DoGetSellFormFieldsRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", ">DoGetSellFormFieldsRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "countryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "localVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webapiKey");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "webapiKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
