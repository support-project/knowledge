package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.web.bean.LoginedUser;

@DI(instance=Instance.Singleton)
public class TemplateLogic {
	private static Log LOG = LogFactory.getLog(TemplateLogic.class);
	public static TemplateLogic get() {
		return Container.getComp(TemplateLogic.class);
	}
	
	public static final int ITEM_TYPE_TEXT = 0;
	public static final int ITEM_TYPE_RADIO = 10;
	public static final int ITEM_TYPE_CHECKBOX = 10;
	
	public static final String ITEM_TYPE_TEXT_STRING = "text";
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
		} else if (ITEM_TYPE_RADIO_STRING.equals(type)) {
			return ITEM_TYPE_RADIO;
		} else if (ITEM_TYPE_CHECKBOX_STRING.equals(type)) {
			return ITEM_TYPE_CHECKBOX;
		}
		return -1;
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
		template = templateDao.insert(template);
		
		List<TemplateItemsEntity> itemsEntities = template.getItems();
		for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
			templateItemsEntity.setTypeId(template.getTypeId());
			itemsDao.insert(templateItemsEntity);
		}
		
		return template;
	}

	
	
	

}
