package com.github.chen0040.magento.models.search;

public enum ConditionType {
	EQUAL("eq"),
	IN_SET("finset"),
	FROM("from"),
	GREATER_THAN("gt"),
	GREATER_THAN_OR_EQUAL("gteq"),
	IN("in"),
	LIKE("like"),
	LESS_THAN("lt"),
	LESS_THAN_OR_EQUAL("lteq"),
	MORE_OR_EQUAL("moreq"),
	NOT_EQUAL("neq"),
	NOT_IN_SET("nfinset"),
	NOT_IN("nin"),
	NOT_NULL("notnull"),
	NULL("null"),
	TO("to")
	;
	
	private final String value;
	
	private ConditionType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
