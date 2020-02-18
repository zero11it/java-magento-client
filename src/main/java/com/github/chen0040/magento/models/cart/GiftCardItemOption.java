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
public class GiftCardItemOption {
	private String giftcard_amount;
	private Double custom_giftcard_amount;
	private String giftcard_sender_name;
	private String giftcard_recipient_name;
	private String giftcard_sender_email;
	private String giftcard_recipient_email;
	private String giftcard_message;
	private Map<String, Object> extension_attributes;
}
