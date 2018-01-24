DELETE FROM MAILS 
WHERE INSERT_DATETIME < DATEADD(
                        'MONTH'
                        ,-1
                        ,NOW()
                    )
AND (STATUS >= 100 OR DELETE_FLAG = 1);