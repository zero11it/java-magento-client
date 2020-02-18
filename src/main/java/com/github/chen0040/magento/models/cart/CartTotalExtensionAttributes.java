package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartTotalExtensionAttributes {
	private Double base_customer_balance_amount;
	private Double customer_balance_amount;
	private Double reward_points_balance;
	private Double reward_currency_amount;
	private Double base_reward_currency_amount;
}
