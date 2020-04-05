package com.prulloac.territoriesdata.utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import static com.prulloac.territoriesdata.utils.EntityScanUtil.getAllFields;

/**
 * @author Prulloac
 */
@Slf4j
public class SorteableColumnIdentifier {

	private SorteableColumnIdentifier() throws IllegalAccessException {
		throw new IllegalAccessException("Utility class should not be instantiated");
	}

	public static List<String> getSorteableColumns(Class<?> entity) {
		if (entity.isAnnotationPresent(Entity.class)) {
			return getAllFields(entity)
					.stream()
					.filter(x ->
							x.isAnnotationPresent(SorteableColumn.class) &&
							x.isAnnotationPresent(Column.class))
					.map(Field::getName)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}
