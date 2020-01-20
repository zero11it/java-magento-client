package com.github.chen0040.magento.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class Account {
	private long id;
	private long group_id;
	private Date created_at;
	private Date updated_at;
	private String created_in = "";
	private String email = "";
	private String firstname = "";
	private String lastname = "";
	private long store_id = 1;
	private long website_id = 1;
	private List<Address> addresses = new ArrayList<>();
	private int disable_auto_group_change = 0;
}
