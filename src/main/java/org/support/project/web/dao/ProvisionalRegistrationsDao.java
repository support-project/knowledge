package org.support.project.web.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.exception.SystemException;
import org.support.project.common.util.PasswordUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.config.AppConfig;
import org.support.project.web.dao.gen.GenProvisionalRegistrationsDao;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;

/**
 * 仮登録ユーザ
 */
@DI(instance = Instance.Singleton)
public class ProvisionalRegistrationsDao extends GenProvisionalRegistrationsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ProvisionalRegistrationsDao get() {
        return Container.getComp(ProvisionalRegistrationsDao.class);
    }

    @Override
    public ProvisionalRegistrationsEntity physicalInsert(ProvisionalRegistrationsEntity entity) {
        // DBに保存する直前に暗号化する
        passwordEncrypted(entity);
        return super.physicalInsert(entity);
    }

    @Override
    public ProvisionalRegistrationsEntity physicalUpdate(ProvisionalRegistrationsEntity entity) {
        // DBに保存する直前に暗号化する
        passwordEncrypted(entity);
        return super.physicalUpdate(entity);
    }

    /**
     * DBに保存する直前に暗号化する
     * 
     * @param entity ProvisionalRegistrationsEntity
     */
    private void passwordEncrypted(ProvisionalRegistrationsEntity entity) {
        try {
            if (entity.getEncrypted() == null || !entity.getEncrypted()) {
                String salt = PasswordUtil.getSalt();
                entity.setSalt(salt);
                AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
                entity.setPassword(PasswordUtil.getStretchedPassword(entity.getPassword(), salt, config.getHashIterations()));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(e);
        }
    }

    /**
     * ユーザのキー(メールアドレス)を指定し、情報を削除
     * @param userKey userKey
     */
    public void deleteOnUserKey(String userKey) {
        List<ProvisionalRegistrationsEntity> list = selectOnUserKey(userKey);
        if (list != null) {
            for (ProvisionalRegistrationsEntity entity : list) {
                delete(entity);
            }
        }
    }

    /**
     * ユーザのキー(メールアドレス)を指定し、情報を取得
     * @param userKey userKey
     * @return 仮登録情報
     */
    public List<ProvisionalRegistrationsEntity> selectOnUserKey(String userKey) {
        String sql = "SELECT * FROM PROVISIONAL_REGISTRATIONS WHERE USER_KEY = ? AND DELETE_FLAG = 0";
        return executeQueryList(sql, ProvisionalRegistrationsEntity.class, userKey);
    }

}
