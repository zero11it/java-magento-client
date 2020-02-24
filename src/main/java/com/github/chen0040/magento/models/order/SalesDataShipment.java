package com.github.chen0040.magento.models.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	
	//
	// Items
	//
	public SalesDataShipment addItem(SalesDataItem item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		
		this.items.add(item);
		return this;
	}
	
	public SalesDataShipment addItems(SalesDataItem... items) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		
		this.items.addAll(Arrays.asList(items));
		return this;
	}
	
	public SalesDataShipment addItems(Collection<SalesDataItem> items) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		
		this.items.addAll(items);
		return this;
	}
	
	//
	// Tracks
	//
	public SalesDataShipment addTrack(SalesDataTrack track) {
		if (this.tracks == null) {
			this.tracks = new ArrayList<>();
		}
		
		this.tracks.add(track);
		return this;
	}
	
	public SalesDataShipment addTracks(SalesDataTrack... tracks) {
		if (this.tracks == null) {
			this.tracks = new ArrayList<>();
		}
		
		this.tracks.addAll(Arrays.asList(tracks));
		return this;
	}
	
	public SalesDataShipment addTracks(Collection<SalesDataTrack> tracks) {
		if (this.tracks == null) {
			this.tracks = new ArrayList<>();
		}
		
		this.tracks.addAll(tracks);
		return this;
	}
	
	//
	// Packages
	//
	public SalesDataShipment addPackage(SalesDataPackage _package) {
		if (this.packages == null) {
			this.packages = new ArrayList<>();
		}
		
		this.packages.add(_package);
		return this;
	}
	
	public SalesDataShipment addPackages(SalesDataPackage... packages) {
		if (this.packages == null) {
			this.packages = new ArrayList<>();
		}
		
		this.packages.addAll(Arrays.asList(packages));
		return this;
	}
	
	public SalesDataShipment addPackages(Collection<SalesDataPackage> packages) {
		if (this.packages == null) {
			this.packages = new ArrayList<>();
		}
		
		this.packages.addAll(packages);
		return this;
	}
}
