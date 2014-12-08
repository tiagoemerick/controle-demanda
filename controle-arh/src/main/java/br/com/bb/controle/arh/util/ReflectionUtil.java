package br.com.bb.controle.arh.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("rawtypes")
public class ReflectionUtil {

	public static String getCamelCaseName(Class clazz) {
		return clazz.getSimpleName().replaceFirst(String.valueOf(clazz.getSimpleName().charAt(0)), String.valueOf(clazz.getSimpleName().charAt(0)).toLowerCase());
	}

	/**
	 * Percorre recursivamente a hierarquia da classe at� encontrar a
	 * superclasse que possua um tipo parametrizado no indice passado como
	 * parametro
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class getParametrizedClass(Class clazz, Integer index) {
		Type typeClass = clazz.getGenericSuperclass();
		if (typeClass instanceof ParameterizedType) {
			ParameterizedType genericSuperclass = (ParameterizedType) typeClass;
			return (Class) genericSuperclass.getActualTypeArguments()[index];

		} else {
			return getParametrizedClass(clazz.getSuperclass(), 0);
		}

	}

	/**
	 * Recupera o valor do campo fieldName
	 * 
	 * @param toInvoke
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object toInvoke, String fieldName) {
		Object value = null;
		try {
			Class clazz = toInvoke.getClass();

			Field[] fields = clazz.getDeclaredFields();
			Field field = null;
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals(fieldName)) {
					field = fields[i];
					break;
				}
			}

			if (field == null)
				throw new NoSuchFieldException();

			changeFieldToPublic(field);
			value = field.get(toInvoke);
		} catch (Exception e) {
			// TODO: Definir qual tratamento de exce��o ser� executado...
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Recupera o valor do campo fieldName na classe passada ou nos pais da
	 * classe
	 * 
	 * @param toInvoke
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValueInheritance(Object object, String fieldName) {
		Object value = null;
		try {
			external: for (Class clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					if (field.getName().equals(fieldName)) {
						field.setAccessible(true);
						value = field.get(object);
						break external;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;

	}

	public static Field changeFieldToPublic(Field field) {
		if (field.getModifiers() != Field.PUBLIC)
			field.setAccessible(true);
		return field;
	}

}
