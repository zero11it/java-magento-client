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
	
	protected void setProperty(UpdateableModel<T> object, String property, Object value) {
		try {
			PropertyUtils.setProperty(object, property, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return;
		}
	}
	
	public UpdateableModel<T> inherit(UpdateableModel<T> parent) {
		try {
			Map<String, Object> childProperties = getProperties(this);
			Map<String, Object> parentProperties = getProperties(parent);
			
			childProperties.forEach((property, value) -> {
					if (canCopyProperty(property) && value == null && parentProperties.get(property) != null) {
						setProperty(this, property, value);
					}
			});
		}
		catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return this;
		}
		
		return this;
	}
	
	protected boolean canCopyProperty(String property) {
		return !propertyBlackList().contains(property);
	}

	protected abstract List<String> propertyBlackList();
}
