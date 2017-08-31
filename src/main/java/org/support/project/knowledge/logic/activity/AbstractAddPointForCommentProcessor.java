package org.support.project.knowledge.logic.activity;

import org.support.project.knowledge.entity.CommentsEntity;

public abstract class AbstractAddPointForCommentProcessor extends AbstractActivityProcessor {
    private CommentsEntity comment;
    /**
     * @return the comment
     */
    public CommentsEntity getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(CommentsEntity comment) {
        this.comment = comment;
    }
}
