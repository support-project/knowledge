package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.dao.GroupsDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;

public class SearchKnowledgeParam {

    private String keyword;
    private List<TagsEntity> tags;
    private List<GroupsEntity> groups;
    private List<UsersEntity> creators;
    private String[] templates;
    private AccessUser loginedUser;
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
    public void setGroupsAndLoginUser(String groupNames, AccessUser loginedUser) {
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
     * @param template the template to set
     */
    public void setTemplates(String template) {
        if (template != null) {
            this.templates = template.split(",");
        }
    }
    public void setCreators(String creators) {
        List <UsersEntity> creatorUserEntities = new ArrayList<>();
        if (StringUtils.isNotEmpty(creators)) {
            String[] creatorsArray = creators.split(",");
            for (String userName : creatorsArray) {
                List<UsersEntity> users = ExUsersDao.get().selectByUserName(userName);
                creatorUserEntities.addAll(users);
            }
        }
        this.creators = creatorUserEntities;
    }

    
    /**
     * @return the templates
     */
    public String[] getTemplates() {
        return templates;
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
     * @return the loginedUser
     */
    public AccessUser getLoginedUser() {
        return loginedUser;
    }
    /**
     * @param loginedUser the loginedUser to set
     */
    public void setLoginedUser(AccessUser loginedUser) {
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
    public List<UsersEntity> getCreators() {
        return creators;
    }
    public void setCreators(List<UsersEntity> creators) {
        this.creators = creators;
    }
    
}
