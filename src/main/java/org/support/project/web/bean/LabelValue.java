package org.support.project.web.bean;

public class LabelValue {
    /** 表示する値 */
    private String label;
    /** 実際の値 */
    private String value;
    
    /**
     * コンストラクタ
     */
    public LabelValue() {
        super();
    }
    /**
     * コンストラクタ
     * @param label label
     * @param value value
     */
    public LabelValue(String label, String value) {
        super();
        this.label = label;
        this.value = value;
    }
    
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
