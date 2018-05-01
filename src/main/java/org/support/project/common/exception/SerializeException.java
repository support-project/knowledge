package org.support.project.common.exception;

import org.support.project.common.config.Resources;

public class SerializeException extends SystemException {
	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public SerializeException(Resources resources, String key, String... params) {
		super(resources, key, params);
	}

	public SerializeException(Resources resources, String key, Throwable cause, String... params) {
		super(resources, key, cause, params);
	}

	public SerializeException(Resources resources, String key, Throwable cause) {
		super(resources, key, cause);
	}

	public SerializeException(Resources resources, String key) {
		super(resources, key);
	}


	public SerializeException(String key, String... params) {
		super(key, params);
	}

	public SerializeException(String key, Throwable cause, String... params) {
		super(key, cause, params);
	}

	public SerializeException(String key, Throwable cause) {
		super(key, cause);
	}

	public SerializeException(String key) {
		super(key);
	}

	public SerializeException(Throwable cause) {
		super(cause);
	}

	public SerializeException() {
		super();
	}

}