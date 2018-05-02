package org.support.project.common.exception;

import org.support.project.common.config.Resources;

/**
 * まだ実装が完了していない場合に発生する例外
 * @author Koda
 *
 */
public class NotImplementedException extends SystemException {
	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public NotImplementedException(Resources resources, String key, String... params) {
		super(resources, key, params);
	}

	public NotImplementedException(Resources resources, String key, Throwable cause, String... params) {
		super(resources, key, cause, params);
	}

	public NotImplementedException(Resources resources, String key, Throwable cause) {
		super(resources, key, cause);
	}

	public NotImplementedException(Resources resources, String key) {
		super(resources, key);
	}


	public NotImplementedException(String key, String... params) {
		super(key, params);
	}

	public NotImplementedException(String key, Throwable cause, String... params) {
		super(key, cause, params);
	}

	public NotImplementedException(String key, Throwable cause) {
		super(key, cause);
	}

	public NotImplementedException(String key) {
		super(key);
	}

	public NotImplementedException(Throwable cause) {
		super(cause);
	}

	public NotImplementedException() {
		super();
	}



}
