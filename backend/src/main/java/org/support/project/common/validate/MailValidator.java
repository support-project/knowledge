package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;

public class MailValidator implements Validator {
	
	private static final String MAILFORMAT = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
	
	@Override
	public ValidateError validate(Object value, String label, Object... params) {
		if (value == null) {
			return null;
		}
		if (!(value instanceof String)) {
			return new ValidateError("errors.email", label);
		}
		String mail = (String) value;
		if (mail.matches(MAILFORMAT)) {
			return null;
		}
		return new ValidateError("errors.email", label);
	}

}
