package org.support.project.web.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.annotation.Auth;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.bean.SendList;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.control.service.Put;
import org.support.project.web.entity.NoticesEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.NoticesLogic;

/**
 * Control for notices from admin.
 * @author Koda
 */
@DI(instance = Instance.Prototype)
public class NoticesControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(NoticesControl.class);
    /** 既読情報をSessionに格納するキー */
    public static final String READ_NOTICES = "READ_NOTICES";
    
    @Get(path = "admin.api/notices", publishToken = "csrf")
    @Auth(roles = "admin")
    public Boundary getNotices() {
        LOG.trace("getNotices");
        String pathinfo = super.getPathInfo();
        if (StringUtils.isEmpty(pathinfo)) {
            Integer offset = super.getParam("offset", Integer.class);
            Integer limit = super.getParam("limit", Integer.class);
            if (offset == null) {
                offset = 0;
            }
            if (limit == null) {
                limit = 20;
            }
            SendList sendlist = NoticesLogic.get().selectAllNotices(limit, offset);
            return send(sendlist);
        } else {
            if (!StringUtils.isInteger(pathinfo)) {
                return send(HttpStatus.SC_400_BAD_REQUEST);
            }
            Integer no = Integer.parseInt(pathinfo);
            NoticesEntity entity = NoticesLogic.get().selectNotice(no);
            return send(entity);
        }
    }
    
    @Post(path = "admin.api/notices", subscribeToken = "csrf")
    @Auth(roles = "admin")
    public Boundary postNotice() throws InvalidParamException {
        LOG.trace("postNotice");
        NoticesEntity entity = HttpUtil.parseJson(getRequest(), NoticesEntity.class);
        entity = NoticesLogic.get().insertNotice(entity);
        MessageResult result = new MessageResult(
                MessageStatus.Success, HttpStatus.SC_200_OK, getResource("message.success.insert"), entity.getNo().toString());
        return send(result);
    }
    
    @Put(path = "admin.api/notices", subscribeToken = "csrf")
    @Auth(roles = "admin")
    public Boundary putNotice() throws InvalidParamException {
        LOG.trace("putNotice");
        Integer no = super.getPathInteger();
        NoticesEntity entity = HttpUtil.parseJson(getRequest(), NoticesEntity.class);
        entity.setNo(no);
        entity = NoticesLogic.get().updateNotice(entity);
        if (entity == null) {
            return send(HttpStatus.SC_404_NOT_FOUND);
        }
        MessageResult result = new MessageResult(
                MessageStatus.Success, HttpStatus.SC_200_OK, getResource("message.success.update"), entity.getNo().toString());
        return send(result);
    }
    
    @Delete(path = "admin.api/notices", subscribeToken = "csrf")
    @Auth(roles = "admin")
    public Boundary deleteNotice() throws InvalidParamException {
        LOG.trace("deleteNotice");
        Integer no = super.getPathInteger();
        NoticesEntity entity = NoticesLogic.get().deleteNotice(no);
        if (entity == null) {
            return send(HttpStatus.SC_404_NOT_FOUND);
        }
        MessageResult result = new MessageResult(
                MessageStatus.Success, HttpStatus.SC_200_OK, getResource("message.success.delete"), entity.getNo().toString());
        return send(result);
    }
    
    
    @Get(path = "open.api/mynotices")
    public Boundary getMyNotices() {
        LOG.trace("getMyNotices");
        String all = getParam("all");
        List<NoticesEntity> sendlist;
        if ("true".equals(all)) {
            sendlist = NoticesLogic.get().selectMyNotices(null);
        } else {
            sendlist = NoticesLogic.get().selectMyNotices(getLoginedUser());
        }
        HttpSession session = getRequest().getSession();
        session.setAttribute(READ_NOTICES, Boolean.TRUE);
        
        return send(sendlist);
    }
    
    @Put(path = "open.api/readmark")
    public Boundary readMark() throws InvalidParamException {
        LOG.trace("readMark");
        LoginedUser loginedUser = getLoginedUser();
        if (loginedUser == null) {
            return send(HttpStatus.SC_403_FORBIDDEN);
        }
        Integer no = super.getPathInteger();
        Integer showNextTime = super.getParam("showNextTime", Integer.class);
        NoticesLogic.get().readMark(loginedUser.getUserId(), no, showNextTime);
        return send(HttpStatus.SC_200_OK);
    }

    
    
}
