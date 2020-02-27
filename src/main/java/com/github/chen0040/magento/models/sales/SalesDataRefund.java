package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
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
	private Boolean isOnline;
	private Boolean notify;
	private Boolean appendComment;
	private SalesDataComment comment;
	private Arguments arguments;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Arguments {
		private BigDecimal shipping_amount;
		private BigDecimal adjustment_positive;
		private BigDecimal adjustment_negative;
		private ExtensionAttributes extension_attributes;
		
		@Getter
		@Setter
		@NoArgsConstructor
		public static class ExtensionAttributes {
			List<Integer> return_to_stock_items;
		}
	}
	
	public SalesDataRefund addItem(SalesDataItem item) {
		return BeanUtils.addItemToCollection(this, this::getItems, this::setItems, item);
	}
	
	public SalesDataRefund addItems(SalesDataItem... items) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, items);
	}
	
	public SalesDataRefund addItems(Collection<SalesDataItem> items) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, items);
	}
	
	public SalesDataRefund addItems(Order order) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, order.getSalesDataItems());
	}
}
