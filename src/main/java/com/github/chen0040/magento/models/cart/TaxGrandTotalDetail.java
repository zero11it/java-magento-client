package com.github.chen0040.magento.models.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class TaxGrandTotalDetail {
	private Double amount;
	private List<TaxGrandTotalDetailRate> rates;
	private Integer group_id;
}
