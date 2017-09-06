SELECT
        YMD
        ,MAX(TOTAL) AS TOTAL
    FROM
        (
            SELECT
                    TO_CHAR(TIMESTAMPADD(
                        'MINUTE'
                        ,?
                        ,INSERT_DATETIME
                    ), 'YYYYMMDD') AS YMD
                    ,TOTAL
                FROM
                    POINT_USER_HISTORIES
                WHERE
                    USER_ID = ?
                    AND INSERT_DATETIME > DATEADD(
                        'DAY'
                        ,-32
                        ,NOW()
                    )
        ) AS SUBQ
    GROUP BY
        YMD
    ORDER BY
        YMD;
