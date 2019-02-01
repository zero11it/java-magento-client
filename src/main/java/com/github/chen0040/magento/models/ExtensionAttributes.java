package com.github.chen0040.magento.models;

import java.util.ArrayList;
import java.util.List;

public class ExtensionAttributes {

    private StockItem stock_item;
    private List<Long> configurable_product_links = new ArrayList<>();

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

    public List<Long> getConfigurable_product_links() {
        return configurable_product_links;
    }

    public void setConfigurable_product_links(List<Long> configurable_product_links) {
        this.configurable_product_links = configurable_product_links;
    }
}
