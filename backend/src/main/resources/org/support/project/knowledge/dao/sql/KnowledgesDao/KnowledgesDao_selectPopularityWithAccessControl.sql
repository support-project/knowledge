SELECT
        KNOWLEDGES.*
        ,USERS.USER_NAME AS INSERT_USER_NAME
        ,UP_USER.USER_NAME AS UPDATE_USER_NAME
        ,COALESCE(SUM(POINT_KNOWLEDGE_HISTORIES.POINT), 0) AS POINT_ON_TERM
    FROM
        KNOWLEDGES
            LEFT OUTER JOIN POINT_KNOWLEDGE_HISTORIES
                ON (
                    KNOWLEDGES.KNOWLEDGE_ID = POINT_KNOWLEDGE_HISTORIES.KNOWLEDGE_ID
                    AND POINT_KNOWLEDGE_HISTORIES.INSERT_DATETIME BETWEEN ? AND ?
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
        POINT_ON_TERM DESC
        ,KNOWLEDGES.POINT DESC
        ,KNOWLEDGES.UPDATE_DATETIME DESC 
    LIMIT ? OFFSET ?;
