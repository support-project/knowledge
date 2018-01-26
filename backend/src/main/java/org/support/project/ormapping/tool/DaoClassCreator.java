package org.support.project.ormapping.tool;

import java.util.Collection;

import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * Creator for Dao Class.
 * 
 * @author Koda
 */
public interface DaoClassCreator {
    /**
     * Daoのクラスを生成する
     * @param tableDefinitions Definition data of table.
     * @param config config 
     * @throws ORMappingException ORMappingException
     */
    void create(Collection<TableDefinition> tableDefinitions, DaoGenConfig config) throws ORMappingException;

}
