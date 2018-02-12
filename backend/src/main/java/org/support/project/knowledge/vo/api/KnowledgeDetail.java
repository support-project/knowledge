package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

import org.support.project.web.bean.LabelValue;

public class KnowledgeDetail extends Knowledge {
    /** テンプレートの項目値 */
    private List<LabelValue> items = new ArrayList<>();
    /** コメント */
    private List<Comment> comments = new ArrayList<>();
    /** 添付ファイル */
    private List<AttachedFile> attachments = new ArrayList<>();
    
    /** 編集可能な対象（共同編集者） */
    private Targets editors;
    
    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }
    /**
     * @param comments the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    /**
     * @return the editors
     */
    public Targets getEditors() {
        return editors;
    }
    /**
     * @param editors the editors to set
     */
    public void setEditors(Targets editors) {
        this.editors = editors;
    }
    /**
     * @return the attachments
     */
    public List<AttachedFile> getAttachments() {
        return attachments;
    }
    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<AttachedFile> attachments) {
        this.attachments = attachments;
    }
    public List<LabelValue> getItems() {
        return items;
    }
    public void setItems(List<LabelValue> items) {
        this.items = items;
    }
}
