package org.support.project.knowledge.control.open;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.PasswordInitializationLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.PasswordResetsDao;
import org.support.project.web.entity.PasswordResetsEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class PasswordInitializationControl extends Control {

    /**
     * パスワード忘れの画面を表示
     * 
     * @return
     */
    @Get(publishToken = "knowledge")
    public Boundary view() {
        return forward("forgot_pass_request.jsp");
    }

    /**
     * パスワード初期化のリクエストの受付
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary request() {
        String email = getParam("username");

        // 入力チェック
        List<ValidateError> errors = new ArrayList<ValidateError>();
        Validator validator = ValidatorFactory.getInstance(Validator.REQUIRED);
        ValidateError validateError = validator.validate(email, "Email address");
        if (validateError != null) {
            errors.add(validateError);
        }

        validator = ValidatorFactory.getInstance(Validator.MAIL);
        validateError = validator.validate(email, "Email address");
        if (validateError != null) {
            errors.add(validateError);
        }
        if (!errors.isEmpty()) {
            setResult("message.success.insert", errors);
            return forward("forgot_pass_request.jsp");
        }

        // 初期化パスワードデータを発行
        validateError = PasswordInitializationLogic.get().insertPasswordReset(email, HttpUtil.getLocale(getRequest()));
        if (validateError != null) {
            errors.add(validateError);
        }

        // 画面表示
        setResult("message.success.insert", errors);
        if (!errors.isEmpty()) {
            return forward("forgot_pass_request.jsp");
        }

        return forward("forgot_pass_result.jsp");
    }

    /**
     * パスワード初期化画面を表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary init() throws InvalidParamException {
        String key = getPathString();
        PasswordResetsDao resetsDao = PasswordResetsDao.get();
        PasswordResetsEntity resetsEntity = resetsDao.selectOnKey(key);
        if (resetsEntity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }

        long now = DateUtils.now().getTime();
        if (now - resetsEntity.getInsertDatetime().getTime() > 1000 * 60 * 60) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        setAttribute("key", key);
        setAttribute("reset", resetsEntity);
        return forward("password_reset.jsp");
    }

    /**
     * パスワードの初期化
     * 
     * @return
     */
    @Post(subscribeToken = "knowledge")
    public Boundary change() {
        String key = getParam("key");
        PasswordResetsDao resetsDao = PasswordResetsDao.get();
        PasswordResetsEntity resetsEntity = resetsDao.selectOnKey(key);
        if (resetsEntity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        long now = DateUtils.now().getTime();
        if (now - resetsEntity.getInsertDatetime().getTime() > 1000 * 60 * 60) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        setAttribute("key", key);
        setAttribute("reset", resetsEntity);

        List<ValidateError> errors = new ArrayList<>();
        if (!StringUtils.isEmpty(getParam("password"))) {
            if (!getParam("password").equals(getParam("confirm_password", String.class))) {
                ValidateError error = new ValidateError("knowledge.user.invalid.same.password");
                errors.add(error);
            }
        } else {
            ValidateError error = new ValidateError("errors.required", "Password");
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            return forward("password_reset.jsp");
        }

        // パスワードデータを初期化
        ValidateError validateError = PasswordInitializationLogic.get().changePassword(resetsEntity, getParam("password"));
        if (validateError != null) {
            errors.add(validateError);
        }

        // 画面表示
        setResult(getResource("message.success.update.target", "Password"), errors);
        if (!errors.isEmpty()) {
            return forward("password_reset.jsp");
        }
        return forward("reset_result.jsp");
    }

}
