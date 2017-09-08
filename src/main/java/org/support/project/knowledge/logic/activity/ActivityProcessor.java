package org.support.project.knowledge.logic.activity;

public interface ActivityProcessor {
    public static final int TYPE_KNOWLEDGE_DO_INSERT = 11;
    public static final int TYPE_KNOWLEDGE_INSERTED = 13;
    public static final int TYPE_KNOWLEDGE_DO_SHOW = 21;
    public static final int TYPE_KNOWLEDGE_SHOWN_BY_OHER = 22;
    public static final int TYPE_KNOWLEDGE_SHOWN = 23;
    public static final int TYPE_KNOWLEDGE_DO_LIKE = 31;
    public static final int TYPE_KNOWLEDGE_LIKED_BY_OHER = 32;
    public static final int TYPE_KNOWLEDGE_LIKED = 33;
    public static final int TYPE_KNOWLEDGE_DO_STOCK = 41;
    public static final int TYPE_KNOWLEDGE_STOCKED_BY_OHER = 42;
    public static final int TYPE_KNOWLEDGE_STOCKED = 43;
    public static final int TYPE_KNOWLEDGE_DO_ANSWER = 51;
    public static final int TYPE_KNOWLEDGE_ANSWERD_BY_OHER = 52;
    public static final int TYPE_KNOWLEDGE_ANSWERD = 53;
    public static final int TYPE_KNOWLEDGE_DO_JOIN_EVENT = 61;
    public static final int TYPE_KNOWLEDGE_JOINED_BY_OHER = 62;
    public static final int TYPE_KNOWLEDGE_JOINED = 63;
    
    public static final int TYPE_KNOWLEDGE_DO_POST_PUBLIC = 101;
    public static final int TYPE_KNOWLEDGE_POSTED_PUBLIC = 103;
    public static final int TYPE_KNOWLEDGE_DO_POST_PROTECT = 111;
    public static final int TYPE_KNOWLEDGE_POSTED_PROTECT = 113;
    public static final int TYPE_KNOWLEDGE_DO_POST_PRIVATE = 121;
    public static final int TYPE_KNOWLEDGE_POSTED_PRIVATE = 123;
    
    public static final int TYPE_COMMENT_DO_INSERT = 1011;
    public static final int TYPE_COMMENT_INSERTED = 1013;
    public static final int TYPE_COMMENT_DO_LIKE = 1031;
    public static final int TYPE_COMMENT_LIKED_BY_OHER = 1032;
    public static final int TYPE_COMMENT_LIKED = 1033;
    
    void execute() throws Exception;
}
