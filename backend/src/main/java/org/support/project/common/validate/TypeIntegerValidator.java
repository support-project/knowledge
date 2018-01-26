package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;

public class TypeIntegerValidator implements Validator {
	
	@Override
	public ValidateError validate(Object value, String label, Object... param) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof Integer) {
			return null;
		}
		if (value.getClass().isPrimitive() && int.class.isAssignableFrom(value.getClass())) {
			return null;
		}
		
		if (value instanceof String) {
			String str = (String) value;
			if (StringUtils.isInteger(str)) {
				return null;
			}
		}
		
		return new ValidateError("errors.integer", label);
	}
	
	
}
