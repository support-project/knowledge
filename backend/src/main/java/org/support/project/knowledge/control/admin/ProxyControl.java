package org.support.project.knowledge.control.admin;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.AuthType;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.CrawlerLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

@DI(instance = Instance.Prototype)
public class ProxyControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private static final String NO_CHANGE_PASSWORD = "NO_CHANGE_PASSWORD-fXLSJ_V-ZJ2E-GHUuagFASR-gtaG"; // パスワードを更新しなかったことを表すパスワード

    /**
     * 設定画面を表示
     * 
     * @return
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary config() {
        ProxyConfigsDao dao = ProxyConfigsDao.get();
        ProxyConfigsEntity entity = dao.selectOnKey(AppConfig.get().getSystemName());
        if (entity != null) {
            entity.setProxyAuthPassword(NO_CHANGE_PASSWORD);
            setAttributeOnProperty(entity);
        }
        setAttribute("systemName", AppConfig.get().getSystemName());
        return forward("config.jsp");
    }

    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary save() throws Exception {
        List<ValidateError> errors = new ArrayList<>();
        errors.addAll(ProxyConfigsEntity.get().validate(getParams()));

        String type = getParam("proxyAuthType");
        // 認証がONの場合のチェック
        if (!type.equals(String.valueOf(AuthType.None.getValue()))) {
            if (StringUtils.isEmpty(getParam("proxyAuthUserId"))) {
                ValidateError error = new ValidateError("errors.required", getResource("label.auth.id"));
                errors.add(error);
            }
            if (StringUtils.isEmpty(getParam("proxyAuthPassword"))) {
                ValidateError error = new ValidateError("errors.required", getResource("label.auth.password"));
                errors.add(error);
            }
        }
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("config.jsp");
        }

        ProxyConfigsEntity entity = super.getParamOnProperty(ProxyConfigsEntity.class);
        ProxyConfigsDao dao = ProxyConfigsDao.get();

        if (entity.getProxyAuthPassword().equals(NO_CHANGE_PASSWORD)) {
            // パスワード変更無し
            ProxyConfigsEntity db = dao.selectOnKey(AppConfig.get().getSystemName());
            entity.setProxyAuthPassword(db.getProxyAuthPassword());
            entity.setProxyAuthSalt(db.getProxyAuthSalt());
        } else {
            // パスワードは暗号化する
            String salt = PasswordUtil.getSalt();
            entity.setProxyAuthPassword(PasswordUtil.encrypt(entity.getProxyAuthPassword(), salt));
            entity.setProxyAuthSalt(salt);
        }

        entity = dao.save(entity);

        String successMsg = "message.success.save";
        setResult(successMsg, errors);
        return config();
    }

    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary test() throws Exception {
        try {
            ProxyConfigsEntity entity;
            String testUrl = getParam("testUrl");
            Integer testType = getParam("testType", Integer.class);
            if (INT_FLAG.OFF.getValue() == testType.intValue()) {
                entity = new ProxyConfigsEntity();
            } else {
                List<ValidateError> errors = new ArrayList<>();
                errors.addAll(ProxyConfigsEntity.get().validate(getParams()));

                String type = getParam("proxyAuthType");
                // 認証がONの場合のチェック
                if (!type.equals(String.valueOf(AuthType.None.getValue()))) {
                    if (StringUtils.isEmpty(getParam("proxyAuthUserId"))) {
                        ValidateError error = new ValidateError("errors.required", getResource("label.auth.id"));
                        errors.add(error);
                    }
                    if (StringUtils.isEmpty(getParam("proxyAuthPassword"))) {
                        ValidateError error = new ValidateError("errors.required", getResource("label.auth.password"));
                        errors.add(error);
                    }
                }
                if (!errors.isEmpty()) {
                    setResult(null, errors);
                    return forward("config.jsp");
                }

                entity = super.getParamOnProperty(ProxyConfigsEntity.class);
                ProxyConfigsDao dao = ProxyConfigsDao.get();

                if (entity.getProxyAuthPassword().equals(NO_CHANGE_PASSWORD)) {
                    // パスワード変更無し
                    ProxyConfigsEntity db = dao.selectOnKey(AppConfig.get().getSystemName());
                    entity.setProxyAuthPassword(db.getProxyAuthPassword());
                    entity.setProxyAuthSalt(db.getProxyAuthSalt());
                } else {
                    // パスワードは暗号化する
                    String salt = PasswordUtil.getSalt();
                    entity.setProxyAuthPassword(PasswordUtil.encrypt(entity.getProxyAuthPassword(), salt));
                    entity.setProxyAuthSalt(salt);
                }
            }

            // 確認用のURLで通信出来るか確認
            CrawlerLogic crawlerLogic = CrawlerLogic.get();
            String content = crawlerLogic.crawle(entity, testUrl);
            setAttribute("content", content);

            addMsgInfo("knowledge.proxy.test.done");

        } catch (Exception e) {
            LOG.warn("knowledge.proxy.test.fail", e);
            addMsgError("knowledge.proxy.test.fail");
            addMsgError(e.getClass().getSimpleName());
            if (StringUtils.isNotEmpty(e.getMessage())) {
                addMsgError(e.getMessage());
            }
        }
        return forward("config.jsp");
    }

    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary delete() throws Exception {
        ProxyConfigsDao dao = ProxyConfigsDao.get();
        dao.physicalDelete(AppConfig.get().getSystemName()); // 物理削除で消してしまう

        ProxyConfigsEntity entity = new ProxyConfigsEntity();
        entity.setSystemName(AppConfig.get().getSystemName());
        setAttributeOnProperty(entity);

        addMsgInfo("message.success.delete.target", getResource("knowledge.proxy.title"));

        return forward("config.jsp");
    }

}
