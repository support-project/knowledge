package org.support.project.knowledge.control.open;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.logic.TagLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class TagControl extends Control {
    private static final int LIST_LIMIT = 20;

    /**
     * タグの一覧を表示 （ページきりかえあり）
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary list() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);
        List<TagsEntity> tags = TagLogic.get().selectTagsWithCount(super.getLoginedUser(), offset * LIST_LIMIT, LIST_LIMIT);
        setAttribute("tags", tags);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        return forward("list.jsp");
    }

    /**
     * タグの選択画面で表示するデータをJSON形式で取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary json() throws InvalidParamException {
        int limit = 10;
        String keyword = super.getParam("keyword");
        Integer offset = super.getPathInteger(0);
        // タグに紐付いているナレッジの件数でデータをソートして取得(ナレッジへのアクセス件は考慮しない)
        List<TagsEntity> tags = TagsDao.get().selectWithKnowledgeCountOnTagName(keyword, offset * limit, limit);
        return send(tags);
    }

}
