package com.github.chen0040.magento.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeanUtils {
	public static <T, I> T addItemToCollection(T self, Supplier<List<I>> getter, Function<List<I>, T> setter, I item) {
		if (item == null) {
			return self;
		}
		
		if (getter.get() == null) {
			setter.apply(new ArrayList<I>());
		}
		
		getter.get().add(item);
		
		return self;
	}
	
	@SafeVarargs
	public static <T, I> T addItemsToCollection(T self, Supplier<List<I>> getter, Function<List<I>, T> setter, I... items) {
		if (items == null) {
			return self;
		}
		
		if (getter.get() == null) {
			setter.apply(new ArrayList<I>());
		}
		
		getter.get().addAll(Arrays.asList(items));
		
		return self;
	}
	
	public static <T, I> T addItemsToCollection(T self, Supplier<List<I>> getter, Function<List<I>, T> setter, Collection<I> items) {
		if (items == null) {
			return self;
		}
		
		if (getter.get() == null) {
			setter.apply(new ArrayList<I>());
		}
		
		getter.get().addAll(items);
		
		return self;
	}
}
