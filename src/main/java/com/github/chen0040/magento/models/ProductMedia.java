package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.List;

public class ProductMedia {
   private long id = 1;
   private String media_type = "image";
   private String label = "Image";
   private int position = 1;
   private boolean disabled = false;
   private List<String> types = new ArrayList<>();
   private String file = "new_image.png";

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getMedia_type() {
      return media_type;
   }

   public void setMedia_type(String media_type) {
      this.media_type = media_type;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public int getPosition() {
      return position;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public boolean isDisabled() {
      return disabled;
   }

   public void setDisabled(boolean disabled) {
      this.disabled = disabled;
   }

   public List<String> getTypes() {
      return types;
   }

   public void setTypes(List<String> types) {
      this.types = types;
   }

   public String getFile() {
      return file;
   }

   public void setFile(String file) {
      this.file = file;
   }
}
