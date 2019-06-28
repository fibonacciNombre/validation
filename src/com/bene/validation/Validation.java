package com.bene.validation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	public static void main(String[] args) throws Exception {
		
		CardDTO car = new CardDTO();
		
		car.setCard("gdgdgd");
		car.setEmail("tttdwh@dhd.com");
		car.setTypeCard("4");
		
		validate(car);
		System.out.println("todo oka");
	}
	
	public static void validate(Object dto) throws Exception {
		Field[] properties = dto.getClass().getDeclaredFields();
		PropertyDescriptor property = null;
		Column column = null;
		Domain domain = null;
		Object value = null;

		for (Field field : properties) {
			column = field.getAnnotation(Column.class);
			if (column != null) {
				property = new PropertyDescriptor(field.getName(), dto.getClass());
				value = property.getReadMethod().invoke(dto, new Object[0]);
				validateNull(field, value, column);
				validateRange(field, value, column);
				validateEmail(field, value, column);
				validateAlphanum(field, value, column);
				domain = field.getAnnotation(Domain.class);
				if (domain != null && !column.nullable()) {
					validateDomain(field, value, domain);
				}
			}
		}

	}
	

	private static void validateDomain(Field field, Object value, Domain domain) throws Exception {
		if (value != null) {
			
			if (value instanceof String) {
				String[] values = domain.string();
				for (String str : values) {
					if (str.equals(value.toString())) {
						return;
					}
				}
				throw new Exception("El campo " + field.getName() + " no esta definido en el dominio - String");
			}
			if (value instanceof Integer) {
				int[] values = domain.integer();
				for (int str : values) {
					if ((Integer)str == value) {
						return;
					}
				}
				throw new Exception("El campo " + field.getName() + " no esta definido en el dominio  - Integer");
			}
			if (value instanceof BigDecimal) {
				String[] values = domain.string();
				for (String str : values) {
					if ((new BigDecimal(str)).equals(value)) {
						return;
					}
				}
				throw new Exception("El campo " + field.getName() + " no esta definido en el dominio  - Big Decimal");
			}
		}
	}

	private static void validateNull(Field field, Object value, Column column) throws Exception {
		if (!column.nullable()) {
			if (value == null) {
				throw new Exception("El campo " + field.getName() + " no puede ser nulo ");
			} else {
				if (value instanceof String) {
					String strValue = (String) value;
					strValue = strValue.trim();
					if (strValue.length() == 0) {
						throw new Exception("El campo " + field.getName() + "  no puede ser '' ");
					}
				}
			}
		}
	}

	private static void validateRange(Field field, Object value, Column column) throws Exception {
		if (column.range()) {
			if (value instanceof Integer) {
				
				Integer n = (Integer) value;
				if (n.intValue() < column.min() || n.intValue() > column.max()) {
					throw new Exception("El campo " + field.getName() + " no se encuentra dentro del rango permitido");
				}
			} else if (value instanceof String) {

			}

		}
	}
	
	//public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
	
	private static void validateEmail(Field field, Object value, Column column) throws Exception {
		if (column.email()) {
			
			if (value instanceof String) {
				String strValue = (String) value;
				strValue = strValue.trim();
				
				Pattern pattern = Pattern.compile(PATTERN_EMAIL);
				Matcher matcher = pattern.matcher(strValue);
				//return matcher.matches();
				
				if (!matcher.matches()) {
					throw new Exception("El campo " + field.getName() + "  debe ser un Email ");
				}
				
			} 

		}
	}
	
	public static final String PATTERN_ALPHANUM = "^[0-9a-zA-Z\\.\\-\\S]*$";
	
	private static void validateAlphanum(Field field, Object value, Column column) throws Exception {
		if (column.valalphanum()) {
			
			if (value instanceof String) {
				String strValue = (String) value;
				//strValue = strValue.trim();
				
				Pattern pattern = Pattern.compile(PATTERN_ALPHANUM);
				Matcher matcher = pattern.matcher(strValue);
				//return matcher.matches();
				
				if (!matcher.matches()) {
					throw new Exception("El campo " + field.getName() + "  debe ser Alphanumerico y sin espacio en blanco");
				}
				
			} 

		}
	}

}
