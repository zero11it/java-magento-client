package com.github.chen0040.magento.models.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerAddress {
	private Integer id;
	private String region;
	private String region_id;
	private String region_code;
	private String country_id;
	private List<String> street;
	private String telephone;
	private String postcode;
	private String city;
	private String firstname;
	private String lastname;
	private String email;
	private Integer same_as_billing;
	private Integer save_in_address_book;
}
