package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.AccountLogic;
import org.support.project.knowledge.logic.KnowledgeAuthenticationLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.UserLogicEx;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UserConfigsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.AuthenticationLogic;

@DI(instance = Instance.Prototype)
public class AccountControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AccountControl.class);
    /** アイコン画像の最大サイズ(5MB) */
    private static final int ICON_IMAGE_MAX_SIZE = 5 * 1024 * 1024;
    
    /**
     * アカウント情報表示
     */
    @Get(publishToken = "account")
    @Override
    public Boundary index() {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        setAttribute("userAddType", userAddType.getConfigValue());

        LoginedUser loginedUser = super.getLoginedUser();
        if (loginedUser == null) {
            return sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
        }
        Integer userId = loginedUser.getLoginUser().getUserId();
        UsersEntity user = UsersDao.get().selectOnKey(userId);
        if (user == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        user.setPassword(null);

        setAttributeOnProperty(user);
        return forward("index.jsp");
    }

    /**
     * ユーザの情報更新
     * 
     * @return
     * @throws ParseException
     * @throws ScanException
     * @throws PolicyException
     */
    @Post(subscribeToken = "account", checkReqToken = true)
    public Boundary update() throws ParseException {
        SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();

        SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        setAttribute("userAddType", userAddType.getConfigValue());

        LoginedUser loginedUser = super.getLoginedUser();
        if (loginedUser == null) {
            return sendError(HttpStatus.SC_401_UNAUTHORIZED, "");
        }

        Map<String, String> values = getParams();
        values.put("userId", String.valueOf(super.getLoginUserId()));
        values.put("rowId", "-"); // row_idは必須だが、画面からはこない
        if (StringUtils.isEmpty(getParam("password"))) {
            values.put("password", "-");
        }
        values.put("userName", super.sanitize(values.get("userName"))); // ユーザ名はXSS対策する

        UsersEntity user = new UsersEntity();
        List<ValidateError> errors = user.validate(values);
        if (!StringUtils.isEmpty(getParam("password"))) {
            if (!getParam("password").equals(getParam("confirm_password", String.class))) {
                ValidateError error = new ValidateError("knowledge.user.invalid.same.password");
                errors.add(error);
            }
        }

        if (errors.isEmpty()) {
            // エラーが無い場合のみ更新する
            // UsersEntity user = super.getParams(UsersEntity.class);
            UsersDao dao = UsersDao.get();
            user = dao.selectOnKey(getLoginUserId());
            if (user == null) {
                return sendError(HttpStatus.SC_400_BAD_REQUEST, "user is allready removed.");
            }
            if (user.getAuthLdap() != null && user.getAuthLdap().intValue() == INT_FLAG.ON.getValue()) {
                return sendError(HttpStatus.SC_400_BAD_REQUEST, "can not edit ldap user.");
            }
            if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
                // ユーザ登録を管理者が行っている場合、メールアドレスは変更出来ない（変更用の画面も使えない）
                LOG.trace("USER_ADD_TYPE_VALUE_ADMIN");
            } else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE)) {
                // ユーザが自分で登録して管理者が承認の場合も変更出来ない（変更用の画面も使えない）
                LOG.trace("USER_ADD_TYPE_VALUE_APPROVE");
            } else if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
                // ダブルオプトインの場合も変更出来ない（変更用の画面で変更）
                LOG.trace("USER_ADD_TYPE_VALUE_MAIL");
            } else {
                user.setUserKey(getParam("userKey"));
            }
            user.setUserName(getParam("userName"));
            if (!StringUtils.isEmpty(getParam("password"))) {
                user.setPassword(getParam("password"));
                user.setEncrypted(false);
            }
            if (StringUtils.isEmpty(user.getMailAddress())) {
                if (StringUtils.isEmailAddress(user.getUserKey())) {
                    user.setMailAddress(user.getUserKey());
                }
            }
            dao.update(user);
        }
        String successMsg = "message.success.update";
        setResult(successMsg, errors);

        return forward("index.jsp");
    }

    /**
     * 退会画面を表示
     * 
     * @return
     */
    @Get(publishToken = "withdrawal")
    public Boundary withdrawal() {
        return forward("withdrawal.jsp");
    }

    /**
     * 退会を実行
     * 
     * @return
     * @throws Exception
     */
    @Post(subscribeToken = "withdrawal", checkReqToken = true)
    public Boundary delete() throws Exception {
        // アカウント削除(退会処理)
        boolean knowledgeRemove = true;
        if ("2".equals(getParam("knowledge_remove"))) {
            knowledgeRemove = false;
        }
        UserLogicEx.get().withdrawal(getLoginUserId(), knowledgeRemove, HttpUtil.getLocale(getRequest()));

        // セッションを破棄
        AuthenticationLogic<LoginedUser> authenticationLogic = Container.getComp(KnowledgeAuthenticationLogic.class);
        authenticationLogic.clearSession(getRequest());

        addMsgInfo("knowledge.account.delete");
        return devolution(HttpMethod.get, "Index/index");
        // return redirect(getRequest().getContextPath());
    }

    /**
     * アイコン画像をアップロード
     * 
     * @return
     * @throws IOException
     */
    @Post(subscribeToken = "account")
    public Boundary iconupload() throws IOException {
        AccountLogic logic = AccountLogic.get();
        String fileimg = getParam("fileimg");
        if (StringUtils.isEmpty(fileimg)) {
            ValidateError error = new ValidateError("errors.required", "Image");
            Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
            return send(HttpStatus.SC_400_BAD_REQUEST, msg);
        }
        
        if (fileimg.startsWith("data:image/png;base64,")) {
            fileimg = fileimg.substring("data:image/png;base64,".length());
            byte[] img = Base64.decodeBase64(fileimg);
            
            if (img.length > ICON_IMAGE_MAX_SIZE) {
                ValidateError error = new ValidateError("errors.maxfilesize", "5MB");
                Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
                return send(HttpStatus.SC_400_BAD_REQUEST, msg);
            }
            
            UploadFile file = logic.saveIconImage(img, getLoginedUser(), getRequest().getContextPath());
            return send(HttpStatus.SC_200_OK, file);
        }
        
        return send(HttpStatus.SC_400_BAD_REQUEST, "data error");
    }

    /**
     * メールアドレスの変更リクエストを登録する画面を表示
     * 
     * @return
     */
    @Get(publishToken = "changekey")
    public Boundary changekey() {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }
        // ダブルオプトインでユーザ登録をしている場合のみ、メールアドレス変更通知にてアドレスを変更する
        return forward("changekey.jsp");
    }

    /**
     * メールアドレスの変更リクエストを登録
     * 
     * @return
     */
    @Post(subscribeToken = "changekey", checkReqToken = true)
    public Boundary changerequest() {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }

        // ダブルオプトインでユーザ登録をしている場合のみ、メールアドレス変更通知にてアドレスを変更する
        AccountLogic accountLogic = AccountLogic.get();
        List<ValidateError> results = accountLogic.saveChangeEmailRequest(getParam("userKey"), getLoginedUser());

        setResult("message.success.insert.target", results, getResource("knowledge.account.changekey.title"));

        if (results != null && !results.isEmpty()) {
            return forward("changekey.jsp");
        }
        return forward("saveresult.jsp");
    }

    /**
     * メールアドレス変更通知の確認
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary confirm_mail() throws InvalidParamException {
        // メールアドレス変更ができるのは、ダブルオプトインでユーザ登録する設定になっている場合のみ
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity userAddType = dao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
        if (userAddType == null) {
            userAddType = new SystemConfigsEntity(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
            userAddType.setConfigValue(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN);
        }
        if (!userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_MAIL)) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
        }

        String id = getPathString();
        if (StringUtils.isEmpty(id)) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        AccountLogic accountLogic = AccountLogic.get();
        List<ValidateError> results = accountLogic.completeChangeEmailRequest(id, getLoginedUser());
        setResult("knowledge.account.changekey.complete", results);
        if (results != null && !results.isEmpty()) {
            return index(); // 何かエラーになった場合アカウントの画面へ遷移
        }
        // return forward("complete.jsp");
        return index(); // エラーの無い場合でもアカウントの画面へ遷移（新しいメールアドレスになったことを確認）
    }
    
    /**
     * デフォルトの公開範囲を表示
     * @return
     */
    @Get(publishToken = "knowledge")
    public Boundary targets() {
        UserConfigsEntity publicFlag = UserConfigsDao.get().physicalSelectOnKey(
                UserConfig.DEFAULT_PUBLIC_FLAG, AppConfig.get().getSystemName(), getLoginUserId());
        if (publicFlag != null) {
            setAttribute("publicFlag", publicFlag.getConfigValue());
            UserConfigsEntity targets = UserConfigsDao.get().physicalSelectOnKey(
                    UserConfig.DEFAULT_TARGET, AppConfig.get().getSystemName(), getLoginUserId());
            if (targets != null) {
                if (StringUtils.isNotEmpty(targets.getConfigValue())) {
                    String[] targetKeys = targets.getConfigValue().split(",");
                    List<LabelValue> viewers = TargetLogic.get().selectTargets(targetKeys);
                    setAttribute("viewers", viewers);
                }
            }
        }
        return forward("targets.jsp");
    }
    
    
    @Post(subscribeToken = "knowledge")
    public Boundary savetargets() {
        String publicFlag = getParam("publicFlag");
        String viewers = getParam("viewers");
        UserConfigsEntity publicFlagEntiry = new UserConfigsEntity(UserConfig.DEFAULT_PUBLIC_FLAG, AppConfig.get().getSystemName(), getLoginUserId());
        publicFlagEntiry.setConfigValue(publicFlag);
        UserConfigsDao.get().save(publicFlagEntiry);
        
        UserConfigsEntity targetsEntity = new UserConfigsEntity(UserConfig.DEFAULT_TARGET, AppConfig.get().getSystemName(), getLoginUserId());
        targetsEntity.setConfigValue(viewers);
        
        List<ValidateError> results = targetsEntity.validate();
        if (results != null && !results.isEmpty()) {
            setResult("message.success.save", results);
            return targets();
        }
        UserConfigsDao.get().save(targetsEntity);
        
        addMsgSuccess("message.success.save");
        return targets();
    }
    
    
}
