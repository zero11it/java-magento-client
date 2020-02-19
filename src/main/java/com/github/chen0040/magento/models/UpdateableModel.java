package com.github.chen0040.magento.models;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;

public abstract class UpdateableModel<T> {
	
	protected Map<String, Object> getProperties(UpdateableModel<T> object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return PropertyUtils.describe(object).entrySet().stream()
	        .filter(_entry -> _entry.getValue() != null)
	        .filter(_entry -> ! _entry.getKey().equals("class"))
	        .collect(Collectors.toMap(_entry -> _entry.getKey(), _entry -> _entry.getValue()));
	}
	
	@SuppressWarnings("unchecked")
	protected T setProperty(UpdateableModel<T> object, String property, Object value) {
		try {
			PropertyUtils.setProperty(object, property, value);
			return (T) this;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return (T) this;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T inherit(UpdateableModel<T> parent) {
		try {
			Map<String, Object> childProperties = getProperties(this);
			Map<String, Object> parentProperties = getProperties(parent);
			
			childProperties.forEach((property, value) -> {
					if (canCopyProperty(property) && value != null && parentProperties.get(property) != null) {
						setProperty(this, property, value);
					}
			});
		}
		catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return (T) this;
		}
		
		return (T) this;
	}
	
	protected boolean canCopyProperty(String property) {
		final List<String> blacklist = propertyUpdateBlackList();
		
		return blacklist == null || !blacklist.contains(property);
	}

	protected abstract List<String> propertyUpdateBlackList();
}
