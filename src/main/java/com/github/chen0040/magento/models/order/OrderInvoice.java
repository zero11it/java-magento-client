package com.github.chen0040.magento.models.order;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderInvoice {
	private Boolean capture;
	private List<SalesDataItem> items;
	private Boolean notify;
	private Boolean appendComment;
	private List<SalesDataComment> comment;
	private InvoiceArguments arguments;
	
	public static OrderInvoice createInvoicePayload() {
		return new OrderInvoice()
				.setCapture(true)
				.setNotify(true);
	}
}
