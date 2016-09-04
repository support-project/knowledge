package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
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
    public List<KnowledgeFilesEntity> selectOnKnowledgeId(Long knowledgeId) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, FILE_NAME, FILE_SIZE, PARSE_STATUS, "
                + "INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE KNOWLEDGE_ID = ?;");
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class, knowledgeId);
    }

    /**
     * キーで1件取得 （ファイルのバイナリは取得しない）
     */
    public KnowledgeFilesEntity selectOnKeyWithoutBinary(Long fileNo) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, FILE_NAME, FILE_SIZE, PARSE_STATUS "
                + "INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
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
    public void connectKnowledge(Long fileNo, Long knowledgeId, Long commentNo, LoginedUser loginedUser) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE KNOWLEDGE_FILES ");
        sql.append("SET KNOWLEDGE_ID = ?, UPDATE_USER = ?, UPDATE_DATETIME = ?, COMMENT_NO = ? ");
        sql.append("WHERE FILE_NO = ? ");
        executeUpdate(sql.toString(), knowledgeId, loginedUser.getUserId(), new Timestamp(new Date().getTime()), commentNo, fileNo);
    }

    /**
     * ナレッジに紐づいていないファイルで、かつ更新日が24時間前のものは削除する
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int deleteNotConnectFiles() {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM KNOWLEDGE_FILES WHERE KNOWLEDGE_ID IS NULL AND UPDATE_DATETIME < ? ");
        Timestamp timestamp = new Timestamp(new Date().getTime() - (1000 * 60 * 60 * 24));
        int count = executeUpdate(sql.toString(), timestamp);
        return count;
    }

    /**
     * パース待ちの状態にあるファイルを取得 (バイナリは取得しない)
     * 
     * @return
     */
    public List<KnowledgeFilesEntity> selectWaitStateFiles() {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT FILE_NO, KNOWLEDGE_ID, COMMENT_NO, FILE_NAME, FILE_SIZE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ");
        sql.append("FROM KNOWLEDGE_FILES WHERE PARSE_STATUS = 0 AND KNOWLEDGE_ID IS NOT NULL");
        return executeQueryList(sql.toString(), KnowledgeFilesEntity.class);
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
        executeUpdate(sql.toString(), parseStatus, updateUserId, new Timestamp(new Date().getTime()), fileNo);
    }

}
