package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtensionAttributes {

    private StockItems stockItems;

    public ExtensionAttributes() {
    }

    public ExtensionAttributes(StockItems stockItems) {
        this.stockItems = stockItems;
    }
}
