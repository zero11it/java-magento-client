package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.github.chen0040.magento.models.Customer;

/**
 * Created by xschen on 10/7/2017.
 */
@Getter
@Setter
public class Cart {
	private long id = 0;
	private String created_at = "";
	private String updated_at = "";
	private boolean is_active = true;
	private boolean is_virtual = false;
	private List<CartItem> items = new ArrayList<>();
	private int items_count = 0;
	private int items_qty = 0;
	private Customer customer = new Customer();
}
