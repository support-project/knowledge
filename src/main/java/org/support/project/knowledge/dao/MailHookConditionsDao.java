package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailHookConditionsDao;

/**
 * メールから投稿する条件
 */
@DI(instance = Instance.Singleton)
public class MailHookConditionsDao extends GenMailHookConditionsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailHookConditionsDao get() {
        return Container.getComp(MailHookConditionsDao.class);
    }

    /**
     * メール投稿設定の番号を発行
     * 同時のタイミングでアクセスがあると、同じ番号を発行する可能性があるが、
     * 管理者メニューであり、通常操作で発生する事は無いと思われるので簡単に作っておく
     * @param hookId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int nextConditionNo(Integer hookId) {
        String sql = "SELECT COUNT(*) FROM MAIL_HOOK_CONDITIONS WHERE HOOK_ID = ?";
        int num = super.executeQuerySingle(sql, Integer.class, hookId);
        num++;
        return num;
    }


}
