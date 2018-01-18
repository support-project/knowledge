package org.support.project.knowledge.bat;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;


public class ReIndexingBat extends AbstractBat {

    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    public static void main(String[] args) throws Exception {
        try {
            initLogName("ReIndexingBat.log");
            configInit(ClassUtils.getShortClassName(ReIndexingBat.class));
            
            ReIndexingBat bat = new ReIndexingBat();
            bat.dbInit(); //カスタマイズDBが設定されていてばそれを参照
            bat.start();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    private void start() throws Exception {
        out("start");
        SystemConfigsEntity entity = SystemConfigsDao.get().selectOnKey(SystemConfig.RE_INDEXING, AppConfig.get().getSystemName());
        if (entity != null) {
            String[] values = entity.getConfigValue().split(",");
            Long start = Long.valueOf(values[0].substring("start=".length()));
            Long end = Long.valueOf(values[1].substring("end=".length()));
            
            out("search knowledge:" + start + "-" + end);
            KnowledgesDao knowledgesDao = KnowledgesDao.get();
            List<KnowledgesEntity> knowledgesEntities = knowledgesDao.selectBetween(start, end);
            for (KnowledgesEntity knowledgesEntity : knowledgesEntities) {
                out("indexing knowledge: " + knowledgesEntity.getKnowledgeId());
                KnowledgeLogic.get().reindexing(knowledgesEntity);
            }
            SystemConfigsDao.get().physicalDelete(entity);
        }
        out("finish");
    }


    /**
     * メッセージをコンソールに出力
     * @param str
     */
    private void out(String str) {
        LOG.info("[SEND]" + str);
    }

}