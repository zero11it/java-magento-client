package com.github.chen0040.magento.models.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiftMessage {
	private Integer gift_message_id;
	private Integer customer_id;
	private String sender;
	private String recipient;
	private String message;
	private ExtensionAttributes extension_attributes;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		private String entity_id;
		private String entity_type;
		private Integer wrapping_id;
		private Boolean wrapping_allow_gift_receipt;
		private Boolean wrapping_add_printed_card;
	}
}
