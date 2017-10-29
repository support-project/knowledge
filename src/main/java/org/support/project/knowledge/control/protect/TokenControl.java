package org.support.project.knowledge.control.protect;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.TokensDao;
import org.support.project.knowledge.entity.TokensEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;

@DI(instance = Instance.Prototype)
public class TokenControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(TokenControl.class);

    private DateFormat gateDayFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
    
    /**
     * 現在のTokenの状態を見る
     */
    @Get(publishToken = "csrf")
    public Boundary index() {
        LOG.trace("access to index");
        String expires = "";
        TokensEntity entity = TokensDao.get().selectOnUserId(getLoginUserId());
        if (entity == null) {
            entity = new TokensEntity();
            entity.setToken("");
        } else {
            expires = gateDayFormat().format(entity.getExpires());
        }
        setAttribute("token", entity.getToken());
        setAttribute("expires", expires);
        return forward("index.jsp");
    }
    
    
    /**
     * Tokenの発行／更新
     */
    @Post(subscribeToken = "csrf")
    public Boundary save() {
        LOG.trace("access to save");
        String expires = getParam("expires");
        if (StringUtils.isEmpty(expires)) {
            expires = "9999-12-31";
        }
        Date date;
        try {
            date = gateDayFormat().parse(expires);
        } catch (ParseException e) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST, "BAD_REQUEST");
        }
        
        TokensEntity entity = TokensDao.get().selectOnUserId(getLoginUserId());
        if (entity == null) {
            entity = new TokensEntity();
            entity.setToken(RandomUtil.randamGen(64));
            entity.setUserId(getLoginUserId());
        }
        entity.setExpires(new Timestamp(date.getTime()));
        TokensDao.get().save(entity);
        addMsgSuccess("message.success.save");
        return index();
    }
    
    /**
     * Tokenの削除
     */
    @Post(subscribeToken = "csrf")
    public Boundary delete() {
        TokensEntity entity = TokensDao.get().selectOnUserId(getLoginUserId());
        if (entity != null) {
            TokensDao.get().physicalDelete(entity);
        }
        addMsgSuccess("message.success.delete");
        return index();
    }
}
