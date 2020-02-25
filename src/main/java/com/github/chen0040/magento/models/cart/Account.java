package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.github.chen0040.magento.models.sales.CustomerAddress;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class Account {
	private Integer id;
	private Integer group_id;
	private Date created_at;
	private Date updated_at;
	private String created_in;
	private String email;
	private String firstname;
	private String lastname;
	private Integer store_id;
	private Integer website_id;
	private List<CustomerAddress> addresses;
	private Integer disable_auto_group_change;
}
