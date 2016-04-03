SELECT
        KNOWLEDGES.*
        ,USERS.USER_NAME AS INSERT_USER_NAME
        ,UP_USER.USER_NAME AS UPDATE_USER_NAME
        ,COUNT(LIKES.NO) AS LIKE_COUNT_ON_TERM
    FROM
        KNOWLEDGES
            LEFT OUTER JOIN LIKES
                ON (
                    KNOWLEDGES.KNOWLEDGE_ID = LIKES.KNOWLEDGE_ID
                    AND LIKES.INSERT_DATETIME BETWEEN ? AND ?
                )
            LEFT OUTER JOIN USERS
                ON USERS.USER_ID = KNOWLEDGES.INSERT_USER
            LEFT OUTER JOIN USERS AS UP_USER
                ON UP_USER.USER_ID = KNOWLEDGES.UPDATE_USER
    WHERE
        KNOWLEDGES.DELETE_FLAG = 0
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
    GROUP BY
        KNOWLEDGES.KNOWLEDGE_ID, USERS.USER_NAME, UP_USER.USER_NAME
    ORDER BY
        COUNT(LIKES.NO) DESC
        ,KNOWLEDGES.UPDATE_DATETIME DESC 
    LIMIT ? OFFSET ?;
