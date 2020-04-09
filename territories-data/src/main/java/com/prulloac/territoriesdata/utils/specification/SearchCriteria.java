package com.prulloac.territoriesdata.utils.specification;

import lombok.Data;

/**
 * @author Prulloac
 */
@Data
public class SearchCriteria {
	String key;
	QueryOperation operation;
	Object value;
}
