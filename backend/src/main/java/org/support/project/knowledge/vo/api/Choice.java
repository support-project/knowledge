package org.support.project.knowledge.vo.api;

public class Choice {
    /** テンプレートの種類ID */
    private Integer typeId;
    /** 項目NO */
    private Integer itemNo;
    /** 選択肢番号 */
    private Integer choiceNo;
    /** 選択肢値 */
    private String choiceValue;
    /** 選択肢ラベル */
    private String choiceLabel;
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
    public Integer getChoiceNo() {
        return choiceNo;
    }
    public void setChoiceNo(Integer choiceNo) {
        this.choiceNo = choiceNo;
    }
    public String getChoiceValue() {
        return choiceValue;
    }
    public void setChoiceValue(String choiceValue) {
        this.choiceValue = choiceValue;
    }
    public String getChoiceLabel() {
        return choiceLabel;
    }
    public void setChoiceLabel(String choiceLabel) {
        this.choiceLabel = choiceLabel;
    }

}
