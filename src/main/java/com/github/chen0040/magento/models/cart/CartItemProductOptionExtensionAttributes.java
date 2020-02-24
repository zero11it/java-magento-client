package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.github.chen0040.magento.models.sales.ConfigurableItemOption;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartItemProductOptionExtensionAttributes {

	private CartItemProductCustomOption custom_options = new CartItemProductCustomOption();
	private List<CartItemProductBundleOption> bundle_options = new ArrayList<>();
	private CartItemProductDownloadableOption downloadable_option = new CartItemProductDownloadableOption();
	private GiftCardItemOption giftcard_item_option = new GiftCardItemOption();
	private List<ConfigurableItemOption> configurable_item_options = new ArrayList<>();
}
