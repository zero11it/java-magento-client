package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditMemo {
	private Integer shipping_amount;
	private BigDecimal adjustment_positive;
	private BigDecimal adjustment_negative;
	private CreditMemoAttributes extension_attributes;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class CreditMemoAttributes {
		List<Integer> return_to_stock_items;
	}
}
