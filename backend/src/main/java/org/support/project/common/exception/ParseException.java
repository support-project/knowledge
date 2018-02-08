package org.support.project.common.exception;

/**
 * MarkdownやHTMLなどのパース失敗を表すException
 * @author Koda
 */
public class ParseException extends Exception {
	/**
	 * シリアルバージョン
	 */
	private static final long serialVersionUID = 1L;

	public ParseException(String key, Throwable cause) {
		super(key, cause);
	}

	public ParseException(String key) {
		super(key);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException() {
		super();
	}

}
