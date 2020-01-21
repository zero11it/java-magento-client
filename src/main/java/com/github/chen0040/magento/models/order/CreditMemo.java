package com.github.chen0040.magento.models.order;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.chen0040.magento.models.MagentoAttribute;
import com.github.chen0040.magento.models.serialization.AttributeValueDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditMemo {
	private long shipping_amount;
	private BigDecimal adjustment_positive;
	private BigDecimal adjustment_negative;
	private CreditMemoAttributes extension_attributes;
}
