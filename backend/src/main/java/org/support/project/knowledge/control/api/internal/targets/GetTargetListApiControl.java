package org.support.project.knowledge.control.api.internal.targets;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.vo.api.Target;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetTargetListApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事の公開範囲や、編集者に指定するターゲット（ユーザやグループ）のリストを取得
     * @throws Exception 
     */
    @Get(path="_api/targets")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("call _api/targets");
        String keyword = super.getParam("keyword");
        ApiParams apiParams = super.getCommonApiParams();
        int limit = apiParams.getLimit();
        int offset = apiParams.getOffset();
        TargetLogic groupLogic = TargetLogic.get();
        List<LabelValue> aHeads = groupLogic.selectOnKeyword(keyword, super.getLoginedUser(), offset, limit);
        // LabelValueはやめる（既存の処理に影響を与えないように、ここで変換)
        List<Target> targets = TargetLogic.get().convLabelValuesToTargets(aHeads);
        return send(targets);
    }
}
