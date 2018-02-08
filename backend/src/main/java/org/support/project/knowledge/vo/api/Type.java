package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

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
    
    private List<Item> items = new ArrayList<>();
    
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
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    
}
