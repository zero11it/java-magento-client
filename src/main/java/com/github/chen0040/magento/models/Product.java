package com.github.chen0040.magento.models;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Product {

   public static final int VISIBILITY_NOT_VISIBLE    = 1;
   public static final int VISIBILITY_IN_CATALOG     = 2;
   public static final int VISIBILITY_IN_SEARCH      = 3;
   public static final int VISIBILITY_BOTH           = 4;

   public static final int STATUS_DISABLED = 2;
   public static final int STATUS_ENABLED = 1;

   private long id = 1;
   private String sku = "product_dynamic_1";
   private String name =  "Simple Product 1";
   private long attribute_set_id =  4;
   private double price = 10;
   private int status  = 1;
   private int visibility = 4;
   private String type_id = "simple";
   private String created_at = "2017-05-03 03:46:13";
   private String updated_at = "2017-05-03 03:46:13";
   private double weight = 1;

   private ExtensionAttributes extension_attributes = new ExtensionAttributes();
   private List<String> product_links = new ArrayList<>();
   private List<TierPrices> tier_prices = new ArrayList<>();

   @JSONField(deserializeUsing = ProductAttributeValueDeserializer.class)
   private List<MagentoAttribute> custom_attributes = new ArrayList<>();

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getSku() {
      return sku;
   }

   public void setSku(String sku) {
      this.sku = sku;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public long getAttribute_set_id() {
      return attribute_set_id;
   }

   public void setAttribute_set_id(long attribute_set_id) {
      this.attribute_set_id = attribute_set_id;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public int getVisibility() {
      return visibility;
   }

   public void setVisibility(int visibility) {
      this.visibility = visibility;
   }

   public String getType_id() {
      return type_id;
   }

   public void setType_id(String type_id) {
      this.type_id = type_id;
   }

   public String getCreated_at() {
      return created_at;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }

   public String getUpdated_at() {
      return updated_at;
   }

   public void setUpdated_at(String updated_at) {
      this.updated_at = updated_at;
   }

   public double getWeight() {
      return weight;
   }

   public void setWeight(double weight) {
      this.weight = weight;
   }

   public ExtensionAttributes getExtension_attributes() {
      return extension_attributes;
   }

   public void setExtension_attributes(ExtensionAttributes extension_attributes) {
      this.extension_attributes = extension_attributes;
   }

   public List<String> getProduct_links() {
      return product_links;
   }

   public void setProduct_links(List<String> product_links) {
      this.product_links = product_links;
   }

   public List<TierPrices> getTier_prices() {
      return tier_prices;
   }

   public void setTier_prices(List<TierPrices> tier_prices) {
      this.tier_prices = tier_prices;
   }

   public List<MagentoAttribute> getCustom_attributes() {
      return custom_attributes;
   }

   public void setCustom_attributes(List<MagentoAttribute> custom_attributes) {
      this.custom_attributes = custom_attributes;
   }
}


