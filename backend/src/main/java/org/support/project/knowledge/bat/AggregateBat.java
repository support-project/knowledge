package org.support.project.knowledge.bat;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.AggregateLogic;

public class AggregateBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AggregateBat.class);

    public static void main(String[] args) throws Exception {
        try {
            initLogName("AggregateBat.log");
            configInit(ClassUtils.getShortClassName(AggregateBat.class));
            
            AggregateBat bat = new AggregateBat();
            bat.dbInit();
            bat.start();
            
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    /**
     * 処理の実行
     * @throws Exception Exception
     */
    public void start() throws Exception {
        AggregateLogic aggregateLogic = AggregateLogic.get();
        aggregateLogic.startAggregate();
    }
}
