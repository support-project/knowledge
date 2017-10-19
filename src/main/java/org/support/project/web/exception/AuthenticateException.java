package org.support.project.web.exception;

import org.support.project.common.config.Resources;
import org.support.project.common.exception.SystemException;

public class AuthenticateException extends SystemException {

	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticateException(Resources resources, String key, String... params) {
		super(resources, key, params);
	}

	public AuthenticateException(Resources resources, String key, Throwable cause, String... params) {
		super(resources, key, cause, params);
	}

	public AuthenticateException(Resources resources, String key, Throwable cause) {
		super(resources, key, cause);
	}

	public AuthenticateException(Resources resources, String key) {
		super(resources, key);
	}

	public AuthenticateException(String key, String... params) {
		super(key, params);
	}

	public AuthenticateException(String key, Throwable cause, String... params) {
		super(key, cause, params);
	}

	public AuthenticateException(String key, Throwable cause) {
		super(key, cause);
	}

	public AuthenticateException(String key) {
		super(key);
	}

	public AuthenticateException(Throwable cause) {
		super(cause);
	}

}
