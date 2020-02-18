package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartTotalItem {
	private Integer item_id;
	private Double price;
	private Double base_price;
	private Integer qty;
	private Double row_total;
	private Double base_row_total;
	private Double row_total_with_discount;
	private Double tax_amount;
	private Double base_tax_amount;
	private Double tax_percent;
	private Double discount_amount;
	private Double base_discount_amount;
	private Double discount_percent;
	private Double price_incl_tax;
	private Double base_price_incl_tax;
	private Double row_total_incl_tax;
	private Double base_row_total_incl_tax;
	private String options;
	private Double weee_tax_applied_amount;
	private String weee_tax_applied;
	private Map<String, Object> extension_attributes;
	private String name;
}
