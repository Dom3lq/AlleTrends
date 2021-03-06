/**
 * DoGetMySoldItemsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.allegro.webapi.service_php;

public class DoGetMySoldItemsResponse  implements java.io.Serializable {
    private int soldItemsCounter;

    private pl.allegro.webapi.service_php.SoldItemStruct[] soldItemsList;

    public DoGetMySoldItemsResponse() {
    }

    public DoGetMySoldItemsResponse(
           int soldItemsCounter,
           pl.allegro.webapi.service_php.SoldItemStruct[] soldItemsList) {
           this.soldItemsCounter = soldItemsCounter;
           this.soldItemsList = soldItemsList;
    }


    /**
     * Gets the soldItemsCounter value for this DoGetMySoldItemsResponse.
     * 
     * @return soldItemsCounter
     */
    public int getSoldItemsCounter() {
        return soldItemsCounter;
    }


    /**
     * Sets the soldItemsCounter value for this DoGetMySoldItemsResponse.
     * 
     * @param soldItemsCounter
     */
    public void setSoldItemsCounter(int soldItemsCounter) {
        this.soldItemsCounter = soldItemsCounter;
    }


    /**
     * Gets the soldItemsList value for this DoGetMySoldItemsResponse.
     * 
     * @return soldItemsList
     */
    public pl.allegro.webapi.service_php.SoldItemStruct[] getSoldItemsList() {
        return soldItemsList;
    }


    /**
     * Sets the soldItemsList value for this DoGetMySoldItemsResponse.
     * 
     * @param soldItemsList
     */
    public void setSoldItemsList(pl.allegro.webapi.service_php.SoldItemStruct[] soldItemsList) {
        this.soldItemsList = soldItemsList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DoGetMySoldItemsResponse)) return false;
        DoGetMySoldItemsResponse other = (DoGetMySoldItemsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.soldItemsCounter == other.getSoldItemsCounter() &&
            ((this.soldItemsList==null && other.getSoldItemsList()==null) || 
             (this.soldItemsList!=null &&
              java.util.Arrays.equals(this.soldItemsList, other.getSoldItemsList())));
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
        _hashCode += getSoldItemsCounter();
        if (getSoldItemsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSoldItemsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSoldItemsList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DoGetMySoldItemsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", ">doGetMySoldItemsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soldItemsCounter");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "soldItemsCounter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soldItemsList");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "soldItemsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "SoldItemStruct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "item"));
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
