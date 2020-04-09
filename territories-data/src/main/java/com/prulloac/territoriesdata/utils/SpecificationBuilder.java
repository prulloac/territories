package com.prulloac.territoriesdata.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import com.prulloac.territoriesdata.utils.specification.DynamicSpecification;
import com.prulloac.territoriesdata.utils.specification.QueryOperation;
import com.prulloac.territoriesdata.utils.specification.SearchCriteria;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Prulloac
 */
public class SpecificationBuilder<T> {

	private Class<T> entity;

	public SpecificationBuilder(Class<T> entity) {
		this.entity = entity;
	}

	private SearchCriteria createSearchCriteria(String filter) {
		String[] split = filter.split(":");
		SearchCriteria criteria = new SearchCriteria();
		criteria.setKey(split[0]);
		criteria.setOperation(QueryOperation.parse(split[1]));
		int binaryOperationLength = 3;
		if (split.length == binaryOperationLength) {
			criteria.setValue(split[2]);
		}
		return criteria;
	}

	private static boolean isValidFilterSyntax(String[] filters) {
		String regex = "^\\w*:("+QueryOperation.UNARY_OPERATORS_REGEX+"|"+QueryOperation.BINARY_OPERATORS_REGEX+":\\w*)$";
		return Arrays.stream(filters).allMatch(filter ->
				filter.matches(regex));
	}

	private static List<String> filterFilters(String[] filters, List<String> fields) {
		if (null == filters || filters.length == 0) {
			return Collections.emptyList();
		}
		Assert.isTrue(isValidFilterSyntax(filters),
				"filter syntax error: it should be noted as <field>:<operation>[:<value>]");
		return Arrays.stream(filters)
				.filter(filter -> fields.contains(filter.split(":")[0]))
				.collect(Collectors.toList());
	}

	public Specification<T> build(String[] filters) {
		if (null == filters || filters.length == 0) {
			return null;
		}
		List<String> fields = EntityScanUtil.getAllFields(entity)
				.stream()
				.filter(field ->
						field.isAnnotationPresent(Column.class) ||
						field.isAnnotationPresent(JoinColumn.class))
				.map(Field::getName)
				.collect(Collectors.toList());
		List<DynamicSpecification<T>> cleansedFilters = filterFilters(filters, fields)
				.stream()
				.map(this::createSearchCriteria)
				.map(DynamicSpecification<T>::new)
				.collect(Collectors.toList())
				;
		Specification<T> result = cleansedFilters.get(0);
		for (int i = 1; i < cleansedFilters.size(); i++) {
			result = Specification.where(result)
					.and(cleansedFilters.get(i));
		}
		return result;
	}

}
