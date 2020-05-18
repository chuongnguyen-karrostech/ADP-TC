package com.APIMM.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Mapping {

	public Mapping() {

	}

	public Object FieldMapping(Object srcFiels, Object destFields)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException {

		List<Object> lstReturn = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object> lstObj = (List<Object>) srcFiels;
		for (Object obj : lstObj) {
			Object temp =  destFields.getClass().newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (!field.getName().equals("serialVersionUID")) {
					field.setAccessible(true);
					Object value = field.get(obj);
					Field tempField = temp.getClass().getDeclaredField(field.getName());
					tempField.setAccessible(true);
					tempField.set(temp, value);
				}
			}
			lstReturn.add(temp);
		}
		return lstReturn;
	}

}
