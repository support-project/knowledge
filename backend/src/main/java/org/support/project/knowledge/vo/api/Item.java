package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 記事のテンプレートの項目
 * @author koda
 */
public class Item {
    /** テンプレートの種類ID */
    private Integer typeId;
    /** 項目NO */
    private Integer itemNo;
    /** 項目名 */
    private String itemName;
    /** 項目の種類 */
    private Integer itemType;
    /** 説明 */
    private String description;
    /** 初期値 */
    private String initialValue;
    
    /** 値 */
    private String itemValue;

    
    private List<Choice> choices = new ArrayList<>();

    
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public Integer getItemNo() {
        return itemNo;
    }
    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Integer getItemType() {
        return itemType;
    }
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInitialValue() {
        return initialValue;
    }
    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }
    public List<Choice> getChoices() {
        return choices;
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    public String getItemValue() {
        return itemValue;
    }
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

}
