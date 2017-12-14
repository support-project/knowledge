SELECT
        KNOWLEDGES.*
        ,USERS.USER_NAME AS INSERT_USER_NAME
        ,UP_USER.USER_NAME AS UPDATE_USER_NAME
        ,STOCK_KNOWLEDGE.STOCK_TIME
    FROM
        KNOWLEDGES INNER JOIN (
            SELECT
                    KNOWLEDGES.KNOWLEDGE_ID
                    ,MAX(STOCK_KNOWLEDGES.UPDATE_DATETIME) AS STOCK_TIME
                FROM
                    KNOWLEDGES INNER JOIN STOCK_KNOWLEDGES
                        ON (KNOWLEDGES.KNOWLEDGE_ID = STOCK_KNOWLEDGES.KNOWLEDGE_ID)
            WHERE
                STOCK_KNOWLEDGES.INSERT_USER = ?
                AND KNOWLEDGES.DELETE_FLAG = 0
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
                (KNOWLEDGES.KNOWLEDGE_ID)
            ORDER BY
                STOCK_TIME DESC LIMIT ? OFFSET ?
        ) AS STOCK_KNOWLEDGE
            ON KNOWLEDGES.KNOWLEDGE_ID = STOCK_KNOWLEDGE.KNOWLEDGE_ID
        LEFT OUTER JOIN USERS
            ON USERS.USER_ID = KNOWLEDGES.INSERT_USER
        LEFT OUTER JOIN USERS AS UP_USER
            ON UP_USER.USER_ID = KNOWLEDGES.UPDATE_USER
ORDER BY
    STOCK_KNOWLEDGE.STOCK_TIME DESC;
