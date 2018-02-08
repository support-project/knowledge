SELECT
        ROLES.*
    FROM
        ROLES
        INNER JOIN USER_ROLES
            ON USER_ROLES.ROLE_ID = ROLES.ROLE_ID INNER JOIN USERS
            ON USERS.User_id = USER_ROLES.User_id
WHERE
    USERS.USER_KEY = ?;
    
    