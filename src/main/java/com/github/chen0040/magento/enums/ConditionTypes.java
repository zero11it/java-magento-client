package com.github.chen0040.magento.enums;

public class ConditionTypes {
	public static final String EQ = "eq"; // Equals.
	public static final String FINSET = "finset"; 	// A value within a set of values
	public static final String FROM = "from"; // The beginning of a range. Must be used with to
	public static final String GT = "gt"; // Greater than
	public static final String GTEQ = "gteq"; // Greater than or equal
	public static final String IN = "in"; // In. The value can contain a comma-separated list of values.
	public static final String LIKE = "like"; // Like. The value can contain the SQL wildcard characters when like is specified.
	public static final String LT = "lt"; // Less than
	public static final String LTEQ = "lteq"; // Less than or equal
	public static final String MOREQ = "moreq"; // More or equal
	public static final String NEQ = "neq"; // Not equal
	public static final String NFINSET = "nfinset"; // A value that is not within a set of values
	public static final String NIN = "nin"; // Not in. The value can contain a comma-separated list of values.
	public static final String NOTNULL = "notnull"; // Not null
	public static final String NULL = "null"; // Null
	public static final String TO = "to"; // The end of a range. Must be used with from
}
