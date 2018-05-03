package org.support.project.common.exception;

import org.support.project.common.config.Resources;



/**
 * システムの異常を表すException
 * 
 */
public class SystemException extends BaseException {
	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public SystemException(Resources resources, String key, String... params) {
		super(resources, key, params);
	}

	public SystemException(Resources resources, String key, Throwable cause, String... params) {
		super(resources, key, cause, params);
	}

	public SystemException(Resources resources, String key, Throwable cause) {
		super(resources, key, cause);
	}

	public SystemException(Resources resources, String key) {
		super(resources, key);
	}

	public SystemException(String key, String... params) {
		super(key, params);
	}

	public SystemException(String key, Throwable cause, String... params) {
		super(key, cause, params);
	}

	public SystemException(String key, Throwable cause) {
		super(key, cause);
	}

	public SystemException(String key) {
		super(key);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException() {
		super();
	}





}
