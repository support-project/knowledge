package org.support.project.knowledge.vo.api;

import org.support.project.web.bean.NameId;

public class Target extends NameId {
    public Target() {
        super();
    }

    public Target(String name, String id) {
        super(name, id);
    }

    private String type = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
