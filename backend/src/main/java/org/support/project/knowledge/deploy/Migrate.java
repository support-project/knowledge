package org.support.project.knowledge.deploy;

public interface Migrate {

    /**
     * マイグレーション実行
     * 
     * @return
     * @throws Exception
     */
    boolean doMigrate() throws Exception;

}
