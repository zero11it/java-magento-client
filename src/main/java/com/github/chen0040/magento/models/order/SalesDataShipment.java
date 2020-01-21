package com.github.chen0040.magento.models.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDataShipment {
	private List<SalesDataItem> items;
	private boolean notify;
	private boolean appendComment;
	private SalesDataComment comment;
	private List<SalesDataTrack> tracks;
	private List<SalesDataPackage> packages;
	private ShipmentArguments arguments;
}
