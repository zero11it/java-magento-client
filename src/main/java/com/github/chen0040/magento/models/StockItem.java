package com.github.chen0040.magento.models;

public class StockItem {

    private long item_id;
    private long product_id;
    private long stock_id;
    private int qty;
    private boolean is_in_stock;
    private boolean is_qty_decimal;

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

    public boolean isIs_qty_decimal() {
        return is_qty_decimal;
    }

    public void setIs_qty_decimal(boolean is_qty_decimal) {
        this.is_qty_decimal = is_qty_decimal;
    }
}
