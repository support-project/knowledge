SELECT
        USERS.*
    FROM
        USERS INNER JOIN CRAWLER_ACCESS_CONFIG
            ON (
                USERS.USER_ID = CRAWLER_ACCESS_CONFIG.ACCESS_ID
                AND CRAWLER_ACCESS_CONFIG.ID_TYPE = 0
                AND CRAWLER_ACCESS_CONFIG.CONFIG_ID = ?
            );

            
            
            