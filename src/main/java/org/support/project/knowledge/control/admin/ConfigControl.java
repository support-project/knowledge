package org.support.project.knowledge.control.admin;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AnalyticsConfig;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.SystemConfigLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.SystemAttributesDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.SystemAttributesEntity;
import org.support.project.web.entity.SystemConfigsEntity;

@DI(instance = Instance.Prototype)
public class ConfigControl extends Control {

    /**
     * ユーザ登録方法設定画面を表示
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary config() {
        SystemConfigsDao dao = SystemConfigsDao.get();

        SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        setAttribute("userAddType", userAddType.getConfigValue());

        SystemConfigsEntity userAddNotify = dao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
        if (userAddNotify == null) {
            userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
            userAddNotify.setConfigValue(SystemConfig.USER_ADD_NOTIFY_OFF);
        }
        setAttribute("userAddNotify", userAddNotify.getConfigValue());

        LdapConfigsDao ldapConfigsDao = LdapConfigsDao.get();
        LdapConfigsEntity ldapConfigsEntity = ldapConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (ldapConfigsEntity == null || ldapConfigsEntity.getAuthType() == null) {
            setAttribute("authType", 0);
        } else {
            setAttribute("authType", ldapConfigsEntity.getAuthType().intValue());
        }

        return forward("config.jsp");
    }

    /**
     * ユーザ登録方法設定を保存
     * 
     * @return
     */
    @Post
    @Auth(roles = "admin")
    public Boundary save() {
        List<ValidateError> errors = new ArrayList<>();
        String type = getParam("userAddType");
        String notify = getParam("userAddNotify");
        // メール送信の場合、メールの設定が完了しているかチェック
        if ((type != null && type.equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL))
                || (notify != null && notify.equals(SystemConfig.USER_ADD_NOTIFY_ON))) {
            MailConfigsDao mailConfigsDao = MailConfigsDao.get();
            MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
            if (mailConfigsEntity == null) {
                ValidateError error = new ValidateError("knowledge.config.mail.require");
                errors.add(error);
            }
        }
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("config.jsp");
        }

        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        userAddType.setConfigValue(type);
        dao.save(userAddType);

        SystemConfigsEntity userAddNotify = new SystemConfigsEntity(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
        userAddNotify.setConfigValue(notify);
        dao.save(userAddNotify);

        String successMsg = "message.success.save";
        setResult(successMsg, errors);

        return forward("config.jsp");
    }

    /**
     * システム設定画面を表示
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary system() {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        if (config == null) {
            String url = HttpUtil.getContextUrl(getRequest());
            config = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
            config.setConfigValue(url);
            dao.save(config);
        }
        setAttribute("systemurl", config.getConfigValue());

        config = dao.selectOnKey(SystemConfig.SYSTEM_EXPOSE_TYPE, AppConfig.get().getSystemName());
        if (config != null) {
            setAttribute("system_open_type", config.getConfigValue());
        }

        return forward("system.jsp");
    }

    /**
     * ユーザ登録方法設定を保存
     * 
     * @return
     */
    @Post
    @Auth(roles = "admin")
    public Boundary save_params() {
        List<ValidateError> errors = new ArrayList<>();
        String systemurl = getParam("systemurl");
        // メール送信の場合、メールの設定が完了しているかチェック
        if (StringUtils.isEmpty(systemurl)) {
            ValidateError error = new ValidateError("errors.required", getResource("knowledge.config.system.label.url"));
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("system.jsp");
        }

        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = new SystemConfigsEntity(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        config.setConfigValue(systemurl);
        dao.save(config);

        String systemOpenType = getParam("system_open_type");
        if (StringUtils.isNotEmpty(systemOpenType)) {
            config = new SystemConfigsEntity(SystemConfig.SYSTEM_EXPOSE_TYPE, AppConfig.get().getSystemName());
            config.setConfigValue(systemOpenType);
            dao.save(config);

            if (SystemConfig.SYSTEM_EXPOSE_TYPE_CLOSE.equals(systemOpenType)) {
                SystemConfigLogic.get().setClose(true);
            } else {
                SystemConfigLogic.get().setClose(false);
            }
        }

        String successMsg = "message.success.save";
        setResult(successMsg, errors);

        return forward("system.jsp");
    }

    /**
     * Analytics設定画面を表示
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary analytics() {
        SystemAttributesDao dao = SystemAttributesDao.get();
        SystemAttributesEntity config = dao.selectOnKey(SystemConfig.ANALYTICS, AppConfig.get().getSystemName());
        if (config != null) {
            setAttribute("analytics_script", config.getConfigValue());
            // 設定を毎回DBから取得するのはパフォーマンス面で良くないので、メモリに保持する
            AnalyticsConfig.get().setAnalyticsScript(config.getConfigValue());
        }
        return forward("analytics.jsp");
    }

    /**
     * Analytics設定を保存
     * 
     * @return
     */
    @Post
    @Auth(roles = "admin")
    public Boundary analytics_save() {
        String analyticsScript = getParam("analytics_script");
        SystemAttributesDao dao = SystemAttributesDao.get();
        SystemAttributesEntity config = dao.selectOnKey(SystemConfig.ANALYTICS, AppConfig.get().getSystemName());
        if (config == null) {
            config = new SystemAttributesEntity(SystemConfig.ANALYTICS, AppConfig.get().getSystemName());
        }
        config.setConfigValue(analyticsScript);
        dao.save(config);

        // 設定を毎回DBから取得するのはパフォーマンス面で良くないので、メモリに保持する
        AnalyticsConfig.get().setAnalyticsScript(analyticsScript);

        addMsgSuccess("message.success.save");

        return analytics();
    }

}
