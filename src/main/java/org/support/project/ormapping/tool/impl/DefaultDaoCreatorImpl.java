package org.support.project.ormapping.tool.impl;

import java.util.Collection;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.tool.DaoClassCreator;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultDaoCreatorImpl implements DaoClassCreator {
    /** ログ */
    private static Log log = LogFactory.getLog(DefaultDaoCreatorImpl.class);

    @Override
    public void create(Collection<TableDefinition> tableDefinitions, DaoGenConfig config) throws ORMappingException {
        for (TableDefinition tableDefinition : tableDefinitions) {
            config.setTableDefinition(tableDefinition);

            log.info("テーブル [" + config.getTableDefinition().getTable_name() + "] の処理を開始します。");

            DefaultTableSQLCreator sqlCreator = new DefaultTableSQLCreator(config);
            sqlCreator.createDefaultSql();

            DefaultTableDaoClassCreator daoClassCreator = new DefaultTableDaoClassCreator(config);

            daoClassCreator.create();
        }
        
        // DBの管理用のDaoクラスの生成
        DatabaseControlDaoCreator creator = new DatabaseControlDaoCreator();
        creator.create(tableDefinitions, config);
        
    }

}
