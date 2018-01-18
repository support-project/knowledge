package org.support.project.knowledge.control.protect;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class TargetControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static final int PAGE_LIMIT = 10;

    /**
     * グループ選択のための候補をJSONで取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary typeahead() throws InvalidParamException {
        LOG.trace("call typeahead");
        String keyword = super.getParam("keyword");
        int offset = 0;
        int limit = 10;
        String off = super.getParam("offset");
        if (StringUtils.isInteger(off)) {
            offset = Integer.parseInt(off);
        }
        String filter = getParam("filter");
        if (!StringUtils.isEmpty(filter) && "user".equals(filter)) {
            TargetLogic groupLogic = TargetLogic.get();
            List<LabelValue> aHeads = groupLogic.selectUsersOnKeyword(keyword, super.getLoginedUser(), offset * limit, limit);
            return send(aHeads);
        } else {
            TargetLogic groupLogic = TargetLogic.get();
            List<LabelValue> aHeads = groupLogic.selectOnKeyword(keyword, super.getLoginedUser(), offset * limit, limit);
            return send(aHeads);
        }
    }

}
