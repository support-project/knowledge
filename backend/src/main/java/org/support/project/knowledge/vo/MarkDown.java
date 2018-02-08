package org.support.project.knowledge.vo;

public class MarkDown {

    private String markdown;

    private String html;

    private boolean parsed = false;

    /**
     * @return the markdown
     */
    public String getMarkdown() {
        return markdown;
    }

    /**
     * @param markdown the markdown to set
     */
    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    /**
     * @return the html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html the html to set
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * @return the parsed
     */
    public boolean isParsed() {
        return parsed;
    }

    /**
     * @param parsed the parsed to set
     */
    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

}
