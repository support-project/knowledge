package org.support.project.knowledge.logic;

import java.util.List;

import org.apache.http.HttpStatus;
import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ItemChoicesDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Singleton)
public class TemplateLogic {
	private static Log LOG = LogFactory.getLog(TemplateLogic.class);
	public static TemplateLogic get() {
		return Container.getComp(TemplateLogic.class);
	}
	
	public static final int ITEM_TYPE_TEXT = 0;
	public static final int ITEM_TYPE_TEXTAREA = 1;
	public static final int ITEM_TYPE_RADIO = 10;
	public static final int ITEM_TYPE_CHECKBOX = 11;
	
	public static final String ITEM_TYPE_TEXT_STRING = "text";
	public static final String ITEM_TYPE_TEXTAREA_STRING = "textarea";
	public static final String ITEM_TYPE_RADIO_STRING = "radio";
	public static final String ITEM_TYPE_CHECKBOX_STRING = "checkbox";
	
	/**
	 * 画面から取得した項目の種類のテキストを、DBに保存するInt値に変換
	 * @param type
	 * @return
	 */
	public int convType(String type) {
		if (ITEM_TYPE_TEXT_STRING.equals(type)) {
			return ITEM_TYPE_TEXT;
		} else if (ITEM_TYPE_TEXTAREA_STRING.equals(type)) {
			return ITEM_TYPE_TEXTAREA;
		} else if (ITEM_TYPE_RADIO_STRING.equals(type)) {
			return ITEM_TYPE_RADIO;
		} else if (ITEM_TYPE_CHECKBOX_STRING.equals(type)) {
			return ITEM_TYPE_CHECKBOX;
		}
		LOG.warn("Item type: " + type + " is undefined.");
		return -1;
	}
	/**
	 * DBの項目種類をテキストに変換
	 * @param type
	 * @return
	 */
	public String convType(int type) {
		if (ITEM_TYPE_TEXT == type) {
			return ITEM_TYPE_TEXT_STRING;
		} else if (ITEM_TYPE_TEXTAREA == type) {
			return ITEM_TYPE_TEXTAREA_STRING;
		} else if (ITEM_TYPE_RADIO == type) {
			return ITEM_TYPE_RADIO_STRING;
		} else if (ITEM_TYPE_CHECKBOX == type) {
			return ITEM_TYPE_CHECKBOX_STRING;
		}
		LOG.warn("Item type: " + type + " is undefined.");
		return "";
	}
	
	/**
	 * テンプレートを新規登録
	 * @param template
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity addTemplate(TemplateMastersEntity template, LoginedUser loginedUser) {
		TemplateMastersDao templateDao = TemplateMastersDao.get();
		TemplateItemsDao itemsDao = TemplateItemsDao.get();
		ItemChoicesDao choicesDao = ItemChoicesDao.get();
		// テンプレート保存
		template = templateDao.insert(template);
		Integer typeId = template.getTypeId();
		// テンプレートの入力項目を保存
		insertItems(template, itemsDao, typeId);
		
		return template;
	}
	protected void insertItems(TemplateMastersEntity template, TemplateItemsDao itemsDao, Integer typeId) {
		List<TemplateItemsEntity> itemsEntities = template.getItems();
		int count = 1;
		for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
			templateItemsEntity.setTypeId(typeId);
			templateItemsEntity.setItemNo(count);
			itemsDao.insert(templateItemsEntity);
			count++;
		}
	}
	
	
	/**
	 * テンプレートを更新
	 * @param template
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity uodateTemplate(TemplateMastersEntity template, LoginedUser loginedUser) throws InvalidParamException {
		TemplateMastersDao templateDao = TemplateMastersDao.get();
		TemplateItemsDao itemsDao = TemplateItemsDao.get();
		ItemChoicesDao choicesDao = ItemChoicesDao.get();

		TemplateMastersEntity db = templateDao.selectOnKey(template.getTypeId());
		if (db == null) {
			MessageResult messageResult = new MessageResult();
			messageResult.setStatus(MessageStatus.Warning.getValue());
			messageResult.setCode(HttpStatus.SC_BAD_REQUEST);
			messageResult.setMessage("update data is not found");
			throw new InvalidParamException(messageResult);
		}
		
		// テンプレート更新
		db.setTypeName(template.getTypeName());
		db.setTypeIcon(template.getTypeIcon());
		db.setDescription(template.getDescription());
		templateDao.update(db);
		
		Integer typeId = template.getTypeId();
		if (KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE == typeId) {
			// 項目の増減はできない
			return template;
		} else if(KnowledgeLogic.TEMPLATE_TYPE_BOOKMARK == typeId) {
			// 項目の増減はできない
			return template;
		}
		
		// 項目、選択肢はデリートインサート
		List<TemplateItemsEntity> itemsEntities = itemsDao.physicalSelectOnTypeId(template.getTypeId());
		for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
			if (LOG.isInfoEnabled()) {
				LOG.info("DELETE: " + templateItemsEntity.getTypeId() + ":" + templateItemsEntity.getItemNo());
			}
			itemsDao.physicalDelete(templateItemsEntity); // 物理削除
		}
		List<ItemChoicesEntity> choicesEntities = choicesDao.physicalSelectOnTypeId(template.getTypeId());
		for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
			choicesDao.physicalDelete(itemChoicesEntity); // 物理削除
		}

		// テンプレートの入力項目を保存
		insertItems(template, itemsDao, typeId);
		
		return db;
	}
	
	
	
	/**
	 * テンプレートの削除
	 * @param typeId
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void deleteTemplate(Integer typeId, LoginedUser loginedUser) {
		TemplateMastersDao templateDao = TemplateMastersDao.get();
		TemplateItemsDao itemsDao = TemplateItemsDao.get();
		ItemChoicesDao choicesDao = ItemChoicesDao.get();
		
		TemplateMastersEntity entity = templateDao.selectOnKey(typeId);
		if (entity != null) {
			templateDao.delete(entity);
		}
		
		List<TemplateItemsEntity> itemsEntities = itemsDao.selectOnTypeId(typeId);
		for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
			itemsDao.delete(templateItemsEntity);
		}
		
		List<ItemChoicesEntity> choicesEntities = choicesDao.selectOnTypeId(typeId);
		for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
			choicesDao.delete(itemChoicesEntity);
		}
	}


	
	
	

}
