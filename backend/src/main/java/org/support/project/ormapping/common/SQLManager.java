package org.support.project.ormapping.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * SQLのリソースを読み込む
 */
public class SQLManager {
    /** ログ */
    private static Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * シングルトンで管理されたconnectionManagerのインスタンス
     */
    private static SQLManager sqlManager = null;

    /** SQLを保持するマップ */
    private Map<String, String[]> sqlMap = null;

    /**
     * コンストラクタ
     * 
     * @throws Exception
     */
    private SQLManager() {
        sqlMap = new HashMap<String, String[]>();
    }

    /**
     * インスタンスの取得
     * 
     * @return ConnectionManagerのインスタンス
     */
    public static SQLManager getInstance() {
        if (sqlManager == null) {
            sqlManager = new SQLManager();
        }
        return sqlManager;
    }
    /**
     * Get sql string on file path.
     * @param sqlFilePath path of sql file.
     * @return SQL string
     */
    public String getSql(String sqlFilePath) {
        String[] sqls = getSqls(sqlFilePath);
        if (sqls.length > 0) {
            return sqls[0];
        }
        return null;
    }

    /**
     * キーでSQLを取得
     * @param sqlFilePath path
     * @return sql strings
     * 
     */
    public String[] getSqls(String sqlFilePath) {
        List<String> sqlFilePaths = getSqlFilePaths(sqlFilePath);
        LOG.trace("load sql");
        for (String path : sqlFilePaths) {
            LOG.trace(path);
            if (this.getClass().getResourceAsStream(path) != null) {
                return readSqls(path);
            }
        }
        throw new ORMappingException("SQL Resource is not found. " + sqlFilePath);
    }
    
    private List<String> getSqlFilePaths(String sqlFilePath) {
        List<String> paths = new ArrayList<>();
        // 現在のコネクションを取得
        String driver = ConnectionManager.getInstance().getDriverClass();
        if (!StringUtils.isEmpty(driver)) {
            if (driver.indexOf("postgresql") != -1) {
                paths.add(connectDatabaseToSqlFileName(sqlFilePath, "postgresql"));
            } else if (driver.indexOf("h2") != -1) {
                paths.add(connectDatabaseToSqlFileName(sqlFilePath, "h2"));
            }
        }
        paths.add(sqlFilePath);
        return paths;
    }
    
    private String connectDatabaseToSqlFileName(String sqlFilePath, String databasename) {
        StringBuilder path = new StringBuilder();
        if (sqlFilePath.indexOf(".") != -1) {
            path.append(sqlFilePath.substring(0, sqlFilePath.lastIndexOf(".")));
            path.append("_");
            path.append(databasename);
            path.append(sqlFilePath.substring(sqlFilePath.lastIndexOf(".")));
        } else {
            path.append(sqlFilePath);
            path.append("_");
            path.append(databasename);
        }
        return path.toString();
    }

    private String[] readSqls(String sqlFilePath) {
        try {
            if (sqlMap.containsKey(sqlFilePath)) {
                return sqlMap.get(sqlFilePath);
            }
            BufferedReader bufferedReader = null;

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(sqlFilePath), "UTF-8"));

                List<String> sqls = new ArrayList<String>();
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.startsWith("--")) {
                        if (line.indexOf(";") != -1) {
                            String[] sp = line.split(";");
                            for (String string : sp) {
                                if (string != null && sp.length > 0) {
                                    buffer.append(string);
                                    sqls.add(buffer.toString());
                                    buffer.delete(0, buffer.length());
                                }
                            }
                        } else {
                            buffer.append(line.trim());
                        }
                        buffer.append(" ");
                    }
                }
                if (buffer.length() > 0) {
                    sqls.add(buffer.toString());
                }

                String[] arry = (String[]) sqls.toArray(new String[0]);
                sqlMap.put(sqlFilePath, arry);
                return arry;
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException e) {
            throw new ORMappingException(e);
        }
    }
    
    

}
