SELECT * FROM EVENTS
WHERE DELETE_FLAG = 0
ORDER BY INSERT_DATETIME %s
LIMIT ? OFFSET ?;
