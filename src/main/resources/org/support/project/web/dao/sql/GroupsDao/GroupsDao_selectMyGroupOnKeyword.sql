SELECT
    *
FROM
    GROUPS
WHERE
    GROUPS.GROUP_NAME LIKE '%' || ? || '%'
    AND
    GROUPS.DELETE_FLAG = 0
    AND
    EXISTS (
        SELECT
            *
        FROM
            USER_GROUPS
        WHERE
            USER_GROUPS.GROUP_ID = GROUPS.GROUP_ID
            AND
            USER_GROUPS.USER_ID = ?
            AND
            USER_GROUPS.GROUP_ROLE >= 1
    )
    GROUP BY
        GROUPS.GROUP_ID
    ORDER BY
        GROUPS.GROUP_NAME
    LIMIT ? OFFSET ?
;