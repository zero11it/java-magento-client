package com.github.chen0040.magento.models.invoice;

import java.util.List;

import com.github.chen0040.magento.models.sales.SalesDataComment;
import com.github.chen0040.magento.models.sales.SalesDataItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Invoice {
	private Boolean capture;
	private List<SalesDataItem> items;
	private Boolean notify;
	private Boolean appendComment;
	private List<SalesDataComment> comment;
	private InvoiceArguments arguments;
	
	public static Invoice createInvoicePayload() {
		return new Invoice()
				.setCapture(true)
				.setNotify(true);
	}
}
