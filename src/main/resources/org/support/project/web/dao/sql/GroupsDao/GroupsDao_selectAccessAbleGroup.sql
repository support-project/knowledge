SELECT
        *
    FROM
        GROUPS
    WHERE
        DELETE_FLAG = 0
        AND GROUP_ID = ?
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

