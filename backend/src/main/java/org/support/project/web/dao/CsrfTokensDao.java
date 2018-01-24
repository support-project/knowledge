package org.support.project.web.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.support.project.aop.Aspect;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.CSRFToken;
import org.support.project.web.dao.gen.GenCsrfTokensDao;
import org.support.project.web.entity.CsrfTokensEntity;

/**
 * CSRF_TOKENS
 */
@DI(instance = Instance.Singleton)
public class CsrfTokensDao extends GenCsrfTokensDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static CsrfTokensDao get() {
        return Container.getComp(CsrfTokensDao.class);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public String addToken(String key, Integer userId) throws NoSuchAlgorithmException {
        CsrfTokensEntity csrf = selectOnKey(key, userId);
        if (csrf == null) {
            CSRFToken csrfToken = CSRFToken.create(key);
            csrf = new CsrfTokensEntity();
            csrf.setProcessName(key);
            csrf.setUserId(userId);
            csrf.setToken(csrfToken.getToken());
            csrf.setExpires(new Timestamp(DateUtils.now().getTime() + (1000 * 60 * 30))); // 30分
            insert(csrf);
            return csrfToken.getToken();
        } else if (csrf.getExpires().getTime() < DateUtils.now().getTime()) {
            // 有効期限が切れている為、新しいキーを再発行
            CSRFToken csrfToken = CSRFToken.create(key);
            csrf.setToken(csrfToken.getToken());
            csrf.setExpires(new Timestamp(DateUtils.now().getTime() + (1000 * 60 * 30))); // 30分
            update(csrf);
            return csrfToken.getToken();
        } else if (csrf.getExpires().getTime() - (1000 * 60 * 20) < DateUtils.now().getTime()) { // 後10分で切れる
            // 有効期限がもうすぐ切れそうなので、有効期限を更新する
            // 編集画面などでは、バックグラウンドでCSRFTokenを取得するGetリクエストを定期的に送り、有効期限を更新していくように実装する
            csrf.setExpires(new Timestamp(DateUtils.now().getTime() + (1000 * 60 * 30))); // 30分
            update(csrf);
            return csrf.getToken();
        } else {
            return csrf.getToken();
        }
    }



}
