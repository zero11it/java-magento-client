package com.github.chen0040.magento.models.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductOption {
	private ExtensionAttributes extension_attributes;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		private List<CustomOption> custom_options;
		private List<BundleOption> bundle_options;
		private List<ConfigurableItemOption> configurable_item_options;
		private List<DownloadableOption> downloadable_options;
		private List<GiftCardItemOption> gift_card_item_options;

		@Getter
		@Setter
		@NoArgsConstructor
		public static class CustomOption {
			private String option_id;
			private String option_value;
			private Map<String, FileInfo> extension_attributes;

			@Getter
			@Setter
			@NoArgsConstructor
			public static class FileInfo {
				private String base64_encoded_data;
				private String type;
				private String name;
			}
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class BundleOption {
			private Integer option_id;
			private Integer option_qty;
			private List<Integer> option_selections;
			@JSONField(deserializeUsing = AttributeValueDeserializer.class)
			private List<MagentoAttribute<?>> extension_attributes;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class ConfigurableItemOption {
			private String option_id;
			private Integer option_value;
			@JSONField(deserializeUsing = AttributeValueDeserializer.class)
			private List<MagentoAttribute<?>> extension_attributes;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class DownloadableOption {
			private List<Integer> downloadable_links;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class GiftCardItemOption {
			private String giftcard_amount;
			private BigDecimal custom_giftcard_amount;
			private String giftcard_sender_name;
			private String giftcard_recipient_name;
			private String giftcard_sender_email;
			private String giftcard_recipient_email;
			private String giftcard_message;
			@JSONField(deserializeUsing = AttributeValueDeserializer.class)
			private List<MagentoAttribute<?>> extension_attributes;
		}
	}
}
