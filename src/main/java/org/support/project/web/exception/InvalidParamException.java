package org.support.project.web.exception;

import org.support.project.web.bean.MessageResult;

/**
 * 入力されたパラメータが不正の場合に生成するException
 * 
 * クライアントサイドにメッセージを返すため、MessageResultを保持する。
 * 
 * @author koda
 *
 */
public class InvalidParamException extends Exception {
	
	private MessageResult messageResult;
	
	public InvalidParamException(MessageResult messageResult) {
		super();
		this.messageResult = messageResult;
	}

	public MessageResult getMessageResult() {
		return messageResult;
	}
	
	
	
	
}
