package org.support.project.knowledge.searcher;

public class SearchResultValue {

    /** データのタイプ */
    private Integer type;
    /** データの ID */
    private String id;

    /** コンテンツのタイトル */
    private String title;
    /** コンテンツの文字列 */
    private String contents;
    /** スコア */
    private float score;
    /** ハイライト（表示用文字） */
    private String highlightedTitle = null;
    /** ハイライト（表示用文字） */
    private String highlightedContents = null;

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @return the highlightedTitle
     */
    public String getHighlightedTitle() {
        return highlightedTitle;
    }

    /**
     * @param highlightedTitle the highlightedTitle to set
     */
    public void setHighlightedTitle(String highlightedTitle) {
        this.highlightedTitle = highlightedTitle;
    }

    /**
     * @return the highlightedContents
     */
    public String getHighlightedContents() {
        return highlightedContents;
    }

    /**
     * @param highlightedContents the highlightedContents to set
     */
    public void setHighlightedContents(String highlightedContents) {
        this.highlightedContents = highlightedContents;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
