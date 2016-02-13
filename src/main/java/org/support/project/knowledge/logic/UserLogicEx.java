package org.support.project.knowledge.logic;

import java.util.Locale;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance=Instance.Singleton)
public class UserLogicEx extends org.support.project.web.logic.UserLogic {

	public static UserLogicEx get() {
		return Container.getComp(UserLogicEx.class);
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
