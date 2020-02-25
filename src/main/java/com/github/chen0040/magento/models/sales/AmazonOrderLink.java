package com.github.chen0040.magento.models.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AmazonOrderLink {
	 private String amazon_order_reference_id;
	 private Integer order_id;
}
