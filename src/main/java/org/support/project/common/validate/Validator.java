package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;

public interface Validator {
	
	String REQUIRED = "required";
	String MAX_LENGTH = "max_length";
	String MIN_LENGTH = "min_length";
	String INTEGER = "integer";
	String EXTENSION = "extension";
	String MAIL = "mail";
	
	ValidateError validate(Object value, String label, Object... params);
	
}
