SELECT
        VALUE
        ,LABEL
    FROM
        (
            SELECT
                    'U-' || USERS.USER_ID AS VALUE
                    ,USERS.USER_NAME AS LABEL
                FROM
                    USERS
                WHERE
                    USERS.DELETE_FLAG != 1
                    AND USERS.USER_NAME ILIKE '%' || ? || '%'
            UNION
            SELECT
                    'G-' || GROUPS.GROUP_ID AS VALUE
                    ,GROUPS.GROUP_NAME AS LABEL
                FROM
                    GROUPS
                WHERE
                    GROUPS.DELETE_FLAG != 1
                    AND GROUPS.GROUP_NAME ILIKE '%' || ? || '%'
        ) AS TARGETS
    ORDER BY
        LABEL
    LIMIT ? OFFSET ?


