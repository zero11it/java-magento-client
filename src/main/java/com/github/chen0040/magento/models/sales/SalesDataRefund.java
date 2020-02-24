package com.github.chen0040.magento.models.sales;

import java.util.Collection;
import java.util.List;

import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.utils.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataRefund {
	private List<SalesDataItem> items;
	private Boolean notify;
	private Boolean appendComment;
	private SalesDataComment comment;
	private CreditMemo arguments;
	
	public SalesDataRefund addItem(SalesDataItem item) {
		return BeanUtils.addItemToCollection(this, this.items, item);
	}
	
	public SalesDataRefund addItems(SalesDataItem... items) {
		return BeanUtils.addItemsToCollection(this, this.items, items);
	}
	
	public SalesDataRefund addItems(Collection<SalesDataItem> items) {
		return BeanUtils.addItemsToCollection(this, this.items, items);
	}
	
	public SalesDataRefund addItems(Order order) {
		return BeanUtils.addItemsToCollection(this, this.items, order.getSalesDataItems());
	}
}
