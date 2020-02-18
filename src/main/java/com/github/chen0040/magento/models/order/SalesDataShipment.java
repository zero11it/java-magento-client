package com.github.chen0040.magento.models.order;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDataShipment {
	private List<SalesDataItem> items;
	private Boolean notify;
	private Boolean appendComment;
	private SalesDataComment comment;
	private List<SalesDataTrack> tracks;
	private List<SalesDataPackage> packages;
	private ShipmentArguments arguments;
}
