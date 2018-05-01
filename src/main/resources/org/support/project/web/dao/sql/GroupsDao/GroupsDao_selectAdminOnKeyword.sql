SELECT
        GROUPS.*
    FROM
        GROUPS
    WHERE
        GROUPS.DELETE_FLAG = 0
        AND
        GROUPS.GROUP_NAME ILIKE '%' || ? || '%'
    GROUP BY
        GROUPS.GROUP_ID
    ORDER BY
        GROUPS.GROUP_ID
    LIMIT ? OFFSET ?;

