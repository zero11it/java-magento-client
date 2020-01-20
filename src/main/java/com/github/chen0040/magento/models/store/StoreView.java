package com.github.chen0040.magento.models.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreView {
	    private long id;
	    private String code;
	    private String name;
	    private long website_id;
	    private long store_group_id;
	    private boolean is_active;
	    // TODO extension_attributes
}
