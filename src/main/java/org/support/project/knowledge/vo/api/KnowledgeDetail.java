package org.support.project.knowledge.vo.api;

import java.util.List;

import org.support.project.web.bean.LabelValue;

public class KnowledgeDetail extends Knowledge {
    /** テンプレートの項目値 */
    private List<LabelValue> templateItems;
    /** コメント */
    private List<Comment> comments;
    /** 添付ファイル */
    private List<AttachedFile> attachments;
    
    /** 編集可能な対象（共同編集者） */
    private Target editors;
    
    /**
     * @return the templateItems
     */
    public List<LabelValue> getTemplateItems() {
        return templateItems;
    }
    /**
     * @param templateItems the templateItems to set
     */
    public void setTemplateItems(List<LabelValue> templateItems) {
        this.templateItems = templateItems;
    }
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
    public Target getEditors() {
        return editors;
    }
    /**
     * @param editors the editors to set
     */
    public void setEditors(Target editors) {
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
}
