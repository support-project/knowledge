package org.support.project.ormapping.gen.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.gen.dao.gen.GenClobTableDao;
import org.support.project.ormapping.gen.entity.ClobTableEntity;

/**
 * CLOBのテストテーブル
 */
@DI(instance = Instance.Singleton)
public class ClobTableDao extends GenClobTableDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ClobTableDao get() {
        return Container.getComp(ClobTableDao.class);
    }

    /**
     * ID
     */
    private int currentId = 0;

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     */
    public Integer getNextId() {
        String sql = "SELECT MAX(NO) FROM CLOB_TABLE;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null) {
            if (currentId < integer) {
                currentId = integer;
            }
        }
        currentId++;
        return currentId;
    }

    /**
     * Contentで検索
     */
    public List<ClobTableEntity> searchContent(String str) {
        String sql = "select * from CLOB_TABLE where contents like ? ";
        return executeQueryList(sql, ClobTableEntity.class, str);
    }

}
