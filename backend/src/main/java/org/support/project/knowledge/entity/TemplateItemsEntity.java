package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.config.Resources;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenTemplateItemsEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.util.ThreadResources;

/**
 * テンプレートの項目
 */
@DI(instance = Instance.Prototype)
public class TemplateItemsEntity extends GenTemplateItemsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    private List<ItemChoicesEntity> choices = new ArrayList<>();

    /** 値 */
    private String itemValue;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static TemplateItemsEntity get() {
        return Container.getComp(TemplateItemsEntity.class);
    }

    /**
     * コンストラクタ
     */
    public TemplateItemsEntity() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param itemNo 項目NO
     * @param typeId テンプレートの種類ID
     */

    public TemplateItemsEntity(Integer itemNo, Integer typeId) {
        super(itemNo, typeId);
    }

    public List<ItemChoicesEntity> getChoices() {
        return choices;
    }

    public void setChoices(List<ItemChoicesEntity> choices) {
        this.choices = choices;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    /**
     * アイテム種類のテキストを取得
     * 
     * @return
     */
    public String getItemTypeText() {
        return TemplateLogic.get().convType(getItemType());
    }

    /**
     * アイテム種類のテキストを設定
     * 
     * @return
     */
    public void setItemTypeText(String type) {
        super.setItemType(TemplateLogic.get().convType(type));
    }

    /**
     * 表示用の名称を変換
     */
    @Override
    protected String convLabelName(String label) {
        Resources resources = ThreadResources.get().getResources();
        if ("Item Name".equals(label)) {
            return resources.getResource("knowledge.template.label.item.title");
        } else if ("Description".equals(label)) {
            return resources.getResource("knowledge.template.label.item.description");
        }
        return label;
    }

}
