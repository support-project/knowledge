package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.bat.FileParseBat;
import org.support.project.knowledge.dao.gen.GenKnowledgeFilesDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.web.bean.LoginedUser;

/**
 * 添付ファイル
 */
@DI(instance = Instance.Singleton)
public class KnowledgeFilesDao extends GenKnowledgeFilesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeFilesDao get() {
        return Container.getComp(KnowledgeFilesDao.class);
    }

    /**
     * ナレッジIDで添付ファイルを取得 （ファイルのバイナリは取得しない）
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeFilesEntity> selectOnKnowledgeId(Long knowledgeId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, DRAFT_ID, FILE_NAME, FILE_SIZE, PARSE_STATUS, ");
        sql.append("INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE KNOWLEDGE_ID = ?;");
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class, knowledgeId);
    }
    
    /**
     * 下書きに紐づく添付ファイルを取得
     * @param draftId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeFilesEntity> selectOnDraftId(Long draftId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, DRAFT_ID, FILE_NAME, FILE_SIZE, PARSE_STATUS, ");
        sql.append("INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE DRAFT_ID = ?;");
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class, draftId);
    }
    
    
    
    /**
     * キーで1件取得 （ファイルのバイナリは取得しない）
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeFilesEntity selectOnKeyWithoutBinary(Long fileNo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, DRAFT_ID, FILE_NAME, FILE_SIZE, PARSE_STATUS, ");
        sql.append("INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE FILE_NO = ?;");
        return executeQuerySingle(sql.toString(), KnowledgeFilesEntity.class, fileNo);
    }

    /**
     * 添付ファイルのナレッジIDをセット
     * 
     * @param fileNo
     * @param knowledgeId
     * @param commentNo Nullがありえる
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void connectKnowledge(Long fileNo, Long knowledgeId, Long commentNo, LoginedUser loginedUser) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE KNOWLEDGE_FILES ");
        sql.append("SET KNOWLEDGE_ID = ?, UPDATE_USER = ?, UPDATE_DATETIME = ?, COMMENT_NO = ? ");
        sql.append("WHERE FILE_NO = ? ");
        executeUpdate(sql.toString(), knowledgeId, loginedUser.getUserId(), new Timestamp(DateUtils.now().getTime()), commentNo, fileNo);
    }

    /**
     * ナレッジに紐づいていないファイルで、かつ更新日が24時間前のものを取得
     * 下書きに紐付いているものも対象外
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeFilesEntity> deleteNotConnectFiles() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FILE_NO FROM KNOWLEDGE_FILES WHERE KNOWLEDGE_ID IS NULL AND DRAFT_ID IS NULL AND UPDATE_DATETIME < ? ");
        Timestamp timestamp = new Timestamp(DateUtils.now().getTime() - (1000 * 60 * 60 * 24));
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class, timestamp);
    }

    /**
     * パース待ちの状態にあるファイルを取得 (バイナリは取得しない)
     * 
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeFilesEntity> selectWaitStateFiles() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, FILE_NAME, DRAFT_ID, FILE_SIZE, INSERT_USER, ");
        sql.append("INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE PARSE_STATUS < ?");
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class, FileParseBat.PARSE_STATUS_PARSING);
    }

    /**
     * パースのステータス更新
     * 
     * @param fileNo
     * @param parseStatus
     * @param updateUserId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void changeStatus(Long fileNo, int parseStatus, Integer updateUserId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE KNOWLEDGE_FILES ");
        sql.append("SET PARSE_STATUS = ?, UPDATE_USER = ?, UPDATE_DATETIME = ? ");
        sql.append("WHERE FILE_NO = ? ");
        executeUpdate(sql.toString(), parseStatus, updateUserId, new Timestamp(DateUtils.now().getTime()), fileNo);
    }

    /**
     * 下書きIDのセット
     * @param entity ファイルEntity
     * @param updateUserId 更新者
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void updateDraftId(KnowledgeFilesEntity entity, Integer updateUserId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE KNOWLEDGE_FILES ");
        sql.append("SET DRAFT_ID = ?, KNOWLEDGE_ID = ?, UPDATE_USER = ?, UPDATE_DATETIME = ? ");
        sql.append("WHERE FILE_NO = ? ");
        executeUpdate(sql.toString(), entity.getDraftId(), entity.getKnowledgeId(), 
                updateUserId, new Timestamp(DateUtils.now().getTime()), entity.getFileNo());
        
    }


}
