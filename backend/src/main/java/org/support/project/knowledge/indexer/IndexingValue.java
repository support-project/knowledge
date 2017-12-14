package org.support.project.knowledge.indexer;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringUtils;

public class IndexingValue {
    public static final int ID_ZEROPADDING_DIGIT = 8;
    public static final String SEPARATE = " ";

    /** データのタイプ */
    private Integer type;
    /** データの ID */
    private String id;

    /** コンテンツのタイトル */
    private String title;
    /** コンテンツの文字列 */
    private String contents;

    /** ユーザの文字列 */
    private List<Integer> users = new ArrayList<Integer>();
    /** グループの文字列 */
    private List<Integer> groups = new ArrayList<Integer>();
    /** タグの文字列 */
    private List<Integer> tags = new ArrayList<Integer>();

    /** 作成者 */
    private Integer creator;
    /** 作成日時（ソート用) */
    private Long time;
    
    /** テンプレート */
    private Integer template;
    

    /**
     * コンストラクタ
     */
    public IndexingValue() {
        super();
    }

    /**
     * @return the type
     */
    public void addUser(Integer user) {
        users.add(user);
    }

    /**
     * @return the type
     */
    public void addGroup(Integer group) {
        groups.add(group);
    }

    /**
     * @return the type
     */
    public void addTag(Integer tag) {
        tags.add(tag);
    }

    /**
     * @return the users
     */
    public String getUsers() {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Integer integer : users) {
            if (count > 0) {
                builder.append(SEPARATE);
            }
            builder.append(StringUtils.zeroPadding(integer, ID_ZEROPADDING_DIGIT));
            count++;
        }
        return builder.toString();
    }

    /**
     * @return the groups
     */
    public String getGroups() {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Integer integer : groups) {
            if (count > 0) {
                builder.append(SEPARATE);
            }
            builder.append(StringUtils.zeroPadding(integer, ID_ZEROPADDING_DIGIT));
            count++;
        }
        return builder.toString();
    }

    /**
     * @return the tags
     */
    public String getTags() {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Integer integer : tags) {
            if (count > 0) {
                builder.append(SEPARATE);
            }
            builder.append(StringUtils.zeroPadding(integer, ID_ZEROPADDING_DIGIT));
            count++;
        }
        return builder.toString();
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return StringUtils.zeroPadding(creator, ID_ZEROPADDING_DIGIT);
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
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * @return the time
     */
    public Long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @return the template
     */
    public Integer getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(Integer template) {
        this.template = template;
    }

}
