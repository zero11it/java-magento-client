package com.github.chen0040.magento.models.shipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentArgumentsAttributes {
	private String source_code;
	private String shipping_label;
	private String ext_shipment_id;
	private String ext_return_shipment_id;
	private String ext_location_id;
	private String ext_tracking_url;
	private String ext_tracking_reference;
}
