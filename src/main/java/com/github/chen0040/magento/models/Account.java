package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
   private long id = 21;
   private long group_id = 1;
   private Date created_at;
   private Date updated_at;
   private String created_in = "Store view 1 - website_id_1 - group_id_1";
   private String email = "xs0040@gmail.com";
   private String firstname = "Xianshun";
   private String lastname = "Chen";
   private long store_id  = 1;
   private long website_id = 1;
   private List<Address> addresses = new ArrayList<>();
   private int disable_auto_group_change = 0;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public long getGroup_id() {
      return group_id;
   }

   public void setGroup_id(long group_id) {
      this.group_id = group_id;
   }

   public Date getCreated_at() {
      return created_at;
   }

   public void setCreated_at(Date created_at) {
      this.created_at = created_at;
   }

   public Date getUpdated_at() {
      return updated_at;
   }

   public void setUpdated_at(Date updated_at) {
      this.updated_at = updated_at;
   }

   public String getCreated_in() {
      return created_in;
   }

   public void setCreated_in(String created_in) {
      this.created_in = created_in;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public long getStore_id() {
      return store_id;
   }

   public void setStore_id(long store_id) {
      this.store_id = store_id;
   }

   public long getWebsite_id() {
      return website_id;
   }

   public void setWebsite_id(long website_id) {
      this.website_id = website_id;
   }

   public List<Address> getAddresses() {
      return addresses;
   }

   public void setAddresses(List<Address> addresses) {
      this.addresses = addresses;
   }

   public int getDisable_auto_group_change() {
      return disable_auto_group_change;
   }

   public void setDisable_auto_group_change(int disable_auto_group_change) {
      this.disable_auto_group_change = disable_auto_group_change;
   }
}
