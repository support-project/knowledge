package org.support.project.knowledge.control.api.internal.auth;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Delete;
import org.support.project.web.dao.TokensDao;
import org.support.project.web.entity.TokensEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.AuthenticationLogic;

@DI(instance = Instance.Prototype)
public class DeleteTokenApiControl extends ApiControl {
    /**
     * Tokenを削除
     * @return
     * @throws InterruptedException
     * @throws InvalidParamException
     */
    @Delete(path="_api/token/:token", checkCookieToken=false)
    public JsonBoundary token() throws InterruptedException, InvalidParamException {
        String t = super.getAttributeByString("token");
        TokensEntity token = TokensDao.get().selectOnKey(t);
        if (token != null) {
            if (!token.getUserId().equals(getAccessUser().getUserId())) {
                return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
            }
            TokensDao.get().physicalDelete(token);
        }
        // ログアウト
        AuthenticationLogic<AccessUser> authenticationLogic = Container.getComp(KnowledgeAuthenticationLogic.class);
        authenticationLogic.clearSession(getRequest());
        getRequest().changeSessionId();
        authenticationLogic.removeCookie(getRequest(), getResponse());
        return send(HttpStatus.SC_200_OK, "Removed");
    }
}
