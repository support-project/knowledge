package org.support.project.knowledge.vo;

import java.util.List;

import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;

public class StockKnowledge extends KnowledgesEntity {
    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;
    
    private List<StocksEntity> stocks;
    
    private Participations participations;
    
    /**
     * Get stocks
     * @return the stocks
     */
    public List<StocksEntity> getStocks() {
        return stocks;
    }

    /**
     * Set stocks
     * @param stocks the stocks to set
     */
    public void setStocks(List<StocksEntity> stocks) {
        this.stocks = stocks;
    }

    /**
     * @return the participations
     */
    public Participations getParticipations() {
        return participations;
    }

    /**
     * @param participations the participations to set
     */
    public void setParticipations(Participations participations) {
        this.participations = participations;
    }
    
    
}
