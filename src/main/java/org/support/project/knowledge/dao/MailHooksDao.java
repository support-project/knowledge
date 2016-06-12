package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailHooksDao;

/**
 * 受信したメールからの処理
 */
@DI(instance = Instance.Singleton)
public class MailHooksDao extends GenMailHooksDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailHooksDao get() {
        return Container.getComp(MailHooksDao.class);
    }


    /**
     * ID 
     */
    private int currentId = 0;

    /**
     * Get Next id
     * @return next id
     */
    public Integer getNextId() {
        String sql = "SELECT MAX(HOOK_ID) FROM MAIL_HOOKS;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null) {
            if (currentId < integer) {
                currentId = integer;
            }
        }
        currentId++;
        return currentId;
    }


}
