SELECT
        USERS.*
    FROM
        USERS 
        INNER JOIN USER_ROLES
            ON USERS.USER_ID = USER_ROLES.USER_ID
        INNER JOIN ROLES
            ON USER_ROLES.ROLE_ID = ROLES.ROLE_ID
WHERE
    ROLES.ROLE_KEY = ?
    AND USERS.DELETE_FLAG = 0
    
    
    