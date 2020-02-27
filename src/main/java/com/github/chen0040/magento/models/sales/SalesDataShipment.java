package com.github.chen0040.magento.models.sales;

import java.util.Collection;
import java.util.List;

import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.shipment.ShipmentArguments;
import com.github.chen0040.magento.utils.BeanUtils;

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
	
	//
	// Items
	//
	public SalesDataShipment addItem(SalesDataItem item) {
		return BeanUtils.addItemToCollection(this, this::getItems, this::setItems, item);
	}
	
	public SalesDataShipment addItems(SalesDataItem... items) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, items);
	}
	
	public SalesDataShipment addItems(Collection<SalesDataItem> items) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, items);
	}
	
	public SalesDataShipment addItems(Order order) {
		return BeanUtils.addItemsToCollection(this, this::getItems, this::setItems, order.getSalesDataItems());
	}
	
	//
	// Tracks
	//
	public SalesDataShipment addTrack(SalesDataTrack track) {
		return BeanUtils.addItemToCollection(this, this::getTracks, this::setTracks, track);
	}
	
	public SalesDataShipment addTracks(SalesDataTrack... tracks) {
		return BeanUtils.addItemsToCollection(this, this::getTracks, this::setTracks, tracks);
	}
	
	public SalesDataShipment addTracks(Collection<SalesDataTrack> tracks) {
		return BeanUtils.addItemsToCollection(this, this::getTracks, this::setTracks, tracks);
	}
	
	//
	// Packages
	//
	public SalesDataShipment addPackage(SalesDataPackage _package) {
		return BeanUtils.addItemToCollection(this, this::getPackages, this::setPackages, _package);
	}
	
	public SalesDataShipment addPackages(SalesDataPackage... packages) {
		return BeanUtils.addItemsToCollection(this, this::getPackages, this::setPackages, packages);
	}
	
	public SalesDataShipment addPackages(Collection<SalesDataPackage> packages) {
		return BeanUtils.addItemsToCollection(this, this::getPackages, this::setPackages, packages);
	}
}
