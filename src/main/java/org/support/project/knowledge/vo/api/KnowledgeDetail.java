package org.support.project.knowledge.vo.api;

import java.util.List;

import org.support.project.web.bean.LabelValue;

public class KnowledgeDetail extends Knowledge {
    /** テンプレートの項目値 */
    private List<LabelValue> templateItems;
    /** コメント */
    private List<Comment> comments;
    /** 添付ファイル */
    private List<AttachedFile> attachedFiles;
    
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
     * @return the attachedFiles
     */
    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }
    /**
     * @param attachedFiles the attachedFiles to set
     */
    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
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
    
    
    
    
    
    
    
    
    
    
}
