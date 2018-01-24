package org.support.project.web.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenRoleFunctionsDao;
import org.support.project.web.entity.RoleFunctionsEntity;

/**
 * 機能にアクセスできる権限
 */
@DI(instance = Instance.Singleton)
public class RoleFunctionsDao extends GenRoleFunctionsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static RoleFunctionsDao get() {
        return Container.getComp(RoleFunctionsDao.class);
    }

    /**
     * その機能にアクセスできる権限を全て取得
     * 
     * @param functionKey functionKey
     * @return functions
     */
    public List<RoleFunctionsEntity> selectOnFunction(String functionKey) {
        String sql = "SELECT * FROM ROLE_FUNCTIONS WHERE FUNCTION_KEY = ?";
        return executeQueryList(sql, RoleFunctionsEntity.class, functionKey);
    }

}
