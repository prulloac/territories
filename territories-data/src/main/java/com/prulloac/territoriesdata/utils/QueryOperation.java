package com.prulloac.territoriesdata.utils;

import lombok.Getter;

public enum QueryOperation {
	EQUALS("eq"),
	LESS_THAN("lt"),
	LESS_THAN_EQUALS("lte"),
	GREATER_THAN("gt"),
	GREATER_THAN_EQUALS("gte"),
	IN("in"),
	NOT_IN("notIn"),
	LIKE("like"),
	LIKE_OR_NULL("likeOrNull"),
	CONTAINS("containts"),
	STARTS_WITH("startsWith"),
	ENDS_WITH("endsWith"),
	IS_NULL("isNull"),
	NOT_NULL("notNull"),
	;

	public static final String BINARY_OPERATORS_REGEX = "(eq|lt|lte|gt|gte|in|notIn|like|likeOrNull|contains|startsWith|endsWith)";
	public static final String UNARY_OPERATORS_REGEX = "(isNull|notNull)";

	@Getter
	private final String identifier;

	QueryOperation(String identifier) {
		this.identifier = identifier;
	}

	public static QueryOperation parse(String operation) {
		for (QueryOperation o : QueryOperation.values()) {
			if (o.identifier.equals(operation)) {
				return o;
			}
		}
		return null;
	}

}
