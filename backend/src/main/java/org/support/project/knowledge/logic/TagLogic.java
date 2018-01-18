package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.entity.GroupsEntity;

@DI(instance = Instance.Singleton)
public class TagLogic {

    public static TagLogic get() {
        return Container.getComp(TagLogic.class);
    }

    /**
     * タグの一覧を取得 同時に、ナレッジの件数も取得する 件数は、ログインユーザがアクセス権があるもの
     * 
     * @param loginedUser
     * @param i
     * @param pageLimit
     * @return
     */
    public List<TagsEntity> selectTagsWithCount(AccessUser loginedUser, int offset, int limit) {
        int userid = Integer.MIN_VALUE;
        if (loginedUser != null) {
            userid = loginedUser.getUserId();
        }
        List<GroupsEntity> groups = new ArrayList<GroupsEntity>();
        if (loginedUser != null && loginedUser.getGroups() != null) {
            groups = loginedUser.getGroups();
        }

        TagsDao tagsDao = TagsDao.get();
        List<TagsEntity> tags;
        if (loginedUser != null && loginedUser.isAdmin()) {
            tags = tagsDao.selectWithKnowledgeCountAdmin(offset, limit);
        } else {
            tags = tagsDao.selectWithKnowledgeCount(userid, groups, offset, limit);
        }
        return tags;
    }

}
