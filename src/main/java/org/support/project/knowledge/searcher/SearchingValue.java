package org.support.project.knowledge.searcher;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.control.open.KnowledgeControl;
import org.support.project.knowledge.indexer.IndexingValue;

public class SearchingValue {
    public static final int ID_ZEROPADDING_DIGIT = IndexingValue.ID_ZEROPADDING_DIGIT;
    public static final String SEPARATE = IndexingValue.SEPARATE;

    public static final int LIMIT = KnowledgeControl.PAGE_LIMIT;

    /** データのタイプ */
    private Integer type;

    /** 検索のキーワード */
    private String keyword;

    /** タグの文字列(AND条件) */
    private List<Integer> tags = new ArrayList<Integer>();

    /** アクセス可能なユーザ（ログインユーザのユーザIDの + 公開先ユーザ指定) (OR条件)*/
    private List<Integer> users = new ArrayList<Integer>();
    /** アクセス可能なグループ(ログインユーザの所属しているグループ + 公開先グループ指定) (OR条件) */
    private List<Integer> groups = new ArrayList<Integer>();

    /** テンプレート(OR条件) */
    private List<Integer> templates = new ArrayList<Integer>();

    /** 作成者(OR条件) */
    private List<Integer> creators = new ArrayList<Integer>();

    /** 読み出し開始 */
    private int offset;
    /** 読み出し件数 */
    private int limit = LIMIT;


    /**
     * コンストラクタ
     */
    public SearchingValue() {
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
     * @param creator the creator to set
     */
    public void addCreator(Integer creator) {
        this.creators.add(creator);
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
    public String getCreators() {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Integer creator : creators) {
            if (count > 0) {
                builder.append(SEPARATE);
            }
            builder.append(StringUtils.zeroPadding(creator, ID_ZEROPADDING_DIGIT));
            count++;
        }
        return builder.toString();
    }

    /**
     * Get templates
     * @return templates
     */
    public List<Integer> getTemplates() {
        return templates;
    }

    /**
     * Add template
     * @param templateId template
     */
    public void addTemplate(Integer templateId) {
        this.templates.add(templateId);
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }


}
