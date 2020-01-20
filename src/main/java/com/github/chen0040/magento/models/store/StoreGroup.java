package com.github.chen0040.magento.models.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreGroup {
	    private long id;
	    private long website_id;
	    private long root_category_id;
	    private long default_store_id;
	    private String name;
	    private String code;
	    //TODO extension_attributes
}
