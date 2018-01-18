package org.support.project.knowledge.control.api.internal.types;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.api.Type;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetTypeListApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事の種類（テンプレート）の一覧を取得
     * 記事の種類が多くの件数になることは無いと思われるため、件数制御／オフセット制御はいったんしない
     * @throws Exception 
     */
    @Get(path="_api/types")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("call _api/types");
        List<TemplateMastersEntity> results = TemplateLogic.get().selectAll();
        List<Type> types = new ArrayList<>();
        // 種類にある、項目の情報も取得
        for (TemplateMastersEntity template : results) {
            template = TemplateLogic.get().loadTemplate(template.getTypeId());
            Type type = KnowledgeDataSelectLogic.get().convType(template);
            types.add(type);
        }
        return send(HttpStatus.SC_200_OK, types);
    }
}
