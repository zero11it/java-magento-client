package com.github.chen0040.magento.models;

import java.util.Date;

public class StockItems {
   private long item_id = 0;
   private long product_id = 0;
   private long stock_id = 0;
   private int qty = 0;
   private boolean is_in_stock = false;
   private boolean is_qty_decimal = false;
   private boolean show_default_notification_message = false;
   private boolean use_config_min_qty = true;
   private int min_qty = 0;
   private int use_config_min_sale_qty = 1;
   private int min_sale_qty = 1;
   private boolean use_config_max_sale_qty = true;
   private int max_sale_qty = 10000;
   private boolean use_config_backorders = true;
   private int backorders = 0;
   private boolean use_config_notify_stock_qty = true;
   private int notify_stock_qty = 1;
   private boolean use_config_qty_increments = true;
   private int qty_increments = 0;
   private boolean use_config_enable_qty_inc = true;
   private boolean enable_qty_increments = false;
   private boolean use_config_manage_stock = true;
   private boolean manage_stock =true;
   private Date low_stock_date = null;
   private boolean is_decimal_divided = false;
   private int stock_status_changed_auto = 0;

   public long getItem_id() {
      return item_id;
   }

   public void setItem_id(long item_id) {
      this.item_id = item_id;
   }

   public long getProduct_id() {
      return product_id;
   }

   public void setProduct_id(long product_id) {
      this.product_id = product_id;
   }

   public long getStock_id() {
      return stock_id;
   }

   public void setStock_id(long stock_id) {
      this.stock_id = stock_id;
   }

   public int getQty() {
      return qty;
   }

   public void setQty(int qty) {
      this.qty = qty;
   }

   public boolean isIs_in_stock() {
      return is_in_stock;
   }

   public void setIs_in_stock(boolean is_in_stock) {
      this.is_in_stock = is_in_stock;
   }

   public boolean isIs_qty_decimal() {
      return is_qty_decimal;
   }

   public void setIs_qty_decimal(boolean is_qty_decimal) {
      this.is_qty_decimal = is_qty_decimal;
   }

   public boolean isShow_default_notification_message() {
      return show_default_notification_message;
   }

   public void setShow_default_notification_message(boolean show_default_notification_message) {
      this.show_default_notification_message = show_default_notification_message;
   }

   public boolean isUse_config_min_qty() {
      return use_config_min_qty;
   }

   public void setUse_config_min_qty(boolean use_config_min_qty) {
      this.use_config_min_qty = use_config_min_qty;
   }

   public int getMin_qty() {
      return min_qty;
   }

   public void setMin_qty(int min_qty) {
      this.min_qty = min_qty;
   }

   public int getUse_config_min_sale_qty() {
      return use_config_min_sale_qty;
   }

   public void setUse_config_min_sale_qty(int use_config_min_sale_qty) {
      this.use_config_min_sale_qty = use_config_min_sale_qty;
   }

   public int getMin_sale_qty() {
      return min_sale_qty;
   }

   public void setMin_sale_qty(int min_sale_qty) {
      this.min_sale_qty = min_sale_qty;
   }

   public boolean isUse_config_max_sale_qty() {
      return use_config_max_sale_qty;
   }

   public void setUse_config_max_sale_qty(boolean use_config_max_sale_qty) {
      this.use_config_max_sale_qty = use_config_max_sale_qty;
   }

   public int getMax_sale_qty() {
      return max_sale_qty;
   }

   public void setMax_sale_qty(int max_sale_qty) {
      this.max_sale_qty = max_sale_qty;
   }

   public boolean isUse_config_backorders() {
      return use_config_backorders;
   }

   public void setUse_config_backorders(boolean use_config_backorders) {
      this.use_config_backorders = use_config_backorders;
   }

   public int getBackorders() {
      return backorders;
   }

   public void setBackorders(int backorders) {
      this.backorders = backorders;
   }

   public boolean isUse_config_notify_stock_qty() {
      return use_config_notify_stock_qty;
   }

   public void setUse_config_notify_stock_qty(boolean use_config_notify_stock_qty) {
      this.use_config_notify_stock_qty = use_config_notify_stock_qty;
   }

   public int getNotify_stock_qty() {
      return notify_stock_qty;
   }

   public void setNotify_stock_qty(int notify_stock_qty) {
      this.notify_stock_qty = notify_stock_qty;
   }

   public boolean isUse_config_qty_increments() {
      return use_config_qty_increments;
   }

   public void setUse_config_qty_increments(boolean use_config_qty_increments) {
      this.use_config_qty_increments = use_config_qty_increments;
   }

   public int getQty_increments() {
      return qty_increments;
   }

   public void setQty_increments(int qty_increments) {
      this.qty_increments = qty_increments;
   }

   public boolean isUse_config_enable_qty_inc() {
      return use_config_enable_qty_inc;
   }

   public void setUse_config_enable_qty_inc(boolean use_config_enable_qty_inc) {
      this.use_config_enable_qty_inc = use_config_enable_qty_inc;
   }

   public boolean isEnable_qty_increments() {
      return enable_qty_increments;
   }

   public void setEnable_qty_increments(boolean enable_qty_increments) {
      this.enable_qty_increments = enable_qty_increments;
   }

   public boolean isUse_config_manage_stock() {
      return use_config_manage_stock;
   }

   public void setUse_config_manage_stock(boolean use_config_manage_stock) {
      this.use_config_manage_stock = use_config_manage_stock;
   }

   public boolean isManage_stock() {
      return manage_stock;
   }

   public void setManage_stock(boolean manage_stock) {
      this.manage_stock = manage_stock;
   }

   public Date getLow_stock_date() {
      return low_stock_date;
   }

   public void setLow_stock_date(Date low_stock_date) {
      this.low_stock_date = low_stock_date;
   }

   public boolean isIs_decimal_divided() {
      return is_decimal_divided;
   }

   public void setIs_decimal_divided(boolean is_decimal_divided) {
      this.is_decimal_divided = is_decimal_divided;
   }

   public int getStock_status_changed_auto() {
      return stock_status_changed_auto;
   }

   public void setStock_status_changed_auto(int stock_status_changed_auto) {
      this.stock_status_changed_auto = stock_status_changed_auto;
   }
}
