package br.com.bb.controle.arh.util;

import java.util.Comparator;

public class PropertyComparator<T> implements Comparator<T> {
	private String fieldToCompare;
	private Boolean ascending;

	public PropertyComparator(String fieldToCompare, Boolean ascending) {
		this.fieldToCompare = fieldToCompare;
		this.ascending = ascending;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(Object o1, Object o2) {
		int returnValue = 0;
		boolean inverse = (ascending != null && !ascending.booleanValue());

		if (o1 == null || o2 == null) {
			if (o1 == null && o2 == null) {
				returnValue = 0;
			} else if (o1 == null) {
				returnValue = -1;
			} else if (o2 == null) {
				returnValue = 1;
			}

			if (inverse) {
				returnValue *= -1;
			}

			return returnValue;
		}

		T p1 = (T) o1;
		T p2 = (T) o2;

		Comparable value1 = (Comparable) ReflectionUtil.getFieldValueInheritance(p1, fieldToCompare);
		Comparable value2 = (Comparable) ReflectionUtil.getFieldValueInheritance(p2, fieldToCompare);

		if (value1 == null && value2 == null) {
			returnValue = 0;
		} else if (value1 == null) {
			returnValue = -1;
		} else if (value2 == null) {
			returnValue = 1;
		} else {
			returnValue = value1.compareTo(value2);
		}

		if (inverse) {
			returnValue *= -1;
		}

		return returnValue;
	}
}
