package org.support.project.knowledge.control.api.internal.me;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Put;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Prototype)
public class PutMyLanguageApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 表示言語の切り替え
     * @throws Exception
     */
    @Put(path = "_api/me/lang/:lang")
    public Boundary execute() {
        LOG.trace("call put _api/me/lang/:lang");
        UsersEntity user = UsersDao.get().selectOnKey(getLoginUserId());
        if (user == null) {
            return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
        }
        String lang = super.getRouteParam("lang");
        user.setLocaleKey(lang);
        UsersDao.get().update(user);
        return send(HttpStatus.SC_200_OK, _OK);
    }
}
