package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPinsDao;

/**
 * ピン
 */
@DI(instance = Instance.Singleton)
public class PinsDao extends GenPinsDao {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    
    /**
     * インスタンス取得
     * AOPに対応
     * @return インスタンス
     */
    public static PinsDao get() {
        return Container.getComp(PinsDao.class);
    }

    /**
     * ID 
     */
    private int currentId = 0;

    /**
     * IDを採番 
     * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる 
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(no) FROM pins;";
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