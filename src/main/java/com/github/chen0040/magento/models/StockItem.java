package com.github.chen0040.magento.models;

public class StockItem {

    private int qty;
    private boolean is_in_stock;

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
}
