package com.prulloac.territoriesdata.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

/**
 * @author Prulloac
 */
public class SpecificationBuilder {

	private SpecificationBuilder() throws IllegalAccessException {
		throw new IllegalAccessException("Utility class should not be instantiated");
	}

	@Builder
	public static class SearchCriteria {
		private String key;
		private QueryOperation operation;
		private Object value;

	}

	public static class DynamicSpecification<T> implements Specification<T> {

		private final SearchCriteria criteria;

		public DynamicSpecification(SearchCriteria criteria) {
			this.criteria = criteria;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			if (criteria.operation.equals(QueryOperation.EQUALS)) {
				return criteriaBuilder.equal(root.get(criteria.key), criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.STARTS_WITH)) {
				return criteriaBuilder.like(root.get(criteria.key), criteria.value+"%");
			}
			if (criteria.operation.equals(QueryOperation.ENDS_WITH)) {
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.CONTAINS)) {
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%");
			}
			if (criteria.operation.equals(QueryOperation.LIKE)) {
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%");
			}
			if (criteria.operation.equals(QueryOperation.LIKE_OR_NULL)) {
				return criteriaBuilder.or(criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%"),
						criteriaBuilder.isNull(root.get(criteria.key)));
			}
			if (criteria.operation.equals(QueryOperation.GREATER_THAN)) {
				return criteriaBuilder.gt(root.get(criteria.key), (Number) criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.GREATER_THAN_EQUALS)) {
				return criteriaBuilder.ge(root.get(criteria.key), (Number) criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.LESS_THAN)) {
				return criteriaBuilder.lt(root.get(criteria.key), (Number) criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.LESS_THAN_EQUALS)) {
				return criteriaBuilder.le(root.get(criteria.key), (Number) criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.IN)) {
				return criteriaBuilder.in(root.get(criteria.key)).value(criteria.value);
			}
			if (criteria.operation.equals(QueryOperation.NOT_IN)) {
				return criteriaBuilder.not(criteriaBuilder.in(root.get(criteria.key)).value(criteria.value));
			}
			if (criteria.operation.equals(QueryOperation.IS_NULL)) {
				return criteriaBuilder.isNull(root.get(criteria.key));
			}
			if (criteria.operation.equals(QueryOperation.NOT_NULL)) {
				return criteriaBuilder.isNotNull(root.get(criteria.key));
			}
			return null;
		}
	}

	private static SearchCriteria createSearchCriteria(String filter) {
		String[] split = filter.split(":");
		SearchCriteria.SearchCriteriaBuilder builder = SearchCriteria.builder()
				.key(split[0])
				.operation(QueryOperation.parse(split[1]));
		if (split.length == 3) {
			builder.value(split[2]);
		}
		return builder.build();
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

	public static <T> Specification<T> build(String[] filters, Class<T> entity) {
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
				.map(SpecificationBuilder::createSearchCriteria)
				.map(DynamicSpecification<T>::new)
				.collect(Collectors.toList());
		Specification result = cleansedFilters.get(0);
		for (int i = 1; i < cleansedFilters.size(); i++) {
			result = Specification.where(result)
					.and(cleansedFilters.get(i));
		}
		return result;
	}

}
