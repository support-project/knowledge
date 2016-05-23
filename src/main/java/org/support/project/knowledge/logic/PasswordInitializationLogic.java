package org.support.project.knowledge.logic;

import java.util.Locale;

import org.support.project.aop.Aspect;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.PasswordResetsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.PasswordResetsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class PasswordInitializationLogic {

    public static PasswordInitializationLogic get() {
        return Container.getComp(PasswordInitializationLogic.class);
    }

    /**
     * パスワードリセットメールの送信
     * 
     * @param email
     * @return
     */
    public ValidateError insertPasswordReset(String email, Locale locale) {
        // 存在チェック
        UsersDao usersDao = UsersDao.get();
        if (usersDao.selectOnUserKey(email) == null) {
            return new ValidateError("errors.noexist", "Email Address");
        }

        // 初期化データ登録
        PasswordResetsEntity resetsEntity = new PasswordResetsEntity();
        resetsEntity.setId(MailLogic.get().idGen("RESET-").concat("-").concat(RandomUtil.randamGen(32)));
        resetsEntity.setUserKey(email);
        PasswordResetsDao resetsDao = PasswordResetsDao.get();
        resetsDao.insert(resetsEntity);

        // メール送信
        MailLogic.get().sendPasswordReset(email, locale, resetsEntity);

        return null;
    }

    /**
     * パスワード初期化
     * 
     * @param resetsEntity
     * @param password
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ValidateError changePassword(PasswordResetsEntity resetsEntity, String password) {
        // パスワード更新
        UsersDao usersDao = UsersDao.get();
        UsersEntity entity = usersDao.selectOnUserKey(resetsEntity.getUserKey());
        if (entity == null) {
            return new ValidateError("errors.noexist", "Email Address");
        }
        entity.setUpdateUser(entity.getUserId());
        entity.setPassword(password);
        usersDao.update(entity);

        // パスワード初期化リクエストを無効にする
        PasswordResetsDao.get().delete(resetsEntity);

        return null;
    }

}
