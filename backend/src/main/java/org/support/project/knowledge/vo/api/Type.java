package org.support.project.knowledge.vo.api;

/**
 * 記事種類（記事のテンプレート）
 * @author koda
 */
public class Type {
    /** テンプレートの種類ID */
    private Integer id;
    /** テンプレート名 */
    private String name;
    /** アイコン */
    private String icon;
    /** 説明 */
    private String description;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
