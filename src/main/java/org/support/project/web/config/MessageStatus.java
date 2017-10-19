package org.support.project.web.config;


/**
 * 結果のタイプ
 * @author koda
 */
public enum MessageStatus {
	Success,
	Info,
	Warning,
	Error;
	public int getValue() {
		return ordinal();
	}
	public static MessageStatus getType(int type) {
		MessageStatus[] values = values();
		return values[type];
	}
}
