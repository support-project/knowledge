package org.support.project.knowledge.entity;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenWebhookConfigsEntity;

/**
 * Webhooks 設定
 */
@DI(instance = Instance.Prototype)
public class WebhookConfigsEntity extends GenWebhookConfigsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** Hooks */
    public static final String HOOK_KNOWLEDGES = "knowledges";
    public static final String HOOK_COMMENTS = "comments";
    public static final String HOOK_LIKED_KNOWLEDGE = "liked_knowledge";
    public static final String HOOK_LIKED_COMMENT = "liked_comment";

    /**
     * インスタンス取得 AOPに対応
     * 
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
     * 
     * @param hookId HOOK ID
     */

    public WebhookConfigsEntity(Integer hookId) {
        super(hookId);
    }

    /**
     * イベント名を返す
     * 
     * @return
     */
    public String eventName() {
        String hook = getHook();
        return Character.toUpperCase(hook.charAt(0)) + hook.substring(1) + " Events";
    }

    /**
     * リソースのパスを返す
     * 
     * @return
     */
    public String resourcePath() {
        return "/org/support/project/knowledge/webhook/" + getHook() + ".json";
    }
}