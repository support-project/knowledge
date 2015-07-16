package org.support.project.knowledge.logic;

import java.util.Locale;

import org.support.project.di.Container;

public class UserLogic extends org.support.project.web.logic.UserLogic {

	public static UserLogic get() {
		return Container.getComp(UserLogic.class);
	}

	/**
	 * 退会
	 * @param loginUserId
	 * @param knowledgeRemove
	 * @param locale
	 * @throws Exception
	 */
	public void withdrawal(Integer loginUserId, boolean knowledgeRemove, Locale locale) throws Exception {
		super.withdrawal(loginUserId, locale);
		if (knowledgeRemove) {
			// ナレッジを削除
			KnowledgeLogic.get().deleteOnUser(loginUserId);
		}
	}

}
