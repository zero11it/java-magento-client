package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TierPrices {
	private Integer customer_group_id;
	private Integer qty;
	private Double value;
}
