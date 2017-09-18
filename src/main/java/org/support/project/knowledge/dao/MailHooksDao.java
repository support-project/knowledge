package org.support.project.knowledge.dao;

import java.sql.Timestamp;

import org.support.project.aop.Aspect;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenMailHooksDao;
import org.support.project.knowledge.entity.MailHooksEntity;
import org.support.project.ormapping.common.DBUserPool;

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
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
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

    /* (non-Javadoc)
     * @see org.support.project.knowledge.dao.gen.GenMailHooksDao#save(org.support.project.knowledge.entity.MailHooksEntity)
     */
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHooksEntity save(MailHooksEntity entity) {
        if (entity.getHookId() != null) {
            MailHooksEntity db = physicalSelectOnKey(entity.getHookId());
            if (db != null) {
                physicalDelete(entity.getHookId());
            }
            entity.setDeleteFlag(INT_FLAG.OFF.getValue());
            entity.setInsertUser((Integer) DBUserPool.get().getUser());
            entity.setInsertDatetime(new Timestamp(DateUtils.now().getTime()));
            return rawPhysicalInsert(entity);
        } else {
            return super.save(entity);
        }
    }
    
    
    

}
