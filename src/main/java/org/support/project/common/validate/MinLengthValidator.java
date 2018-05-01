package org.support.project.common.validate;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.exception.ArgumentException;

public class MinLengthValidator implements Validator {

	@Override
	public ValidateError validate(Object value, String label, Object... params) {
		if (value != null) {
			int length = getParam(params);
			if (value instanceof String) {
				String str = (String) value;
				if (str.length() < length) {
					return new ValidateError("errors.minlength", label, String.valueOf(length));
				}
			} else {
				// 最大桁数チェックで、String以外のエラーは不正
				return new ValidateError("errors.invalid", label);
			}
		}
		
		return null;
	}

	private int getParam(Object[] params) {
		if (params == null || params.length == 0) {
			throw new ArgumentException("桁数を指定してください");
		}
		Object obj = params[0];
		if (obj == null) {
			throw new ArgumentException("桁数を指定してください");
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		if (obj.getClass().isPrimitive() && int.class.isAssignableFrom(obj.getClass())) {
			return (int) obj;
		}
		throw new ArgumentException("桁数を指定が不正です");
	}
}
