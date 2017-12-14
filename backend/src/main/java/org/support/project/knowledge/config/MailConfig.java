package org.support.project.knowledge.config;

public class MailConfig {

    private String templateTitle;
    private String description;
    
    private String title;
    private String contents;

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
     * @return the templateTitle
     */
    public String getTemplateTitle() {
        return templateTitle;
    }

    /**
     * @param templateTitle the templateTitle to set
     */
    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
