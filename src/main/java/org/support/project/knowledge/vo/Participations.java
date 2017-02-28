package org.support.project.knowledge.vo;

import java.io.Serializable;
import java.util.List;

public class Participations implements Serializable {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    /** 参加可能人数 */
    private Integer limit;
    /** 参加登録人数 */
    private Integer count;
    /** 参加者一覧 */
    private List<Participation> participations;
    
    /** 自分の参加ステータス(参加予定、補欠登録） */
    private Integer status;

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the participations
     */
    public List<Participation> getParticipations() {
        return participations;
    }

    /**
     * @param participations the participations to set
     */
    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
