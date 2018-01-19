package org.support.project.knowledge.control.api.internal.auth;

import java.sql.Timestamp;
import java.util.Date;

import org.support.project.common.util.DateUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.vo.api.AuthResult;
import org.support.project.knowledge.vo.api.User;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.TokensDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.TokensEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class CheckTokenApiControl extends ApiControl {
    
    /**
     * Tokenが有効かチェックする
     * @return
     * @throws InterruptedException
     * @throws InvalidParamException
     */
    @Open
    @Post(path="_api/token", checkCookieToken=false)
    public JsonBoundary token() throws InterruptedException, InvalidParamException {
        Msg request = parseJson(Msg.class);
        TokensEntity token = TokensDao.get().selectOnKey(request.getMsg());
        if (token == null) {
            return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
        }
        if (token.getExpires().getTime() < DateUtils.now().getTime()) {
            return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
        }
        
        // 有効期間を延長
        token.setExpires(new Timestamp(new Date().getTime() + (1000 * 60 * 60 * 7))); // 7日間有効なTokenを発行する
        TokensDao.get().update(token);
        
        Integer userId = token.getUserId();
        UsersEntity user = UsersDao.get().selectOnKey(userId);
        AuthResult authResult = new AuthResult();
        User u = new User();
        u.setUserId(userId);
        u.setUserName(user.getUserName());
        authResult.setUser(u);
        authResult.setToken(token.getToken());
        authResult.setExpires(token.getExpires());
        
        return send(HttpStatus.SC_200_OK, authResult);
    }
}
