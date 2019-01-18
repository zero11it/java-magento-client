package com.github.chen0040.magento.models;

import java.util.HashMap;
import java.util.Map;

public class CartItem {
   private int item_id;
   private String sku;
   private int qty;
   private String name;
   private double price;
   private String product_type;
   private String quote_id;

   private CartItemProductOption product_option = new CartItemProductOption();
   private Map<String, Object> extension_attributes = new HashMap<>();

   public int getItem_id() {
      return item_id;
   }

   public void setItem_id(int item_id) {
      this.item_id = item_id;
   }

   public String getSku() {
      return sku;
   }

   public void setSku(String sku) {
      this.sku = sku;
   }

   public int getQty() {
      return qty;
   }

   public void setQty(int qty) {
      this.qty = qty;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public String getProduct_type() {
      return product_type;
   }

   public void setProduct_type(String product_type) {
      this.product_type = product_type;
   }

   public String getQuote_id() {
      return quote_id;
   }

   public void setQuote_id(String quote_id) {
      this.quote_id = quote_id;
   }

   public CartItemProductOption getProduct_option() {
      return product_option;
   }

   public void setProduct_option(CartItemProductOption product_option) {
      this.product_option = product_option;
   }

   public Map<String, Object> getExtension_attributes() {
      return extension_attributes;
   }

   public void setExtension_attributes(Map<String, Object> extension_attributes) {
      this.extension_attributes = extension_attributes;
   }
}


