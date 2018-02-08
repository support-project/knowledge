SELECT
        KNOWLEDGES.*
        ,USERS.USER_NAME AS INSERT_USER_NAME
        ,UP_USER.USER_NAME AS UPDATE_USER_NAME
        ,EVENTS.START_DATE_TIME AS START_DATE_TIME
    FROM
        KNOWLEDGES
        INNER JOIN EVENTS ON EVENTS.KNOWLEDGE_ID = KNOWLEDGES.KNOWLEDGE_ID
            LEFT OUTER JOIN USERS
                ON USERS.USER_ID = KNOWLEDGES.INSERT_USER
            LEFT OUTER JOIN USERS AS UP_USER
                ON UP_USER.USER_ID = KNOWLEDGES.UPDATE_USER
    WHERE
        EVENTS.START_DATE_TIME >= ?
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
ORDER BY 
        EVENTS.START_DATE_TIME ASC 
LIMIT ? OFFSET ?

