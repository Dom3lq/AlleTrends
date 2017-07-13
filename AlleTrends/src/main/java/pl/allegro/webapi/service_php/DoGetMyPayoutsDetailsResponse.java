/**
 * DoGetMyPayoutsDetailsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.allegro.webapi.service_php;

public class DoGetMyPayoutsDetailsResponse  implements java.io.Serializable {
    private int paymentsCount;

    private pl.allegro.webapi.service_php.PayoutPaymentsStruct[] payments;

    private int refundsFromCount;

    private pl.allegro.webapi.service_php.PayoutRefundFromStruct[] refundFrom;

    private int refundsToCount;

    private pl.allegro.webapi.service_php.PayoutRefundToStruct[] refundTo;

    public DoGetMyPayoutsDetailsResponse() {
    }

    public DoGetMyPayoutsDetailsResponse(
           int paymentsCount,
           pl.allegro.webapi.service_php.PayoutPaymentsStruct[] payments,
           int refundsFromCount,
           pl.allegro.webapi.service_php.PayoutRefundFromStruct[] refundFrom,
           int refundsToCount,
           pl.allegro.webapi.service_php.PayoutRefundToStruct[] refundTo) {
           this.paymentsCount = paymentsCount;
           this.payments = payments;
           this.refundsFromCount = refundsFromCount;
           this.refundFrom = refundFrom;
           this.refundsToCount = refundsToCount;
           this.refundTo = refundTo;
    }


    /**
     * Gets the paymentsCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return paymentsCount
     */
    public int getPaymentsCount() {
        return paymentsCount;
    }


    /**
     * Sets the paymentsCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param paymentsCount
     */
    public void setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
    }


    /**
     * Gets the payments value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return payments
     */
    public pl.allegro.webapi.service_php.PayoutPaymentsStruct[] getPayments() {
        return payments;
    }


    /**
     * Sets the payments value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param payments
     */
    public void setPayments(pl.allegro.webapi.service_php.PayoutPaymentsStruct[] payments) {
        this.payments = payments;
    }


    /**
     * Gets the refundsFromCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return refundsFromCount
     */
    public int getRefundsFromCount() {
        return refundsFromCount;
    }


    /**
     * Sets the refundsFromCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param refundsFromCount
     */
    public void setRefundsFromCount(int refundsFromCount) {
        this.refundsFromCount = refundsFromCount;
    }


    /**
     * Gets the refundFrom value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return refundFrom
     */
    public pl.allegro.webapi.service_php.PayoutRefundFromStruct[] getRefundFrom() {
        return refundFrom;
    }


    /**
     * Sets the refundFrom value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param refundFrom
     */
    public void setRefundFrom(pl.allegro.webapi.service_php.PayoutRefundFromStruct[] refundFrom) {
        this.refundFrom = refundFrom;
    }


    /**
     * Gets the refundsToCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return refundsToCount
     */
    public int getRefundsToCount() {
        return refundsToCount;
    }


    /**
     * Sets the refundsToCount value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param refundsToCount
     */
    public void setRefundsToCount(int refundsToCount) {
        this.refundsToCount = refundsToCount;
    }


    /**
     * Gets the refundTo value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @return refundTo
     */
    public pl.allegro.webapi.service_php.PayoutRefundToStruct[] getRefundTo() {
        return refundTo;
    }


    /**
     * Sets the refundTo value for this DoGetMyPayoutsDetailsResponse.
     * 
     * @param refundTo
     */
    public void setRefundTo(pl.allegro.webapi.service_php.PayoutRefundToStruct[] refundTo) {
        this.refundTo = refundTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DoGetMyPayoutsDetailsResponse)) return false;
        DoGetMyPayoutsDetailsResponse other = (DoGetMyPayoutsDetailsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.paymentsCount == other.getPaymentsCount() &&
            ((this.payments==null && other.getPayments()==null) || 
             (this.payments!=null &&
              java.util.Arrays.equals(this.payments, other.getPayments()))) &&
            this.refundsFromCount == other.getRefundsFromCount() &&
            ((this.refundFrom==null && other.getRefundFrom()==null) || 
             (this.refundFrom!=null &&
              java.util.Arrays.equals(this.refundFrom, other.getRefundFrom()))) &&
            this.refundsToCount == other.getRefundsToCount() &&
            ((this.refundTo==null && other.getRefundTo()==null) || 
             (this.refundTo!=null &&
              java.util.Arrays.equals(this.refundTo, other.getRefundTo())));
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
        _hashCode += getPaymentsCount();
        if (getPayments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getRefundsFromCount();
        if (getRefundFrom() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRefundFrom());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRefundFrom(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getRefundsToCount();
        if (getRefundTo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRefundTo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRefundTo(), i);
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
        new org.apache.axis.description.TypeDesc(DoGetMyPayoutsDetailsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", ">doGetMyPayoutsDetailsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentsCount");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "paymentsCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payments");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "payments"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "PayoutPaymentsStruct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refundsFromCount");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "refundsFromCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refundFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "refundFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "PayoutRefundFromStruct"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refundsToCount");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "refundsToCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refundTo");
        elemField.setXmlName(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "refundTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://webapi.allegro.pl/service.php", "PayoutRefundToStruct"));
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
