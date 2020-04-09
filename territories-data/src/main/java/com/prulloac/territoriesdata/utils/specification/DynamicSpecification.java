package com.prulloac.territoriesdata.utils.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Prulloac
 */
@Slf4j
public class DynamicSpecification<T> implements Specification<T> {

	private final transient SearchCriteria criteria;

	public DynamicSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		switch (criteria.getOperation()) {
			case EQUALS:
				return criteriaBuilder.equal(root.get(criteria.key), criteria.value);
			case EQUALS_IGNORE_CASE:
				return criteriaBuilder.equal(criteriaBuilder.upper(root.get(criteria.key)),
						((String)criteria.value).toUpperCase());
			case STARTS_WITH:
				return criteriaBuilder.like(root.get(criteria.key), criteria.value+"%");
			case STARTS_WITH_IGNORE_CASE:
				return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.key)),
						((String)criteria.value).toUpperCase()+"%");
			case ENDS_WITH:
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value);
			case ENDS_WITH_IGNORE_CASE:
				return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.key)),
						"%"+((String)criteria.value).toUpperCase());
			case CONTAINS:
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%");
			case CONTAINS_IGNORE_CASE:
				return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.key)),
						"%"+((String)criteria.value).toUpperCase()+"%");
			case LIKE:
				return criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%");
			case LIKE_IGNORE_CASE:
				return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.key)),
						"%"+((String)criteria.value).toUpperCase()+"%");
			case LIKE_OR_NULL:
				return criteriaBuilder.or(criteriaBuilder.like(root.get(criteria.key), "%"+criteria.value+"%"),
						criteriaBuilder.isNull(root.get(criteria.key)));
			case LIKE_OR_NULL_IGNORE_CASE:
				return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.key)),
						"%"+((String)criteria.value).toUpperCase()+"%"),
						criteriaBuilder.isNull(root.get(criteria.key)));
			case GREATER_THAN:
				return criteriaBuilder.gt(root.get(criteria.key), (Number) criteria.value);
			case GREATER_THAN_EQUALS:
				return criteriaBuilder.ge(root.get(criteria.key), (Number) criteria.value);
			case LESS_THAN:
				return criteriaBuilder.lt(root.get(criteria.key), (Number) criteria.value);
			case LESS_THAN_EQUALS:
				return criteriaBuilder.le(root.get(criteria.key), (Number) criteria.value);
			case IN:
				return criteriaBuilder.in(root.get(criteria.key)).value(criteria.value);
			case NOT_IN:
				return criteriaBuilder.not(criteriaBuilder.in(root.get(criteria.key)).value(criteria.value));
			case IS_NULL:
				return criteriaBuilder.isNull(root.get(criteria.key));
			case NOT_NULL:
				return criteriaBuilder.isNotNull(root.get(criteria.key));
			case BEFORE:
				return buildBefore(root, criteriaBuilder, criteria);
			case AFTER:
				return buildAfter(root, criteriaBuilder, criteria);
			default:
				return null;
		}
	}

	private Predicate buildAfter(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
		try {
			return criteriaBuilder.greaterThan(root.get(criteria.key).as(Timestamp.class),
					Timestamp.valueOf(LocalDateTime.parse((String)criteria.value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
		} catch (Exception e) {
			return criteriaBuilder.greaterThan(root.get(criteria.key).as(Date.class),
					toDate(LocalDate.parse((String)criteria.value, DateTimeFormatter.ISO_LOCAL_DATE)));
		}
	}

	private Predicate buildBefore(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
		try {
			return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.key).as(Timestamp.class),
					Timestamp.valueOf(LocalDateTime.parse((String)criteria.value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
		} catch (Exception e) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.key).as(Date.class),
					toDate(LocalDate.parse((String)criteria.value, DateTimeFormatter.ISO_LOCAL_DATE)));
		}
	}

	private Date toDate(ChronoLocalDate field) {
		return new Date(field.toEpochDay());
	}

}
