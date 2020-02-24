package com.github.chen0040.magento.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanUtils {
	public static <T, I> T addItemToCollection(T self, List<I> collection, I item) {
		if (item == null) {
			return self;
		}
		
		if (collection == null) {
			collection = new ArrayList<I>();
		}
		
		collection.add(item);
		
		return self;
	}
	
	@SafeVarargs
	public static <T, I> T addItemsToCollection(T self, List<I> collection, I... items) {
		if (items == null) {
			return self;
		}
		
		if (collection == null) {
			collection = new ArrayList<I>();
		}
		
		collection.addAll(Arrays.asList(items));
		
		return self;
	}
	
	public static <T, I> T addItemsToCollection(T self, List<I> collection, Collection<I> items) {
		if (items == null) {
			return self;
		}
		
		if (collection == null) {
			collection = new ArrayList<I>();
		}
		
		collection.addAll(items);
		
		return self;
	}
}
