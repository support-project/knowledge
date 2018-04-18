package org.support.project.knowledge.vo.api;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.vo.Participations;
import org.support.project.web.bean.LabelValue;

/**
 * 記事の情報をAPIでやりとりする型を定義
 * 
 * @author koda
 */
public class Knowledge extends KnowledgesEntity {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    /** 編集可能かどうか */
    private boolean editable = false;
    /** Markdownを表示用に変換したHTML(サニタイズ済で直接描画しても問題無いはず） */
    private String displaySafeHtml;

    /** 下書きID */
    private Long draftId;

    /** タグ */
    private List<String> tags = new ArrayList<>();
    
    /** 記事の種類 */
    private Type type;
    /** テンプレートの項目値 */
    private List<LabelValue> items = new ArrayList<>();
    
    /** 閲覧可能な対象（publicflag=2(保護)の場合に指定） */
    private Targets viewers;
    /** 編集可能な対象（共同編集者） */
    private Targets editors;
    
    /** この記事をストックしている場合のストック情報 */
    private List<StocksEntity> stocks = new ArrayList<>();
    
    /** コメント */
    private List<Comment> comments = new ArrayList<>();
    /** 添付ファイル */
    private List<AttachedFile> attachments = new ArrayList<>();
    
    
    /** 検索結果のデータのタイプ(コメント？添付ファイル？） */
    private Integer searchItemType;
    /** データの ID（KnowledgeId + commentId + attachId) */
    private String id;
    /** ハイライト（表示用文字） */
    private String highlightedTitle = null;
    /** ハイライト（表示用文字） */
    private String highlightedContents = null;
    
    /** イベントの場合の参加者情報 */
    private Participations participations;
    /** 取得したユーザが、この記事を表示したことがあるかどうか */
    private boolean viewed = false;
    
    /** 通知を送らない */
    private Boolean ignoreNotification = false;

    
    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public String getDisplaySafeHtml() {
        return displaySafeHtml;
    }
    public void setDisplaySafeHtml(String displaySafeHtml) {
        this.displaySafeHtml = displaySafeHtml;
    }
    public Long getDraftId() {
        return draftId;
    }
    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public List<LabelValue> getItems() {
        return items;
    }
    public void setItems(List<LabelValue> items) {
        this.items = items;
    }
    public Targets getViewers() {
        return viewers;
    }
    public void setViewers(Targets viewers) {
        this.viewers = viewers;
    }
    public Targets getEditors() {
        return editors;
    }
    public void setEditors(Targets editors) {
        this.editors = editors;
    }
    public List<StocksEntity> getStocks() {
        return stocks;
    }
    public void setStocks(List<StocksEntity> stocks) {
        this.stocks = stocks;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public List<AttachedFile> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<AttachedFile> attachments) {
        this.attachments = attachments;
    }
    public Integer getSearchItemType() {
        return searchItemType;
    }
    public void setSearchItemType(Integer searchItemType) {
        this.searchItemType = searchItemType;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHighlightedTitle() {
        return highlightedTitle;
    }
    public void setHighlightedTitle(String highlightedTitle) {
        this.highlightedTitle = highlightedTitle;
    }
    public String getHighlightedContents() {
        return highlightedContents;
    }
    public void setHighlightedContents(String highlightedContents) {
        this.highlightedContents = highlightedContents;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
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
    public Boolean getIgnoreNotification() {
        return ignoreNotification;
    }
    public void setIgnoreNotification(Boolean ignoreNotification) {
        this.ignoreNotification = ignoreNotification;
    }
    


}
