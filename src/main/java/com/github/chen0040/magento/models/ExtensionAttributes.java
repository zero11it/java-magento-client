package com.github.chen0040.magento.models;

public class ExtensionAttributes {

    private StockItem stock_item;

    public ExtensionAttributes() {
    }

    public ExtensionAttributes(StockItem stock_item) {
        this.stock_item = stock_item;
    }

    public StockItem getStock_item() {
        return stock_item;
    }

    public void setStock_item(StockItem stock_item) {
        this.stock_item = stock_item;
    }
}
