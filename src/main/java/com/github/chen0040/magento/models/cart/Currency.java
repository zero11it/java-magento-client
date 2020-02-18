package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Currency {
	private String global_currency_code;
	private String base_currency_code;
	private String store_currency_code;
	private String quote_currency_code;
	private Double store_to_base_rate;
	private Double store_to_quote_rate;
	private Double base_to_global_rate;
	private Double base_to_quote_rate;
}
