package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtensionAttribute {

    private StockItems stockItems;

    public ExtensionAttribute() {
    }

    public ExtensionAttribute(StockItems stockItems) {
        this.stockItems = stockItems;
    }
}
