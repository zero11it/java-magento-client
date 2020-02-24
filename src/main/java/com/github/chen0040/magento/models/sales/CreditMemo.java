package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
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
}
