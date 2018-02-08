package org.support.project.web.deploy;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.support.project.common.classanalysis.ClassSearch;
import org.support.project.common.classanalysis.impl.ClassSearchImpl;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.web.config.AppConfig;
import org.support.project.web.dao.SystemsDao;
import org.support.project.web.entity.SystemsEntity;


public class InitDB {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private Map<Double, Migrate> migrateMap = new TreeMap<>();
    
    private String systemName;
    private String migratePackage;
    
    public InitDB() {
        super();
        systemName = AppConfig.get().getSystemName();
        migratePackage = getClass().getPackage().getName() + ".migrate";
    }
    
    public void setMigratePackage(String pkg) {
        this.migratePackage = pkg;
    }
    
    public void start() throws Exception {
        this.getMigrate();
        this.doMigrate();
    }

    private void getMigrate() {
        ClassSearch classSearch = new ClassSearchImpl();
        Class<?>[] classes = classSearch.classSearch(migratePackage, true);
        if (classes != null) {
            for (Class<?> class1 : classes) {
                if (Migrate.class.isAssignableFrom(class1)) {
                    Migrate migrate = (Migrate) Container.getComp(class1);
                    migrateMap.put(migrate.getVersion(), migrate);
                }
            }
        }
    }
    
    private void doMigrate() throws Exception {
        double version = 0;
        SystemsDao dao = SystemsDao.get();
        SystemsEntity entity = null;
        try {
            entity = dao.selectOnKey(systemName);
        } catch (Exception e) {
            // 存在していない場合
            LOG.info(e.getMessage());
        }
        if (entity != null && StringUtils.isNumeric(entity.getVersion())) {
            version = Double.parseDouble(entity.getVersion());
        } else {
            entity = new SystemsEntity(systemName);
        }
        Iterator<Double> versions = migrateMap.keySet().iterator();
        while (versions.hasNext()) {
            Double v = versions.next();
            if (version < v) {
                LOG.info("Migrate to [" + version + "]");
                Migrate migrate = migrateMap.get(v);
                boolean result = migrate.doMigrate();
                if (result) {
                    entity.setVersion(String.valueOf(v));
                    dao.save(entity);
                    version = v;
                } else {
                    LOG.info("Migrate error. [" + version + "]");
                }
            } else {
                LOG.info("Skip migrate. [" + version + "]");
                
            }
        }
    }

}
