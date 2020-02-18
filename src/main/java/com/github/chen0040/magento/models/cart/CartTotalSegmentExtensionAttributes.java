package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartTotalSegmentExtensionAttributes {
	private List<TaxGrandTotalDetail> tax_grandtotal_details;
	private String gift_cards;
	private String gw_order_id;
	private List<String> gw_item_ids;
	private String gw_allow_gift_receipt;
	private String gw_add_card;
	private String gw_price;
	private String gw_base_price;
	private String gw_items_price;
	private String gw_items_base_price;
	private String gw_card_price;
	private String gw_card_base_price;
	private String gw_base_tax_amount;
	private String gw_tax_amount;
	private String gw_items_base_tax_amount;
	private String gw_items_tax_amount;
	private String gw_card_base_tax_amount;
	private String gw_card_tax_amount;
	private String gw_price_incl_tax;
	private String gw_base_price_incl_tax;
	private String gw_card_price_incl_tax;
	private String gw_card_base_price_incl_tax;
	private String gw_items_price_incl_tax;
	private String gw_items_base_price_incl_tax;
}
