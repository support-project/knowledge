package org.support.project.knowledge.vo.api.internal;

import java.util.List;

import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.vo.Participations;
import org.support.project.knowledge.vo.api.Knowledge;

/**
 * 内部APIで一覧取得する場合に返す記事の情報を保持する
 * @author koda
 */
public class KnowledgeList extends Knowledge {
    private List<StocksEntity> stocks;
    private Participations participations;
    private boolean viewed = false;
    
    public List<StocksEntity> getStocks() {
        return stocks;
    }
    public void setStocks(List<StocksEntity> stocks) {
        this.stocks = stocks;
    }
    public Participations getParticipations() {
        return participations;
    }
    public void setParticipations(Participations participations) {
        this.participations = participations;
    }
    public boolean isViewed() {
        return viewed;
    }
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
