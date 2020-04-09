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
		return getAllFields(new ArrayList<>(), type);
	}

	private static List<Field> getAllFields(List<Field> fieldList, Class<?> type) {
		fieldList.addAll(Arrays.asList(type.getDeclaredFields()));
		if (type.getSuperclass() != null) {
			getAllFields(fieldList, type.getSuperclass());
		}
		return fieldList;
	}

}
