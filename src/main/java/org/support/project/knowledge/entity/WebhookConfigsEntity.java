package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenWebhookConfigsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * Webhooks 設定
 */
@DI(instance=Instance.Prototype)
public class WebhookConfigsEntity extends GenWebhookConfigsEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/** Hooks */
	public static final String HOOK_KNOWLEDGES = "knowledges";
	public static final String HOOK_COMMENTS = "comments";

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static WebhookConfigsEntity get() {
		return Container.getComp(WebhookConfigsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public WebhookConfigsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param hookId HOOK ID
	 */

	public WebhookConfigsEntity(Integer hookId) {
		super( hookId);
	}

	/**
	 * イベント名を返す
	 * @return
	 */
	public String eventName() {
		String hook = getHook();
		return Character.toUpperCase(hook.charAt(0)) + hook.substring(1) + " Events";
	}

	/**
	 * リソースのパスを返す
	 * @return
	 */
	public String resourcePath() {
		return "/org/support/project/knowledge/webhook/" + getHook() + ".json";
	}
}