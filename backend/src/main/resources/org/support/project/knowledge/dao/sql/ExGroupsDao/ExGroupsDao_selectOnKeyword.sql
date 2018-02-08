SELECT
        GROUPS.*,
        COUNT(KNOWLEDGE_GROUPS.GROUP_ID) AS GROUP_KNOWLEDGE_COUNT
    FROM
        GROUPS
    LEFT JOIN
      KNOWLEDGE_GROUPS
    ON
      GROUPS.GROUP_ID = KNOWLEDGE_GROUPS.GROUP_ID
    WHERE
        GROUPS.DELETE_FLAG = 0
        AND GROUPS.GROUP_NAME ILIKE '%' || ? || '%'
        AND (
            EXISTS (
                SELECT
                        *
                    FROM
                        USER_GROUPS
                    WHERE
                        USER_GROUPS.GROUP_ID = GROUPS.GROUP_ID
                        AND USER_GROUPS.USER_ID = ?
            )
            OR EXISTS (
                SELECT
                        *
                    FROM
                        GROUPS G2
                    WHERE
                        G2.GROUP_ID = GROUPS.GROUP_ID
                        AND G2.GROUP_CLASS IN (
                            1
                            ,2
                        )
            )
        )
    GROUP BY
        GROUPS.GROUP_ID
    ORDER BY
        GROUP_KNOWLEDGE_COUNT DESC
    LIMIT ? OFFSET ?;

