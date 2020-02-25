package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataItem {
	private String additional_data;
	private BigDecimal base_cost;
	private BigDecimal base_discount_amount;
	private BigDecimal base_discount_tax_compensation_amount;
	private BigDecimal base_price;
	private BigDecimal base_price_incl_tax;
	private BigDecimal base_row_total;
	private BigDecimal base_row_total_incl_tax;
	private BigDecimal base_tax_amount;
	private String description;
	private BigDecimal discount_amount;
	private Integer entity_id;
	private BigDecimal discount_tax_compensation_amount;
	private String name;
	private Integer parent_id;
	private BigDecimal price;
	private BigDecimal price_incl_tax;
	private Integer product_id;
	private BigDecimal row_total;
	private BigDecimal row_total_incl_tax;
	private String sku;
	private BigDecimal tax_amount;
	private SalesDataItemExtensionAttributes extension_attributes;
	private Integer order_item_id;
	private Integer qty;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SalesDataItemExtensionAttributes {
		private List<String> vertex_tax_codes;
		private List<String> invoice_text_codes;
		private List<String> tax_codes;
	}

	public SalesDataItem(Integer order_item_id, Integer qty) {
		this.order_item_id = order_item_id;
		this.qty = qty;
	}
}
