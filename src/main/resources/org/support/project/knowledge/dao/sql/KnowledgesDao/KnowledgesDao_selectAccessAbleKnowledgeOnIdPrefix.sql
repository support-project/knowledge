SELECT
        KNOWLEDGES.KNOWLEDGE_ID,
        KNOWLEDGES.TITLE
    FROM
        KNOWLEDGES
    WHERE
        KNOWLEDGES.DELETE_FLAG = 0
        AND CAST(KNOWLEDGES.KNOWLEDGE_ID AS VARCHAR(20)) LIKE ? || '%'
        AND (
            KNOWLEDGES.PUBLIC_FLAG = 0
            OR (
                KNOWLEDGES.PUBLIC_FLAG = 1
                AND KNOWLEDGES.INSERT_USER = ?
            )
            OR (
                KNOWLEDGES.PUBLIC_FLAG = 2
                AND EXISTS (
                    SELECT
                            *
                        FROM
                            KNOWLEDGE_USERS
                        WHERE
                            KNOWLEDGES.KNOWLEDGE_ID = KNOWLEDGE_USERS.KNOWLEDGE_ID
                            AND KNOWLEDGE_USERS.USER_ID = ?
                )
            )
            OR (
                KNOWLEDGES.PUBLIC_FLAG = 2
                AND EXISTS (
                    SELECT
                            *
                        FROM
                            KNOWLEDGE_GROUPS
                        WHERE
                            KNOWLEDGES.KNOWLEDGE_ID = KNOWLEDGE_GROUPS.KNOWLEDGE_ID
                            AND KNOWLEDGE_GROUPS.GROUP_ID IN (%GROUPS%)
                )
            )
        )
    ORDER BY
        KNOWLEDGES.KNOWLEDGE_ID ASC 
    LIMIT ? OFFSET ?;
