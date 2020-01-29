package com.github.chen0040.magento.models.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.Customer;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private Integer id;
	private Date created_at;
	private Date updated_at;
	private Boolean is_active;
	private Boolean is_virtual;
	private List<CartItem> items;
	private Integer items_count;
	private Integer items_qty;
	private Customer customer;
}
