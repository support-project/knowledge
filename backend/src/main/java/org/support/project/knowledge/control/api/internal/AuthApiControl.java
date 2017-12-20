package org.support.project.knowledge.control.api.internal;

import java.sql.Timestamp;
import java.util.Date;

import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.knowledge.vo.api.Auth;
import org.support.project.knowledge.vo.api.AuthResult;
import org.support.project.knowledge.vo.api.User;
import org.support.project.web.bean.LoginedUser;
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
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.invoke.Open;

public class AuthApiControl extends ApiControl {
    
    @Open
    @Post(path="_api/v1/token", subscribeToken="")
    public JsonBoundary token() throws InterruptedException, InvalidParamException {
        Auth auth = parseJson(Auth.class);
        AuthenticationLogic<LoginedUser> authenticationLogic = Container.getComp(KnowledgeAuthenticationLogic.class);
        int userId = authenticationLogic.auth(auth.getId(), auth.getPassword());
        if (userId >= 0) {
            // Token 登録
            TokensEntity entity = new TokensEntity();
            entity.setToken(RandomUtil.randamGen(128));
            entity.setUserId(userId);
            entity.setTokenType(2);
            entity.setExpires(new Timestamp(new Date().getTime() + (1000 * 60 * 60 * 7))); // 7日間有効なTokenを発行する
            TokensDao.get().insert(entity);
            
            UsersEntity user = UsersDao.get().selectOnKey(userId);
            AuthResult authResult = new AuthResult();
            User u = new User();
            u.setUserId(userId);
            u.setUserName(user.getUserName());
            authResult.setUser(u);
            authResult.setToken(entity.getToken());
            
//            JsonObject json = new JsonObject();
//            json.addProperty("token", entity.getToken());
            
            return send(HttpStatus.SC_200_OK, authResult);
        }
        synchronized (this) {
            Thread.sleep(2000); // 2秒待つ
        }
        return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
    }
    
    
    
}
