package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtensionAttributes {

    private StockItem stock_item;

    public ExtensionAttributes() {
    }

    public ExtensionAttributes(StockItem stock_item) {
        this.stock_item = stock_item;
    }
}
