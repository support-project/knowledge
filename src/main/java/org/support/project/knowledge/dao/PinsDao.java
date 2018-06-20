package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPinsDao;
import org.support.project.knowledge.entity.PinsEntity;

import java.sql.Timestamp;

/**
 * ピン
 */
@DI(instance = Instance.Singleton)
public class PinsDao extends GenPinsDao {
    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * ピン留めは、ユーザ毎に設定できるようにDBが設計されている。
     * いったん、管理者がピン留めしているものを設定するため、登録ユーザを固定値で登録する
     */
    private static final int ADMIN_ID = Integer.MIN_VALUE;


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

    public PinsEntity getPinByAdmin(Long knowledgeId) {
        String sql = "SELECT * FROM pins where KNOWLEDGE_ID = ? and INSERT_USER = ? and DELETE_FLAG != 1;";
        return executeQuerySingle(sql, PinsEntity.class, knowledgeId, ADMIN_ID);
    }


    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void insertPinByAdmin(Long id) {
        PinsEntity entity = new PinsEntity();
        entity.setKnowledgeId(id);
        entity.setInsertUser(ADMIN_ID);
        entity.setInsertDatetime(new Timestamp(DateUtils.now().getTime()));
        entity.setDeleteFlag(0);
        rawPhysicalInsert(entity);
    }
}