package org.support.project.knowledge.bat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

/**
 * Webhookの送信処理は、時間がかかるため、バッチ処理の中で処理する
 *
 * @author nagodon
 */
public class WebhookBat extends AbstractBat {
	/** ログ */
	private static Log LOG = LogFactory.getLog(WebhookBat.class);

	/** webhookの状態：未送信（送信待ち） */
	public static final int WEBHOOK_STATUS_UNSENT = 0;
	/** webhookの状態：なんらかの通信エラーが発生した */
	public static final int WEBHOOK_STATUS_ERROR = -1;

	public static void main(String[] args) throws Exception {
		initLogName("WebhookBat.log");
		configInit(ClassUtils.getShortClassName(WebhookBat.class));

		WebhookBat bat = new WebhookBat();
		bat.dbInit();
		bat.start();

		finishInfo();
	}

	/**
	 * Webhookの実行
	 */
	public void start() {
		Map<String, List<WebhookConfigsEntity>> configs = getMappedConfigs();
		if (0 == configs.size()) {
			// webhookの設定が登録されていなければ、送信処理は終了
			return;
		}

		ProxyConfigsDao proxyConfigDao = ProxyConfigsDao.get();
		ProxyConfigsEntity proxyConfig = proxyConfigDao.selectOnKey(AppConfig.get().getSystemName());

		WebhooksDao dao = WebhooksDao.get();
		List<WebhooksEntity> entities = dao.selectOnStatus(WEBHOOK_STATUS_UNSENT);
		int count = 0;
		for (WebhooksEntity entity : entities) {
			try {
				List<WebhookConfigsEntity> configEntities = configs.get(entity.getHook());
				for (WebhookConfigsEntity configEntity : configEntities) {
					WebhookLogic.get().notify(proxyConfig, configEntity, entity.getContent());
				}
				dao.physicalDelete(entity);
				count++;
			} catch (Exception e) {
				entity.setStatus(WEBHOOK_STATUS_ERROR);
				dao.save(entity);
			}
		}
		LOG.info("Webhook sended. count: " + count);
	}

	/**
	 * 設定を返す
	 * @return
	 */
	private Map<String, List<WebhookConfigsEntity>> getMappedConfigs() {
		Map<String, List<WebhookConfigsEntity>> hooks = new HashMap<String, List<WebhookConfigsEntity>>();

		WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
		List<WebhookConfigsEntity> entities = webhookConfigsDao.selectAll();

		if (0 == entities.size()) {
			return hooks;
		}

		List<WebhookConfigsEntity> knowledgeHooks = new ArrayList<WebhookConfigsEntity>();
		List<WebhookConfigsEntity> commentHooks = new ArrayList<WebhookConfigsEntity>();

		for (WebhookConfigsEntity entity : entities) {
			if (WebhookConfigsEntity.HOOK_KNOWLEDGES.equals(entity.getHook())) {
				knowledgeHooks.add(entity);
			} else if (WebhookConfigsEntity.HOOK_COMMENTS.equals(entity.getHook())) {
				commentHooks.add(entity);
			}
		}

		hooks.put(WebhookConfigsEntity.HOOK_KNOWLEDGES, knowledgeHooks);
		hooks.put(WebhookConfigsEntity.HOOK_COMMENTS, commentHooks);

		return hooks;
	}
}