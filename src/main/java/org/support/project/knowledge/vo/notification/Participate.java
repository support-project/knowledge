package org.support.project.knowledge.vo.notification;

public class Participate extends KnowledgeUpdate {
    /** 参加者 */
    private int participant;
    /** 参加者のステータス */
    private int status;
    /**
     * @return the participant
     */
    public int getParticipant() {
        return participant;
    }
    /**
     * @param participant the participant to set
     */
    public void setParticipant(int participant) {
        this.participant = participant;
    }
    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
