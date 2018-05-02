package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;

public class RequireValidator implements Validator {
	
	@Override
	public ValidateError validate(Object value, String label, Object... param) {
		if (StringUtils.isEmpty(value)) {
			return new ValidateError("errors.required", label);
		}
		return null;
	}

}
