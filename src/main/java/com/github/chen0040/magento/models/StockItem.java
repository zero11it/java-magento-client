package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockItem {

    private int qty;
    private boolean is_in_stock;
}
