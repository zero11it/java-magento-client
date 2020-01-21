package com.github.chen0040.magento.models.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentArgumentsAttributes {
	private String source_code;
	private String shipping_label;
	private String ext_shipment_id;
	private String ext_return_shipment_id;
	private String ext_location_id;
	private String ext_tracking_url;
	private String ext_tracking_reference;
}
