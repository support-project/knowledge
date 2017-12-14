package org.support.project.knowledge.entity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgesEntity;

/**
 * ナレッジ
 */
@DI(instance = Instance.Prototype)
public class KnowledgesEntity extends GenKnowledgesEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgesEntity get() {
        return Container.getComp(KnowledgesEntity.class);
    }

    /**
     * 登録者の名称
     */
    private String insertUserName;
    /**
     * 更新者の名称
     */
    private String updateUserName;

    /** スコア(検索した際のスコア) */
    private Float score;
    /** 指定期間内のイイネ件数 */
    private Integer pointOnTerm;

    /** 開催日     UTC(Eventの場合のみ） */
    private Timestamp startDateTime;
    
    /**
     * コンストラクタ
     */
    public KnowledgesEntity() {
        super();
        // 初期値
        this.setNotifyStatus(0);
        this.setDeleteFlag(0);
    }

    /**
     * コンストラクタ
     * 
     * @param knowledgeId ナレッジID
     */

    public KnowledgesEntity(Long knowledgeId) {
        super(knowledgeId);
    }
    
    /**
     * Get 参照件数.(Safe null)
     * @return 参照件数
     */
    public Long getViewCount() {
        if (super.getViewCount() == null) return new Long(0);
        return super.getViewCount();
    }
   
    
    /**
     * 開始日時を指定のタイムゾーンの日時として取得
     * @param locale
     * @param timezone
     * @return
     */
    public String getLocalStartDateTime(Locale locale, String timezone) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        TimeZone tz = TimeZone.getTimeZone(timezone);
        format.setTimeZone(tz);
        return format.format(startDateTime);
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getTitle()
     */
    @Override
    public String getTitle() {
        if (StringUtils.isEmpty(super.getTitle())) {
            return super.getTitle();
        }
        // try {
        // return Control.doSamy(super.getTitle());
        // } catch (PolicyException | ScanException e) {
        // throw new SystemException(e);
        // }
        return super.getTitle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getContent()
     */
    @Override
    public String getContent() {
        if (StringUtils.isEmpty(super.getContent())) {
            return super.getContent();
        }
        // try {
        // return Control.doSamy(super.getContent());
        // } catch (PolicyException | ScanException e) {
        // throw new SystemException(e);
        // }
        return super.getContent();
    }

    /**
     * @return the insertUserName
     */
    public String getInsertUserName() {
        return insertUserName;
    }

    /**
     * @param insertUserName the insertUserName to set
     */
    public void setInsertUserName(String insertUserName) {
        this.insertUserName = insertUserName;
    }

    /**
     * @return the updateUserName
     */
    public String getUpdateUserName() {
        return updateUserName;
    }

    /**
     * @param updateUserName the updateUserName to set
     */
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    /**
     * @return the score
     */
    public Float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * @return the startDateTime
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * @param startDateTime the startDateTime to set
     */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @return the pointOnTerm
     */
    public Integer getPointOnTerm() {
        return pointOnTerm;
    }

    /**
     * @param pointOnTerm the pointOnTerm to set
     */
    public void setPointOnTerm(Integer pointOnTerm) {
        this.pointOnTerm = pointOnTerm;
    }

}
