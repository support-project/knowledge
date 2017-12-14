package org.support.project.knowledge.control.open;

import java.util.List;
import java.util.UUID;

import org.support.project.aop.Aspect;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.notification.AcceptCheckUserNotification;
import org.support.project.knowledge.logic.notification.AddUserNotification;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.WebConfig;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.ProvisionalRegistrationsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.UserLogic;

@DI(instance = Instance.Prototype)
public class SignupControl extends Control {

    /**
     * ユーザのサインアップ画面を表示
     * 
     * @return
     */
    @Get(publishToken = "knowledge")
    public Boundary view() {
        return forward("signup.jsp");
    }

    /**
     * 新規登録処理を保存
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary save() {
        SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            // ユーザによるデータの追加は認められていない
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
            // ユーザによるデータの追加は認められていない
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        List<ValidateError> errors = validate();
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("signup.jsp");
        }

        if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_USER)) {
            // ユーザが自分で登録
            addUser();
            addMsgInfo("knowledge.signup.success");
            return redirect(getRequest().getContextPath());
        } else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
            // 招待メールで登録
            ProvisionalRegistrationsDao dao = ProvisionalRegistrationsDao.get();
            List<ProvisionalRegistrationsEntity> check = dao.selectOnUserKey(getParam("userKey"));
            if (!check.isEmpty()) {
                long now = DateUtils.now().getTime();
                for (ProvisionalRegistrationsEntity entity : check) {
                    if (now - entity.getInsertDatetime().getTime() > 1000 * 60 * 60) {
                        // 無効なものなので、削除
                        dao.delete(entity);
                    } else {
                        addMsgWarn("knowledge.signup.exists");
                        return forward("signup.jsp");
                    }
                }
            }
            // 仮登録を行う
            ProvisionalRegistrationsEntity entity = addProvisionalRegistration();
            // 招待のメールを送信
            SystemConfigsEntity config = SystemConfigsDao.get().selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
            String url;
            if (config == null) {
                url = HttpUtil.getContextUrl(getRequest());
            } else {
                url = config.getConfigValue();
            }
            MailLogic mailLogic = MailLogic.get();
            mailLogic.sendInvitation(entity, url, HttpUtil.getLocale(getRequest()));
            return forward("mail_sended.jsp");
        } else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE)) {
            // 管理者が承認
            ProvisionalRegistrationsDao dao = ProvisionalRegistrationsDao.get();
            List<ProvisionalRegistrationsEntity> check = dao.selectOnUserKey(getParam("userKey"));
            if (!check.isEmpty()) {
                addMsgWarn("knowledge.signup.waiting");
                return forward("signup.jsp");
            }
            // 仮登録を行う
            ProvisionalRegistrationsEntity entity = addProvisionalRegistration();
            // 管理者へメール通知
            AcceptCheckUserNotification.get().sendNotifyAcceptUser(entity);

            return forward("provisional_registration.jsp");
        }
        return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
    }

    /**
     * 仮登録
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private ProvisionalRegistrationsEntity addProvisionalRegistration() {
        ProvisionalRegistrationsEntity entity = super.getParams(ProvisionalRegistrationsEntity.class);
        String id = UUID.randomUUID().toString() + "-" + DateUtils.getTransferDateFormat().format(DateUtils.now());
        entity.setId(id);
        ProvisionalRegistrationsDao dao = ProvisionalRegistrationsDao.get();
        // 既に仮登録が行われたユーザ(メールアドレス)でも、再度仮登録できる
        // ただし、以前の登録は無効にする
        dao.deleteOnUserKey(entity.getUserKey());
        // データ登録
        entity = dao.insert(entity);
        return entity;
    }

    /**
     * ユーザ追加
     */
    private void addUser() {
        // エラーが無い場合のみ登録
        UsersEntity user = super.getParams(UsersEntity.class);
        String[] roles = { WebConfig.ROLE_USER };
        user = UserLogic.get().insert(user, roles);
        setAttributeOnProperty(user);

        // 管理者にユーザが追加されたことを通知
        AddUserNotification.get().sendNotifyAddUser(user);

        // ログイン処理
        AuthenticationLogic<LoginedUser> logic = Container.getComp(KnowledgeAuthenticationLogic.class);
        logic.setSession(user.getUserKey(), getRequest(), getResponse());
    }

    /**
     * 入力チェック
     * 
     * @return
     */
    private List<ValidateError> validate() {
        List<ValidateError> errors = UsersEntity.get().validate(getParams());
        if (!StringUtils.isEmpty(getParam("password"))) {
            if (!getParam("password").equals(getParam("confirm_password", String.class))) {
                ValidateError error = new ValidateError("knowledge.user.invalid.same.password");
                errors.add(error);
            }
        }
        UsersDao dao = UsersDao.get();
        UsersEntity user = dao.selectOnUserKey(getParam("userKey"));
        if (user != null) {
            ValidateError error = new ValidateError("knowledge.user.mail.exist");
            errors.add(error);
        }

        Validator validator = ValidatorFactory.getInstance(Validator.MAIL);
        ValidateError error = validator.validate(getParam("userKey"), "Email Address");
        if (error != null) {
            errors.add(error);
        }
        return errors;
    }

    /**
     * 招待メールからの本登録
     * 
     * @return
     */
    @Get
    public Boundary activate() {
        String id = getPathInfo();
        if (StringUtils.isEmpty(id)) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        if (id.startsWith("/")) {
            id = id.substring(1);
        }

        ProvisionalRegistrationsDao dao = ProvisionalRegistrationsDao.get();
        ProvisionalRegistrationsEntity entity = dao.selectOnKey(id);
        if (entity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        long now = DateUtils.now().getTime();
        if (now - entity.getInsertDatetime().getTime() > 1000 * 60 * 60) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        // 仮登録から本登録へ
        UsersEntity user = UserLogic.get().activate(entity);
        // 管理者へユーザが追加されたことを通知
        if (user != null) {
            // 管理者にユーザが追加されたことを通知
            AddUserNotification.get().sendNotifyAddUser(user);
        }
        // ログイン処理
        AuthenticationLogic<LoginedUser> logic = Container.getComp(KnowledgeAuthenticationLogic.class);
        logic.setSession(entity.getUserKey(), getRequest(), getResponse());

        addMsgInfo("knowledge.signup.done");
        return forward("signup_done.jsp");
    }

}
