package com.github.chen0040.magento.models.creditmemo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditMemoItem {
	private String additional_data;
	private BigDecimal base_cost;
	private BigDecimal base_discount_amount;
	private BigDecimal base_discount_tax_compensation_amount;
	private BigDecimal base_price;
	private BigDecimal base_price_incl_tax;
	private BigDecimal base_row_total;
	private BigDecimal base_row_total_incl_tax;
	private BigDecimal base_tax_amount;
	private BigDecimal base_weee_tax_applied_amount;
	private BigDecimal base_weee_tax_applied_row_amnt;
	private BigDecimal base_weee_tax_disposition;
	private BigDecimal base_weee_tax_row_disposition;
	private String description;
	private BigDecimal discount_amount;
	private Integer entity_id;
	private BigDecimal discount_tax_compensation_amount;
	private String name;
	private Integer order_item_id;
	private Integer parent_id;
	private BigDecimal price;
	private BigDecimal price_incl_tax;
	private Integer product_id;
	private Integer qty;
	private BigDecimal row_total;
	private BigDecimal row_total_incl_tax;
	private String sku;
	private BigDecimal tax_amount;
	private String weee_tax_applied;
	private BigDecimal weee_tax_applied_amount;
	private BigDecimal weee_tax_applied_row_amount;
	private BigDecimal weee_tax_disposition;
	private BigDecimal weee_tax_row_disposition;
	private ExtensionAttributes extension_attributes;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		List<String> vertex_tax_codes;
		List<String> invoice_text_codes;
		List<String> tax_codes;
	}
}
