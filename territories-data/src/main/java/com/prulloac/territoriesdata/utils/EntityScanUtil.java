package com.prulloac.territoriesdata.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Prulloac
 */
@Slf4j
public class EntityScanUtil {

	private EntityScanUtil() throws IllegalAccessException {
		throw new IllegalAccessException("Utility class should not be instantiated");
	}

	public static List<Field> getAllFields(Class<?> type) {
		long start = System.currentTimeMillis();
		List<Field> proceed = getAllFields(new ArrayList<>(), type);
		long end = System.currentTimeMillis();
		log.info("Execution time of com.prulloac.territoriesdata.utils.getAllFields: {}ms", end - start);
		return proceed;
	}

	private static List<Field> getAllFields(List<Field> fieldList, Class<?> type) {
		fieldList.addAll(Arrays.asList(type.getDeclaredFields()));
		if (type.getSuperclass() != null) {
			getAllFields(fieldList, type.getSuperclass());
		}
		return fieldList;
	}

}
