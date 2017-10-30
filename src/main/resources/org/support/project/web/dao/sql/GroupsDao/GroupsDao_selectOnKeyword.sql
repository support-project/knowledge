SELECT
        GROUPS.*
    FROM
        GROUPS
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
        GROUPS.GROUP_ID
    LIMIT ? OFFSET ?;

