SELECT
        YMD
        ,MAX(TOTAL) AS TOTAL
    FROM
        (
            SELECT
                    TO_CHAR(INSERT_DATETIME + CAST (? AS INTERVAL), 'YYYYMMDD') AS YMD
                    ,TOTAL
                FROM
                    POINT_USER_HISTORIES
                WHERE
                    USER_ID = ?
                    AND INSERT_DATETIME > NOW() + '-32 DAYS'
        ) AS SUBQ
    GROUP BY
        YMD
    ORDER BY
        YMD;
