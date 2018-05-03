package org.support.project.common.exception;

import org.support.project.common.config.Resources;

/**
 * メソッドの引数が正しく無い場合に発生するException
 * @author Koda
 *
 */
public class ArgumentException extends SystemException {
	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public ArgumentException(Resources resources, String key, String... params) {
		super(resources, key, params);
	}

	public ArgumentException(Resources resources, String key, Throwable cause, String... params) {
		super(resources, key, cause, params);
	}

	public ArgumentException(Resources resources, String key, Throwable cause) {
		super(resources, key, cause);
	}

	public ArgumentException(Resources resources, String key) {
		super(resources, key);
	}

	public ArgumentException(String key, String... params) {
		super(key, params);
	}

	public ArgumentException(String key, Throwable cause, String... params) {
		super(key, cause, params);
	}

	public ArgumentException(String key, Throwable cause) {
		super(key, cause);
	}

	public ArgumentException(String key) {
		super(key);
	}

	public ArgumentException(Throwable cause) {
		super(cause);
	}

	public ArgumentException() {
		super();
	}



}
