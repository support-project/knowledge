package org.support.project.knowledge.control;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;

@DI(instance = Instance.Prototype)
public class KnowledgeControlBase extends Control {

    protected String setViewParam() {
        StringBuilder params = new StringBuilder();
        params.append("?keyword=").append(getParamWithDefault("keyword", ""));
        params.append("&tag=").append(getParamWithDefault("tag", ""));
        params.append("&tagNames=").append(getParamWithDefault("tagNames", ""));
        params.append("&user=").append(getParamWithDefault("user", ""));
        params.append("&offset=").append(getParamWithDefault("offset", ""));

        if (super.getLoginedUser() != null) {
            params.append("&group=").append(getParamWithDefault("group", ""));
            params.append("&groupNames=").append(getParamWithDefault("groupNames", ""));
        }

        setAttribute("params", params.toString());
        return params.toString();
    }
    
    protected void setAttributeForEditPage() {
        List<TagsEntity> tagitems = TagsDao.get().selectAll();
        setAttribute("tagitems", tagitems);

        List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
        setAttribute("templates", templates);
    }
    
}
