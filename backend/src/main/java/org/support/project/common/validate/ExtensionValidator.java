package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;

public class ExtensionValidator implements Validator {

	@Override
	public ValidateError validate(Object value, String label, Object... params) {
		StringBuilder builder = new StringBuilder();
		for (Object object : params) {
			builder.append(object.toString());
		}
		if (value == null) {
			return new ValidateError("errors.extension", label, builder.toString());
		}
		String extension = StringUtils.getExtension(value.toString()).toLowerCase();
		boolean check = false;
		for (Object object : params) {
			if (extension.endsWith(object.toString().toLowerCase())) {
				check = true;
				break;
			}
		}
		if (!check) {
			return new ValidateError("errors.extension", label, builder.toString());
		}
		return null;
	}

}
