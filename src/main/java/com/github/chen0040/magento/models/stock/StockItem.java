package com.github.chen0040.magento.models.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by xschen on 15/6/2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class StockItem {
	private Integer item_id;
	private Integer product_id;
	private Integer stock_id;
	private Integer qty;
	private Boolean is_in_stock;
	private Boolean is_qty_decimal;
	private Boolean show_default_notification_message;
	private Boolean use_config_min_qty;
	private Integer min_qty;
	private Integer use_config_min_sale_qty;
	private Integer min_sale_qty;
	private Boolean use_config_max_sale_qty;
	private Integer max_sale_qty;
	private Boolean use_config_backorders;
	private Integer backorders;
	private Boolean use_config_notify_stock_qty;
	private Integer notify_stock_qty;
	private Boolean use_config_qty_increments;
	private Integer qty_increments;
	private Boolean use_config_enable_qty_inc;
	private Boolean enable_qty_increments;
	private Boolean use_config_manage_stock;
	private Boolean manage_stock;
	private Date low_stock_date;
	private Boolean is_decimal_divided;
	private Integer stock_status_changed_auto;
}
