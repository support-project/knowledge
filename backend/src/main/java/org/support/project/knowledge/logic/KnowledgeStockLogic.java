package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.knowledge.vo.Stock;
import org.support.project.knowledge.vo.api.StockSelect;
import org.support.project.web.bean.AccessUser;

@DI(instance = Instance.Singleton)
public class KnowledgeStockLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static KnowledgeStockLogic get() {
        return Container.getComp(KnowledgeStockLogic.class);
    }
    
    private StockSelect getSelected(Long stockId, List<StockSelect> list) {
        for (StockSelect stockSelect : list) {
            if (stockId.longValue() == stockSelect.getStockId()) {
                return stockSelect;
            }
        }
        return null;
    }
    
    /**
     * 記事をストックに入れたり、ストックから外したりする
     * @param knowledgeId
     * @param stocks
     * @param accessUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void setStocks(long knowledgeId, List<Stock> stocks, AccessUser accessUser) {
        LOG.trace("setStocks");
        StockKnowledgesDao dao = StockKnowledgesDao.get();
        for (Stock stock : stocks) {
            StockKnowledgesEntity entity = new StockKnowledgesEntity();
            entity.setStockId(stock.getStockId());
            entity.setKnowledgeId(knowledgeId);
            if (stock.getStocked()) {
                entity.setComment(stock.getComment());
                dao.save(entity);
            } else {
                // 存在チェックはしていない
                dao.physicalDelete(entity);
            }
        }
        ActivityLogic.get().processActivity(Activity.KNOWLEDGE_STOCK, accessUser, DateUtils.now(),
                KnowledgesDao.get().selectOnKey(knowledgeId));
    }

}
