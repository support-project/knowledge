package org.support.project.knowledge.control.api.internal.articles;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public abstract class AbstractArticleApi extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    /**
     * パスに設定されている記事のIDを取得
     * @return
     * @throws InvalidParamException
     */
    protected Long getRouteArticleId() throws InvalidParamException {
        String id = super.getRouteParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            throw new InvalidParamException(new MessageResult(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST"));
        }
        long knowledgeId = Long.parseLong(id);
        if (KnowledgeLogic.get().select(knowledgeId, getLoginedUser()) == null) {
            // 存在しない or アクセス権無し
            throw new InvalidParamException(new MessageResult(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND"));
        }
        return knowledgeId;
    }
}
