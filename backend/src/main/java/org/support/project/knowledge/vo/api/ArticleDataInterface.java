package org.support.project.knowledge.vo.api;

import java.sql.Timestamp;

public interface ArticleDataInterface {
    /**
     * Get ナレッジID.
     * @return ナレッジID
     */
    Long getKnowledgeId();
    /**
     * Get タイトル.
     * @return タイトル
     */
    String getTitle();
    /**
     * Get 内容.
     * @return 内容
     */
    String getContent();
    /**
     * Get 公開区分.
     * @return 公開区分
     */
    Integer getPublicFlag();
//    /**
//     * Get タグID一覧.
//     * @return タグID一覧
//     */
//    String getTagIds();
    /**
     * Get タグ名称一覧.
     * @return タグ名称一覧
     */
    String getTagNames();
//    /**
//     * Get いいね件数.
//     * @return いいね件数
//     */
//    Long getLikeCount();
//    /**
//     * Get コメント件数.
//     * @return コメント件数
//     */
//    Integer getCommentCount();
//    /**
//     * Get 参照件数.
//     * @return 参照件数
//     */
//    Long getViewCount();
    /**
     * Get テンプレートの種類ID.
     * @return テンプレートの種類ID
     */
    Integer getTypeId();
//    /**
//     * Get 通知ステータス.
//     * @return 通知ステータス
//     */
//    Integer getNotifyStatus();
//    /**
//     * Get ポイント.
//     * @return ポイント
//     */
//    Integer getPoint();
    /**
     * Get 登録ユーザ.
     * @return 登録ユーザ
     */
    Integer getInsertUser();
    /**
     * Get 登録日時.
     * @return 登録日時
     */
    Timestamp getInsertDatetime();
    /**
     * Get 更新ユーザ.
     * @return 更新ユーザ
     */
    Integer getUpdateUser();
    /**
     * Get 更新日時.
     * @return 更新日時
     */
    Timestamp getUpdateDatetime();
    /**
     * Get 削除フラグ.
     * @return 削除フラグ
     */
    Integer getDeleteFlag();
    
    
    
//    /**
//     * Set ナレッジID.
//     * @param knowledgeId ナレッジID
//     * @return this object     */
//    ArticleDataInterface setKnowledgeId(Long knowledgeId);
//    /**
//     * Set タイトル.
//     * @param title タイトル
//     * @return this object     */
//    ArticleDataInterface setTitle(String title);
//    /**
//     * Set 内容.
//     * @param content 内容
//     * @return this object     */
//    ArticleDataInterface setContent(String content);    /**
//     * Set 公開区分.
//     * @param publicFlag 公開区分
//     * @return this object     */
//    ArticleDataInterface setPublicFlag(Integer publicFlag);
//    /**
//     * Set タグID一覧.
//     * @param tagIds タグID一覧
//     * @return this object     */
//    ArticleDataInterface setTagIds(String tagIds);
//    /**
//     * Set タグ名称一覧.
//     * @param tagNames タグ名称一覧
//     * @return this object     */
//    ArticleDataInterface setTagNames(String tagNames);
//    /**
//     * Set いいね件数.
//     * @param likeCount いいね件数
//     * @return this object     */
//    ArticleDataInterface setLikeCount(Long likeCount);
//    /**
//     * Set コメント件数.
//     * @param commentCount コメント件数
//     * @return this object     */
//    ArticleDataInterface setCommentCount(Integer commentCount);
//    /**
//     * Set 参照件数.
//     * @param viewCount 参照件数
//     * @return this object     */
//    ArticleDataInterface setViewCount(Long viewCount);
//    /**
//     * Set テンプレートの種類ID.
//     * @param typeId テンプレートの種類ID
//     * @return this object     */
//    ArticleDataInterface setTypeId(Integer typeId);
//    /**
//     * Set 通知ステータス.
//     * @param notifyStatus 通知ステータス
//     * @return this object     */
//    ArticleDataInterface setNotifyStatus(Integer notifyStatus);
//    /**
//     * Set ポイント.
//     * @param point ポイント
//     * @return this object     */
//    ArticleDataInterface setPoint(Integer point);
//    /**
//     * Set 登録ユーザ.
//     * @param insertUser 登録ユーザ
//     * @return this object     */
//    ArticleDataInterface setInsertUser(Integer insertUser);
//    /**
//     * Set 登録日時.
//     * @param insertDatetime 登録日時
//     * @return this object     */
//    ArticleDataInterface setInsertDatetime(Timestamp insertDatetime);
//    /**
//     * Set 更新ユーザ.
//     * @param updateUser 更新ユーザ
//     * @return this object     */
//    ArticleDataInterface setUpdateUser(Integer updateUser);
//    /**
//     * Set 更新日時.
//     * @param updateDatetime 更新日時
//     * @return this object     */
//    ArticleDataInterface setUpdateDatetime(Timestamp updateDatetime);
//    /**
//     * Set 削除フラグ.
//     * @param deleteFlag 削除フラグ
//     * @return this object     */
//    ArticleDataInterface setDeleteFlag(Integer deleteFlag);

}
