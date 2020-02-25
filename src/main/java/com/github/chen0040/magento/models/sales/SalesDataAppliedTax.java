package com.github.chen0040.magento.models.sales;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataAppliedTax {
	 private String code;
	 private String title;
	 private BigDecimal percent;
	 private BigDecimal amount;
	 private BigDecimal base_amount;
	 private ExtensionAttributes extension_attributes;
	 
	 public static class ExtensionAttributes {
		 List<Rate> rates;
		 
		 @Getter
		 @Setter
		 @NoArgsConstructor
		 public class Rate {
			 private String code;
			 private String title;
			 private BigDecimal percent;
			 @JSONField(deserializeUsing = AttributeValueDeserializer.class)
			 private List<MagentoAttribute<?>> extension_attributes;
		 }
	 }
}