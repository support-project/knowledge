package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.logic.KeywordLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.entity.GroupsEntity;

public class SearchKnowledgeParam {

    private String keyword;
    private List<TagsEntity> tags;
    private List<GroupsEntity> groups;
    private String template;
    private LoginedUser loginedUser;
    private int offset;
    private int limit;
    
    public void setTags(String tagNames) {
        String[] taglist = tagNames.split(",");
        List<TagsEntity> tags = new ArrayList<TagsEntity>();
        for (String string : taglist) {
            String tagname = string.trim();
            if (tagname.startsWith(" ") && tagname.length() > " ".length()) {
                tagname = tagname.substring(" ".length());
            }
            TagsEntity tagsEntity = TagsDao.get().selectOnTagName(tagname);
            if (tagsEntity != null) {
                tags.add(tagsEntity);
            }
        }
        setTags(tags);
    }
    public void setGroupsAndLoginUser(String groupNames, LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        List<GroupsEntity> groups = new ArrayList<GroupsEntity>();
        if (loginedUser != null) {
            String[] grouplist = groupNames.split(",");
            for (String string : grouplist) {
                String groupname = string.trim();
                if (groupname.startsWith(" ") && groupname.length() > " ".length()) {
                    groupname = groupname.substring(" ".length());
                }
                GroupsEntity groupsEntity = GroupsDao.get().selectOnGroupName(groupname);
                if (groupsEntity != null) {
                    groups.add(groupsEntity);
                }
            }
        }
        setGroups(groups);
    }
    
    
    
    
    
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }
    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    /**
     * @return the tags
     */
    public List<TagsEntity> getTags() {
        return tags;
    }
    /**
     * @param tags the tags to set
     */
    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }
    /**
     * @return the groups
     */
    public List<GroupsEntity> getGroups() {
        return groups;
    }
    /**
     * @param groups the groups to set
     */
    public void setGroups(List<GroupsEntity> groups) {
        this.groups = groups;
    }
    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }
    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }
    /**
     * @return the loginedUser
     */
    public LoginedUser getLoginedUser() {
        return loginedUser;
    }
    /**
     * @param loginedUser the loginedUser to set
     */
    public void setLoginedUser(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
    }
    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }
    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }
    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}
