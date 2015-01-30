package org.support.project.knowledge.control.open;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.exception.InvalidParamException;

public class TagControl extends Control {
	private static final int LIST_LIMIT = 20;
	
	/**
	 * タグの一覧を表示
	 * （ページきりかえあり）
	 * @return
	 * @throws InvalidParamException 
	 */
	public Boundary list() throws InvalidParamException {
		Integer offset = super.getPathInteger(0);
		int userId = super.getLoginUserId();
		LoginedUser loginedUser = super.getLoginedUser();
		List<GroupsEntity> groups = new ArrayList<GroupsEntity>();
		if (loginedUser != null && loginedUser.getGroups() != null) {
			groups = loginedUser.getGroups();
		}
		
		TagsDao tagsDao = TagsDao.get();
		List<TagsEntity> tags;
		if (super.getLoginedUser() != null && super.getLoginedUser().isAdmin()) {
			tags = tagsDao.selectWithKnowledgeCountAdmin(offset * LIST_LIMIT, LIST_LIMIT);
		} else {
			tags = tagsDao.selectWithKnowledgeCount(userId, groups, offset * LIST_LIMIT, LIST_LIMIT);
		}
		setAttribute("tags", tags);
		
		int previous = offset -1;
		if (previous < 0) {
			previous = 0;
		}
		setAttribute("offset", offset);
		setAttribute("previous", previous);
		setAttribute("next", offset + 1);
		
		return forward("list.jsp");
	}
	
}
