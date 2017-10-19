package org.support.project.common.validate;

import org.support.project.common.exception.ArgumentException;
import org.support.project.di.Container;


public class ValidatorFactory {
	
	public static Validator getInstance(String key) {
		if (key.equals(Validator.REQUIRED)) {
			return Container.getComp(RequireValidator.class);
		} else if (key.equals(Validator.MAX_LENGTH)) {
			return Container.getComp(MaxLengthValidator.class);
		} else if (key.equals(Validator.MIN_LENGTH)) {
			return Container.getComp(MinLengthValidator.class);
		} else if (key.equals(Validator.INTEGER)) {
			return Container.getComp(TypeIntegerValidator.class);
		} else if (key.equals(Validator.EXTENSION)) {
			return Container.getComp(ExtensionValidator.class);
		} else if (key.equals(Validator.MAIL)) {
			return Container.getComp(MailValidator.class);
		}
		throw new ArgumentException("[" + key + "] に指定された入力チェックは存在しません");
	}
}
