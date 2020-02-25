package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiftCard {
	 private Integer id;
	 private String code;
	 private BigDecimal amount;
	 private BigDecimal base_amount;
}
