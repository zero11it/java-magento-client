package com.github.chen0040.magento.models.sales;

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
public class SalesDataInvoice {
	private Boolean capture;
	private List<SalesDataItem> items;
	private Boolean notify;
	private Boolean appendComment;
	private List<SalesDataComment> comment;
	private InvoiceArguments arguments;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class InvoiceArguments {
		@JSONField(deserializeUsing = AttributeValueDeserializer.class)
		private List<MagentoAttribute<?>> extension_attributes;
	}
	
	public static SalesDataInvoice createInvoicePayload() {
		return new SalesDataInvoice()
				.setCapture(true)
				.setNotify(true);
	}
}
