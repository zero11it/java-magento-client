package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataShippingAssignment {
	private SalesDataAddress address;
	private String method;
	private Total total;
	private ExtensionAttributes extension_attributes;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Total {
		private BigDecimal base_shipping_amount;
		private BigDecimal base_shipping_canceled;
		private BigDecimal base_shipping_discount_amount;
		private BigDecimal base_shipping_discount_tax_compensation_amnt;
		private BigDecimal base_shipping_incl_tax;
		private BigDecimal base_shipping_invoiced;
		private BigDecimal base_shipping_refunded;
		private BigDecimal base_shipping_tax_amount;
		private BigDecimal base_shipping_tax_refunded;
		private BigDecimal shipping_amount;
		private BigDecimal shipping_canceled;
		private BigDecimal shipping_discount_amount;
		private BigDecimal shipping_discount_tax_compensation_amount;
		private BigDecimal shipping_incl_tax;
		private BigDecimal shipping_invoiced;
		private BigDecimal shipping_refunded;
		private BigDecimal shipping_tax_amount;
		private BigDecimal shipping_tax_refunded;
		@JSONField(deserializeUsing = AttributeValueDeserializer.class)
		private List<MagentoAttribute<?>> extension_attributes;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExtensionAttributes {
		private String ext_order_id;
		private ShippingExperience shipping_experience;
		private CollectionPoint collection_point;

		@Getter
		@Setter
		@NoArgsConstructor
		public static class ShippingExperience {
			private String label;
			private String code;
			private BigDecimal cost;
		}

		@Getter
		@Setter
		@NoArgsConstructor
		public static class CollectionPoint {
			private Integer recipient_address_id;
			private String collection_point_id;
			private String name;
			private String country;
			private String region;
			private String postcode;
			private String city;
			private List<String> street;
		}
	}
}
