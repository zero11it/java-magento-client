package com.github.chen0040.magento.models.order;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditMemoAttributes {
	List<Integer> return_to_stock_items;
}
