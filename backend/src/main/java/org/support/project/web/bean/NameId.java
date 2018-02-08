package org.support.project.web.bean;

public class NameId {
    /** Name */
    private String name = "";
    /** ID */
    private String id = "";
    public NameId() {
        super();
    }
    public NameId(String name, String id) {
        super();
        this.name = name;
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

}
