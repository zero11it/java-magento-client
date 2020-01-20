package com.github.chen0040.magento.models.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Website {
	    private long id;
	    private String code;
	    private String name;
	    private long default_group_id;
	    //TODO extension_attributes
}
