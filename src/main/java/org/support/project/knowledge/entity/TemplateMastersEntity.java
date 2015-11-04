package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.Resources;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenTemplateMastersEntity;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.util.ThreadResources;


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
	
	/**
	 * 表示用の名称を変換 
	 */
	@Override
	protected String convLabelName(String label) {
		Resources resources = ThreadResources.get().getResources();
		if ("Type Name".equals(label)) {
			String resource = resources.getResource("knowledge.template.label.name");
			return resource;
		} else if ("Type Icon".equals(label)) {
			String resource = resources.getResource("knowledge.template.label.icon");
			return resource;
		} else if ("Description".equals(label)) {
			String resource = resources.getResource("knowledge.template.label.description");
			return resource;
		}
		return label;
	}
	

}
