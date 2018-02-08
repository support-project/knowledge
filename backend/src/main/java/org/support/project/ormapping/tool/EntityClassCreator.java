package org.support.project.ormapping.tool;

import java.util.Collection;

import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * Create java code file of Entity class.
 * 
 * @author Koda
 *
 */
public interface EntityClassCreator {

    /**
     * Entityのクラスを生成する
     * 
     * @param tableDefinitions
     *            tableDefinitions
     * @param dir
     *            output directory path
     * @param packageName
     *            package name
     * @param suffix
     *            suffix of Entity class
     * @throws ORMappingException
     *             ORMappingException
     */
    void create(Collection<TableDefinition> tableDefinitions, String dir, String packageName, String suffix) throws ORMappingException;

}
