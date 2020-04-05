package com.prulloac.territoriesdata.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PageRequestBuilder {

	private PageRequestBuilder() throws IllegalAccessException {
		throw new IllegalAccessException("Utility class should not be instantiated");
	}

	private static boolean isValidSortCombosSyntax(String[] sortCombos) {
		return Arrays.stream(sortCombos).allMatch(combo ->
				combo.matches("^\\w*:((?i)asc|desc)$"));
	}

	private static Sort.Order propertyToOrder(String property) {
		String[] split = property.split(":");
		if ("ASC".equalsIgnoreCase(split[1])) {
			return Sort.Order.asc(split[0]);
		} else {
			return Sort.Order.desc(split[0]);
		}
	}

	private static List<String> filterSortFields(String[] sortCombos, List<String> sorteableBy) {
		if (null == sortCombos || sortCombos.length == 0) {
			return Collections.emptyList();
		}
		Assert.isTrue(isValidSortCombosSyntax(sortCombos),
				"combos syntax error: it should be noted as <field>:asc|desc");
		return Arrays.stream(sortCombos)
				.filter(combo -> sorteableBy.contains(combo.split(":")[0]))
				.collect(Collectors.toList());
	}

	public static PageRequest buildRequest(Integer offset, Integer limit, String[] sortCombos, Class<?> entity) {
		int size = null == limit || limit < 1 ? Integer.MAX_VALUE : limit;
		int page = null == offset || offset < 0 ? Integer.MAX_VALUE : offset;
		if (null != sortCombos && sortCombos.length != 0) {
			List<Sort.Order> sort = filterSortFields(sortCombos, SorteableColumnIdentifier.getSorteableColumns(entity))
					.stream()
					.map(PageRequestBuilder::propertyToOrder)
					.collect(Collectors.toList());
			if (!sort.isEmpty()) {
				return PageRequest.of(page, size, Sort.by(sort));
			}
		}
		return PageRequest.of(page, size);
	}

}
