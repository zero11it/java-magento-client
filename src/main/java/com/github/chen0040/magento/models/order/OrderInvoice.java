package com.github.chen0040.magento.models.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInvoice {
	private boolean capture;
	private List<SalesDataItem> items;
	private boolean notify;
	private boolean appendComment;
	private SalesDataComment comment;
	private InvoiceArguments arguments;
}
