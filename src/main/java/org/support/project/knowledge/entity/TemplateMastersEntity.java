package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenTemplateMastersEntity;


/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Prototype)
public class TemplateMastersEntity extends GenTemplateMastersEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	private List<TemplateItemsEntity> items = new ArrayList<TemplateItemsEntity>();
	
	
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static TemplateMastersEntity get() {
		return Container.getComp(TemplateMastersEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public TemplateMastersEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param typeId テンプレートの種類ID
	 */

	public TemplateMastersEntity(Integer typeId) {
		super( typeId);
	}

	public List<TemplateItemsEntity> getItems() {
		return items;
	}

	public void setItems(List<TemplateItemsEntity> items) {
		this.items = items;
	}

}
