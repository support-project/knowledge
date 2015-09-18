package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenTemplateMastersDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;

/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Singleton)
public class TemplateMastersDao extends GenTemplateMastersDao {
	
	public static final int TYPE_ID_KNOWLEDGE = -100;
	public static final int TYPE_ID_BOOKMARK = -99;
	
	
	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static TemplateMastersDao get() {
		return Container.getComp(TemplateMastersDao.class);
	}


	/**
	 * ID 
	 */
	private int currentId = 0;

	/**
	 * IDを採番 
	 * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる 
	 */
	public Integer getNextId() {
		String sql = "SELECT MAX(TYPE_ID) FROM TEMPLATE_MASTERS;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null) {
			if (currentId < integer) {
				currentId = integer;
			}
		}
		currentId++;
		return currentId;
	}
	
	/**
	 * 登録されているテンプレートを全て取得
	 * @return
	 */
	public TemplateMastersEntity selectWithItems(Integer typeId) {
		TemplateMastersEntity template = selectOnKey(typeId);
		List<TemplateItemsEntity> items = TemplateItemsDao.get().selectOnTypeId(typeId);
		for (TemplateItemsEntity item : items) {
			template.getItems().add(item);
			List<ItemChoicesEntity> choices = ItemChoicesDao.get().selectOnItem(typeId, item.getItemNo());
			for (ItemChoicesEntity choice : choices) {
				item.getChoices().add(choice);
			}
		}
		return template;
	}


}
