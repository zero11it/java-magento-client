package com.github.chen0040.magento.models;


public class MagentoAttribute {
   private String attribute_code = "description";
   private Object value = "Full simple product Description 1";

   public MagentoAttribute(){

   }

   public MagentoAttribute(String attribute_code, Object value) {
      this.attribute_code = attribute_code;
      this.value = value;
   }

   public String getAttribute_code() {
      return attribute_code;
   }

   public void setAttribute_code(String attribute_code) {
      this.attribute_code = attribute_code;
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }
}
