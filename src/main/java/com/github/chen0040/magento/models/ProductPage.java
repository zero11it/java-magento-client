package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {
   private List<Product> items = new ArrayList<>();
   private int total_count = 1000;
   private SearchCriteria search_criteria = new SearchCriteria();

   public List<Product> getItems() {
      return items;
   }

   public void setItems(List<Product> items) {
      this.items = items;
   }

   public int getTotal_count() {
      return total_count;
   }

   public void setTotal_count(int total_count) {
      this.total_count = total_count;
   }

   public SearchCriteria getSearch_criteria() {
      return search_criteria;
   }

   public void setSearch_criteria(SearchCriteria search_criteria) {
      this.search_criteria = search_criteria;
   }
}
