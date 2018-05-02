package org.support.project.ormapping.tool;

import org.support.project.di.Container;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.tool.impl.DefaultDaoCreatorImpl;
import org.support.project.ormapping.tool.impl.DefaultEntityCteatorImpl;

public class ORMappingToolFactory {
    
    /**
     * ドライバークラスにあったEntity生成クラスを取得する
     * @param driverClassName JDBCドライバークラス
     * @return Entity生成クラス
     */
    public static EntityClassCreator getEntityClassCreator(String driverClassName) {
        if (driverClassName.equals(ORMappingParameter.DRIVER_NAME_H2)) {
            // 今のところ分けていない
            return Container.getComp(DefaultEntityCteatorImpl.class);
        }
        return Container.getComp(DefaultEntityCteatorImpl.class);
    }
    
    
    
    /**
     * ドライバークラスにあったDao生成クラスを取得する
     * @param driverClass JDBCドライバークラス
     * @return Entity生成クラス
     */
    public static DaoClassCreator getDaoClassCreator(String driverClass) {
        if (driverClass.equals(ORMappingParameter.DRIVER_NAME_H2)) {
            // 今のところ分けていない
            return Container.getComp(DefaultDaoCreatorImpl.class);
        }
        return Container.getComp(DefaultDaoCreatorImpl.class);
    }
    
}
