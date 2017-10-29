package org.support.project.knowledge.control.open;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.AccountImagesDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.AccountImagesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.AccountLogic;
import org.support.project.knowledge.logic.IdenticonLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.knowledge.vo.AccountInfo;
import org.support.project.knowledge.vo.ActivityHistory;
import org.support.project.knowledge.vo.ContributionPointHistory;
import org.support.project.knowledge.vo.StockKnowledge;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class AccountControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AccountControl.class);
    
    public static final int PAGE_LIMIT = 50;
    /**
     * ユーザのアイコン画像を取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get
    public Boundary icon() throws InvalidParamException {
        long size = 12140;
        String fileName = "icon.png";
        String contentType = "image/png";
        InputStream inputStream;
        
        Integer userId = getPathInteger(-1);
        if (userId.intValue() == -1) {
            inputStream = getClass().getResourceAsStream("/icon/icon.png");
        } else {
            AccountImagesDao dao = AccountImagesDao.get();
            AccountImagesEntity entity = dao.selectOnUserId(userId);

            if (entity != null) {
                fileName = entity.getFileName();
                contentType = entity.getContentType();
                size = entity.getFileSize().longValue();
                inputStream = entity.getFileBinary();
            } else {
                try {
                    byte[] bytes = IdenticonLogic.get().generate(String.valueOf(userId));
                    size = bytes.length;
                    inputStream = new ByteArrayInputStream(bytes);
                } catch (NoSuchAlgorithmException | IOException e) {
                    LOG.warn("generate icon error.", e);
                    inputStream = getClass().getResourceAsStream("/icon/icon.png");
                }
            }
        }
        return download(fileName, inputStream, size, contentType);
    }

    @Get
    public Boundary info() throws Exception {
        // 指定のユーザの情報を取得
        Integer userId = getPathInteger(-1);
        ExUsersDao usersDao = ExUsersDao.get();
        AccountInfo account = usersDao.selectAccountInfoOnKey(userId);
        if (account == null) {
            return sendError(HttpStatus.SC_NOT_FOUND, "NOT FOUND");
        }

        setAttributeOnProperty(account);

        // そのユーザが登録したナレッジを取得
        int offset = 0;
        if (StringUtils.isInteger(getParam("offset"))) {
            offset = getParam("offset", Integer.class);
        }
        List<KnowledgesEntity> knowledges = KnowledgeLogic.get().showKnowledgeOnUser(userId, getLoginedUser(), offset * PAGE_LIMIT, PAGE_LIMIT);
        List<StockKnowledge> stocks = KnowledgeLogic.get().setStockInfo(knowledges, getLoginedUser());
        KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
        setAttribute("knowledges", stocks);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        List<TemplateMastersEntity> templateList = TemplateMastersDao.get().selectAll();
        Map<Integer, TemplateMastersEntity> templates = new HashMap<>();
        for (TemplateMastersEntity templateMastersEntity : templateList) {
            templates.put(templateMastersEntity.getTypeId(), templateMastersEntity);
        }
        setAttribute("templates", templates);
        
        long point = AccountLogic.get().getPoint(userId);
        setAttribute("point", point);
        
        
        return forward("account.jsp");
    }
    
    @Get
    public Boundary cp() throws Exception {
        Integer userId = getPathInteger(-1);
        UsersEntity account = UsersDao.get().selectOnKey(userId);
        if (account == null) {
            return send(HttpStatus.SC_NOT_FOUND, "NOT FOUND");
        }
        List<ContributionPointHistory> list = ActivityLogic.get().getUserPointHistoriesByDate(userId, getUserConfigs());
        return send(list);
    }
    
    @Get
    public Boundary knowledge() throws Exception {
        Integer userId = getPathInteger(-1);
        UsersEntity account = UsersDao.get().selectOnKey(userId);
        if (account == null) {
            return send(HttpStatus.SC_NOT_FOUND, "NOT FOUND");
        }
        // そのユーザが登録したナレッジを取得
        int offset = 0;
        if (StringUtils.isInteger(getParam("offset"))) {
            offset = getParam("offset", Integer.class);
        }
        List<KnowledgesEntity> knowledges = KnowledgeLogic.get().showKnowledgeOnUser(userId, getLoginedUser(), offset * PAGE_LIMIT, PAGE_LIMIT);
        List<StockKnowledge> stocks = KnowledgeLogic.get().setStockInfo(knowledges, getLoginedUser());
        KnowledgeLogic.get().setViewed(stocks, getLoginedUser());
        return send(stocks);
    }
    
    @Get
    public Boundary activity() throws Exception {
        Integer userId = getPathInteger(-1);
        UsersEntity account = UsersDao.get().selectOnKey(userId);
        if (account == null) {
            return send(HttpStatus.SC_NOT_FOUND, "NOT FOUND");
        }
        int limit = 20;
        int offset = 0;
        if (StringUtils.isInteger(getParam("offset"))) {
            offset = getParam("offset", Integer.class);
        }
        List<ActivityHistory> list = ActivityLogic.get().getUserPointHistoriese(userId, limit, offset, getUserConfigs());
        setSendEscapeHtml(false);
        return send(list);
    }
    

}
