package org.support.project.knowledge.vo;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.web.bean.LabelValue;

public class KnowledgeData {
    private KnowledgesEntity knowledge;
    private List<TagsEntity> tags;
    private List<LabelValue> viewers;
    private List<LabelValue> editors;
    private List<Long> fileNos;
    private TemplateMastersEntity template;
    private Long draftId;
    
    private boolean updateContent = false;
    private boolean notifyUpdate = false;
    private boolean donotUpdateTimeline = false;
    
    private String viewersStr;
    private String editorsStr;
    private String tagsStr;
    private String[] filesStrs;
    
    public static KnowledgeData create(KnowledgesEntity knowledge, String viewersStr, String editorsStr,
            String tagsStr, String[] filesStrs, Long draftId, TemplateMastersEntity template) {
        KnowledgeData data = new KnowledgeData();
        
        data.setKnowledge(knowledge);
        data.setViewers(viewersStr);
        data.setEditors(editorsStr);
        data.setTags(tagsStr);
        data.setFileNos(filesStrs);
        data.setDraftId(draftId);
        data.setTemplate(template);
        
        return data;
    }
    
    
    public void setViewers(String viewersStr) {
        this.viewersStr = viewersStr;
        String[] targets = viewersStr.split(",");
        this.viewers = TargetLogic.get().selectTargets(targets);
    }
    public void setEditors(String editorsStr) {
        this.editorsStr = editorsStr;
        String[] editordids = editorsStr.split(",");
        this.editors = TargetLogic.get().selectTargets(editordids);
    }
    public void setTags(String tagsStr) {
        this.tagsStr = tagsStr;
        List<TagsEntity> tags = KnowledgeLogic.get().manegeTags(tagsStr);
        this.tags = tags;
    }
    public void setFileNos(String[] filesStrs) {
        this.filesStrs = filesStrs;
        this.fileNos = new ArrayList<Long>();
        if (filesStrs != null) {
            for (String string : filesStrs) {
                if (StringUtils.isLong(string)) {
                    this.fileNos.add(new Long(string));
                }
            }
        }
    }
    
    
    
    
    
    
    /**
     * Get knowledge
     * @return the knowledge
     */
    public KnowledgesEntity getKnowledge() {
        return knowledge;
    }
    /**
     * Set knowledge
     * @param knowledge the knowledge to set
     */
    public void setKnowledge(KnowledgesEntity knowledge) {
        this.knowledge = knowledge;
    }
    /**
     * Get tags
     * @return the tags
     */
    public List<TagsEntity> getTags() {
        return tags;
    }
    /**
     * Set tags
     * @param tags the tags to set
     */
    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }
    /**
     * Get viewers
     * @return the viewers
     */
    public List<LabelValue> getViewers() {
        return viewers;
    }
    /**
     * Set viewers
     * @param viewers the viewers to set
     */
    public void setViewers(List<LabelValue> viewers) {
        this.viewers = viewers;
    }
    /**
     * Get editors
     * @return the editors
     */
    public List<LabelValue> getEditors() {
        return editors;
    }
    /**
     * Set editors
     * @param editors the editors to set
     */
    public void setEditors(List<LabelValue> editors) {
        this.editors = editors;
    }
    /**
     * Get fileNos
     * @return the fileNos
     */
    public List<Long> getFileNos() {
        return fileNos;
    }
    /**
     * Set fileNos
     * @param fileNos the fileNos to set
     */
    public void setFileNos(List<Long> fileNos) {
        this.fileNos = fileNos;
    }
    /**
     * Get template
     * @return the template
     */
    public TemplateMastersEntity getTemplate() {
        return template;
    }
    /**
     * Set template
     * @param template the template to set
     */
    public void setTemplate(TemplateMastersEntity template) {
        this.template = template;
    }
    /**
     * Get draftId
     * @return the draftId
     */
    public Long getDraftId() {
        return draftId;
    }
    /**
     * Set draftId
     * @param draftId the draftId to set
     */
    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }
    /**
     * Get viewersStr
     * @return the viewersStr
     */
    public String getViewersStr() {
        return viewersStr;
    }
    /**
     * Set viewersStr
     * @param viewersStr the viewersStr to set
     */
    public void setViewersStr(String viewersStr) {
        this.viewersStr = viewersStr;
    }
    /**
     * Get editorsStr
     * @return the editorsStr
     */
    public String getEditorsStr() {
        return editorsStr;
    }
    /**
     * Set editorsStr
     * @param editorsStr the editorsStr to set
     */
    public void setEditorsStr(String editorsStr) {
        this.editorsStr = editorsStr;
    }
    /**
     * Get tagsStr
     * @return the tagsStr
     */
    public String getTagsStr() {
        return tagsStr;
    }
    /**
     * Set tagsStr
     * @param tagsStr the tagsStr to set
     */
    public void setTagsStr(String tagsStr) {
        this.setTags(tagsStr);
    }
    /**
     * Get filesStrs
     * @return the filesStrs
     */
    public String[] getFilesStrs() {
        return filesStrs;
    }
    /**
     * Set filesStrs
     * @param filesStrs the filesStrs to set
     */
    public void setFilesStrs(String[] filesStrs) {
        this.filesStrs = filesStrs;
    }


    /**
     * Get updateContent
     * @return the updateContent
     */
    public boolean isUpdateContent() {
        return updateContent;
    }


    /**
     * Set updateContent
     * @param updateContent the updateContent to set
     */
    public void setUpdateContent(boolean updateContent) {
        this.updateContent = updateContent;
    }


    public boolean isNotifyUpdate() {
        return notifyUpdate;
    }


    public void setNotifyUpdate(boolean notifyUpdate) {
        this.notifyUpdate = notifyUpdate;
    }


    public boolean isDonotUpdateTimeline() {
        return donotUpdateTimeline;
    }


    public void setDonotUpdateTimeline(boolean donotUpdateTimeline) {
        this.donotUpdateTimeline = donotUpdateTimeline;
    }
    
    

}
