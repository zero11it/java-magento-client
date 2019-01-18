package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.List;

public class Category {
  private long id = 2;
  private long parent_id = 1;
  private String name = "Category Store Group 1 - website_id_1";
  private boolean is_active = true;
  private int position = 1;
  private int level = 1;
  private int product_count = 25;
  List<CategoryAttribute> custom_attributes = new ArrayList<>();
  private List<Category> children_data = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getParent_id() {
    return parent_id;
  }

  public void setParent_id(long parent_id) {
    this.parent_id = parent_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isIs_active() {
    return is_active;
  }

  public void setIs_active(boolean is_active) {
    this.is_active = is_active;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getProduct_count() {
    return product_count;
  }

  public void setProduct_count(int product_count) {
    this.product_count = product_count;
  }

  public List<CategoryAttribute> getCustom_attributes() {
    return custom_attributes;
  }

  public void setCustom_attributes(List<CategoryAttribute> custom_attributes) {
    this.custom_attributes = custom_attributes;
  }

  public List<Category> getChildren_data() {
    return children_data;
  }

  public void setChildren_data(List<Category> children_data) {
    this.children_data = children_data;
  }
}
