package org.support.project.ormapping.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.common.ResultSetLoader;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.conv.DatabaseAccessType;
import org.support.project.ormapping.conv.ObjectToDatabaseConv;
import org.support.project.ormapping.conv.ObjectToDatabaseConvFactory;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * DAOの基底クラス
 * 
 * @author Koda
 * 
 */
public abstract class AbstractDao implements Serializable {
    /** ログ */
    private static Log logger = LogFactory.getLog(AbstractDao.class);

    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;

    // /** 初期化したか */
    // private boolean init = false;
    //
    /** コネクションの名称 */
    private String name;
    // /** コネクション設定のファイルタイプ */
    // private ConfigType configType;
    // /** コネクションの設定ファイル名称 */
    // private String configFileName;
    //
    // /** コネクションの設定 */
    // private ConnectionConfig connectionConfig;

    /**
     * コンストラクタ
     */
    public AbstractDao() {
        super();
        // init();
    }

    /**
     * コネクションの名称を切り替え
     * 
     * @param name config name
     */
    public void setConnectionName(String name) {
        this.name = name;
    }

    /**
     * コネクションの名称を取得
     * @return name
     */
    public String getConnectionName() {
        if (StringUtils.isEmpty(this.name)) {
            return ConnectionManager.getInstance().getDefaultConnectionName();
        }
        return this.name;
    }

    /**
     * コネクションを取得
     * 
     * @return Connection
     */
    protected Connection getConnection() {
        // if (!init) {
        // init();
        // }
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        // if (!connectionManager.isExist(name)) {
        // if (connectionConfig != null) {
        // connectionManager.addConnectionConfig(connectionConfig);
        // } else {
        // connectionManager.addConnectionConfig(name, configType, configFileName);
        // }
        // }
        if (!StringUtils.isEmpty(name)) {
            return connectionManager.getConnection(name);
        }
        return connectionManager.getConnection();
    }

    // /**
    // * 初期化処理(コネクションの設定を作成)
    // */
    // private void init() {
    // name = ORMappingParameter.DEFAULT_CONNECTION_NAME;
    // configType = ConfigType.XML;
    // configFileName = ORMappingParameter.CONNECTION_SETTING;
    //
    // org.support.project.ormapping.config.Connection connection =
    // this.getClass().getAnnotation(org.support.project.ormapping.config.Connection.class);
    // if (connection != null) {
    // //アノテーションが指定されていれば、それに従いコネクション設定を生成
    // if (StringUtils.isNotEmpty(connection.name())) {
    // name = connection.name();
    // }
    // if (connection.configType() != null) {
    // configType = connection.configType();
    // }
    // if (StringUtils.isNotEmpty(connection.configFileName())) {
    // configFileName = connection.configFileName();
    // }
    // }
    // init = true;
    // }

    // public void setConectionConfigParam(String name, ConfigType configType, String configFileName) {
    // this.name = name;
    // this.configType = configType;
    // this.configFileName = configFileName;
    // }
    //
    // /**
    // * @param connectionConfig セットする connectionConfig
    // */
    // public void setConnectionConfig(ConnectionConfig connectionConfig) {
    // this.connectionConfig = connectionConfig;
    // if (StringUtils.isNotEmpty(connectionConfig.getName())) {
    // this.name = connectionConfig.getName();
    // }
    // }

    /**
     * PreparedStatement, ResultSetをクローズ
     * 
     * @param stmt
     *            PreparedStatement
     * @param rs
     *            ResultSet
     */
    protected void close(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new ORMappingException(e);
        }
    }

    /**
     * PreparedStatement, ResultSet, Connectionをクローズ
     * 
     * @param conn
     *            Connection
     * @param stmt
     *            PreparedStatement
     * @param rs
     *            ResultSet
     */
    protected void close(PreparedStatement stmt, ResultSet rs, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new ORMappingException(e);
        }

    }

    /**
     * PreparedStatement, ResultSetをクローズ
     * 
     * @param stmt
     *            PreparedStatement
     * @param conn
     *            Connection
     */
    protected void close(PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new ORMappingException(e);
        }
    }

    /**
     * Connectionをクローズ
     * 
     * @param conn
     *            Connection
     */
    protected void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new ORMappingException(e);
        }
    }

    // /**
    // * データベースの情報をロードして、新たなオブジェクトを生成する
    // * @param rs データベースから取得した情報
    // * @return ロードしたオブジェクト
    // * @deprecated
    // */
    // protected <E> E load(ResultSet rs) {
    // GenericAnalysis<E> analysis = new GenericAnalysis<>();
    // return load(rs, analysis.getType());
    // }
    /**
     * データベースの情報から、指定のクラスのオブジェクトを生成し、そこにデータをロードする
     * 
     * @param rs
     *            データベースから取得した情報
     * @param class1
     *            ロードするクラス
     * @param <E> type
     * @return ロードしたオブジェクト
     */
    protected <E> E load(ResultSet rs, Class<? extends E> class1) {
        try {
            if (class1.equals(Integer.class)) {
                Integer result = rs.getInt(1);
                return (E) result;
            } else if (class1.equals(Double.class)) {
                Double result = rs.getDouble(1);
                return (E) result;
            } else if (class1.equals(Float.class)) {
                Float result = rs.getFloat(1);
                return (E) result;
            } else if (class1.equals(Long.class)) {
                Long result = rs.getLong(1);
                return (E) result;
            } else if (class1.equals(Short.class)) {
                Short result = rs.getShort(1);
                return (E) result;
            } else if (class1.equals(String.class)) {
                String result = rs.getString(1);
                return (E) result;
            } else if (class1.equals(BigDecimal.class)) {
                BigDecimal result = rs.getBigDecimal(1);
                return (E) result;
            } else if (class1.equals(Blob.class)) {
                Blob result = rs.getBlob(1);
                return (E) result;
            } else if (class1.equals(Boolean.class)) {
                Boolean result = rs.getBoolean(1);
                return (E) result;
            } else if (class1.equals(Byte.class)) {
                Byte result = rs.getByte(1);
                return (E) result;
            } else if (class1.equals(Timestamp.class)) {
                Timestamp result = rs.getTimestamp(1);
                return (E) result;
            } else if (class1.equals(Time.class)) {
                Time result = rs.getTime(1);
                return (E) result;
            } else if (class1.equals(Date.class)) {
                Date result = rs.getDate(1);
                return (E) result;
            } else if (class1.equals(java.util.Date.class)) {
                java.util.Date result = rs.getDate(1);
                return (E) result;
            }

            E object = class1.newInstance();
            load(rs, object);
            return object;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            throw new ORMappingException(e);
        }
    }

    /**
     * データベースから取得した行の情報をオブジェクトにロードする
     * 
     * @param rs
     *            データベースから取得した情報
     * @param object
     *            ロードするオブジェクト
     */
    protected void load(ResultSet rs, Object object) {
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        ResultSetLoader.load(rs, object, driverClass);
    }

    /**
     * 検索系のSQLの実行(1件取得)
     * 
     * @param sql
     * @param class1
     * @param params
     * @return
     */
    private <E> E executeQueryOnKey(String sql, Class<? extends E> class1, Object... params) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(sql);
            StringBuilder builder = new StringBuilder();
            builder.append("[connection]").append(con.hashCode());
            builder.append("[executeQuery]").append(sql).append("\n     ");
            if (params != null) {
                int count = 1;
                for (Object param : params) {
                    String result = setParam(stmt, param, count);
                    builder.append(result);
                    count++;
                }
            }
            logger.debug(builder.toString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                E obj = load(rs, class1);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            ORMappingException exception = new ORMappingException(e);
            exception.setSql(sql);
            exception.setParams(params);
            throw exception;
        } finally {
            close(stmt, rs, con);
        }
    }

    /**
     * 検索系のSQLの実行
     * 
     * @param sql
     * @param class1
     * @param params
     * @return
     */
    private <E> List<E> executeQuery(String sql, Class<? extends E> class1, Object... params) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(sql);
            StringBuilder builder = new StringBuilder();
            builder.append("[connection]").append(con.hashCode());
            builder.append("[executeQuery]").append(sql).append("\n     ");
            if (params != null) {
                int count = 1;
                for (Object param : params) {
                    String result = setParam(stmt, param, count);
                    builder.append(result);
                    count++;
                }
            }
            logger.debug(builder.toString());
            rs = stmt.executeQuery();

            List<E> list = new ArrayList<>();
            while (rs.next()) {
                E obj = load(rs, class1);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            ORMappingException exception = new ORMappingException(e);
            exception.setSql(sql);
            exception.setParams(params);
            throw exception;
        } finally {
            close(stmt, rs, con);
        }
    }

    /**
     * execute SQL and get single row result.
     * 
     * @param sql
     *            SQL
     * @param class1
     *            Data mapping class for database row data
     * @param params
     *            SQL parameters
     * @param <E> type
     * @return result of SQL.
     */
    protected <E> E executeQuerySingle(String sql, Class<? extends E> class1, Object... params) {
        return executeQueryOnKey(sql, class1, params);
    }

    /**
     * execute SQL and get multi rows result.
     * 
     * @param sql
     *            SQL
     * @param class1
     *            Data mapping class for database row data
     * @param params
     *            SQL parameters
     * @param <E> type
     * @return result of SQL.
     */
    protected <E> List<E> executeQueryList(String sql, Class<? extends E> class1, Object... params) {
        return executeQuery(sql, class1, params);
    }

    /**
     * execute SQL for update data.
     * 
     * @param sql
     *            SQL
     * @param params
     *            SQL parameters
     * @return result of SQL.
     */
    protected int executeUpdate(String sql, Object... params) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection();

            stmt = con.prepareStatement(sql);
            StringBuilder builder = new StringBuilder();
            builder.append("[connection]").append(con.hashCode());
            builder.append("[executeUpdate]").append(sql).append("\n     ");
            if (params != null) {
                int count = 1;
                for (Object param : params) {
                    String result = setParam(stmt, param, count);
                    builder.append(result);
                    count++;
                }
            }
            logger.debug(builder.toString());
            int count = stmt.executeUpdate();
            // con.commit();
            return count;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new ORMappingException(e);
                }
            }
            ORMappingException exception = new ORMappingException(e);
            exception.setSql(sql);
            exception.setParams(params);
            throw exception;
        } finally {
            close(stmt, con);
        }
    }

    /**
     * execute SQL for insert data.
     * 
     * @param sql
     *            SQL
     * @param params
     *            SQL parameters
     * @param type type
     * @return result of SQL.
     */
    protected Object executeInsert(String sql, Class<?> type, Object... params) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection();

            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            StringBuilder builder = new StringBuilder();
            builder.append("[connection]").append(con.hashCode());
            builder.append("[executeUpdate]").append(sql).append("\n     ");
            if (params != null) {
                int count = 1;
                for (Object param : params) {
                    String result = setParam(stmt, param, count);
                    builder.append(result);
                    count++;
                }
            }
            logger.debug(builder.toString());
            stmt.executeUpdate();

            // 採番したキーがあれば取得
            ResultSet keys = stmt.getGeneratedKeys();
            Object value = null;
            if (keys.next()) {
                ObjectToDatabaseConv conv = ObjectToDatabaseConvFactory.getObjectToDatabaseConv(keys);
                DatabaseAccessType accessType = conv.getObjectToDatabaseType(type);
                if (accessType == DatabaseAccessType.Int) {
                    value = keys.getInt(1);
                } else if (accessType == DatabaseAccessType.Long) {
                    value = keys.getLong(1);
                } else if (accessType == DatabaseAccessType.Short) {
                    value = keys.getShort(1);
                } else {
                    logger.warn("オートインクリメントは、Int,Long,Shortの型のみ対応しています");
                }
            }
            // con.commit();
            return value;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new ORMappingException(e);
                }
            }
            ORMappingException exception = new ORMappingException(e);
            exception.setSql(sql);
            exception.setParams(params);
            throw exception;
        } finally {
            close(stmt, con);
        }
    }

    /**
     * set stmt parameter.
     * 
     * @param stmt
     *            PreparedStatement
     * @param param
     *            value of stmt parameter.
     * @param count
     *            index of PreparedStatement param.
     * @return
     * @throws SQLException
     */
    private String setParam(PreparedStatement stmt, Object param, int count) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append("[param-").append(count).append("] ");
        if (param == null) {
            stmt.setNull(count, Types.NULL);
            builder.append("Null@Null");
        } else {
            if (param.getClass().isPrimitive()) {
                if (param.getClass().equals(short.class)) {
                    stmt.setShort(count, (short) param);
                    builder.append("Short@").append(param);
                } else if (param.getClass().equals(int.class)) {
                    stmt.setInt(count, (int) param);
                    builder.append("Int@").append(param);
                } else if (param.getClass().equals(long.class)) {
                    stmt.setLong(count, (long) param);
                    builder.append("Long@").append(param);
                } else if (param.getClass().equals(float.class)) {
                    stmt.setFloat(count, (float) param);
                    builder.append("Float@").append(param);
                } else if (param.getClass().equals(double.class)) {
                    stmt.setDouble(count, (double) param);
                    builder.append("Double@").append(param);
                } else if (param.getClass().equals(boolean.class)) {
                    stmt.setBoolean(count, (boolean) param);
                    builder.append("Boolean@").append(param);
                } else {
                    throw new ORMappingException("この型は未対応 : " + param.getClass().getName());
                }
            } else {
                if (param instanceof Blob) {
                    // BLOBの型は、Entity作成時にInputStreamにしているため、基本的には使わない
                    stmt.setBlob(count, (Blob) param);
                    builder.append("Blob@").append("Binary");
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(count, (Boolean) param);
                    builder.append("Boolean@").append(param);
                } else if (param instanceof Clob) {
                    // CLOBの型はEntity作成時にStringにしているため、基本的には使わない
                    stmt.setClob(count, (Clob) param);
                    builder.append("Clob@").append("Text");
                } else if (param instanceof java.util.Date) {
                    java.util.Date date = (java.util.Date) param;
                    stmt.setTimestamp(count, new Timestamp(date.getTime()));
                    builder.append("Timestamp@").append(DateUtils.formatTransferDateTime(date));
                } else if (param instanceof Date) {
                    stmt.setDate(count, (Date) param);
                    builder.append("Date@").append(DateUtils.formatTransferDateTime((Date) param));
                } else if (param instanceof BigDecimal) {
                    stmt.setBigDecimal(count, (BigDecimal) param);
                    builder.append("BigDecimal@").append(param);
                } else if (param instanceof Double) {
                    stmt.setDouble(count, (Double) param);
                    builder.append("Double@").append(param);
                } else if (param instanceof Float) {
                    stmt.setFloat(count, (Float) param);
                    builder.append("Float@").append(param);
                } else if (param instanceof Integer) {
                    stmt.setInt(count, (Integer) param);
                    builder.append("Int@").append(param);
                } else if (param instanceof Long) {
                    stmt.setLong(count, (Long) param);
                    builder.append("Long@").append(param);
                } else if (param instanceof Time) {
                    stmt.setTime(count, (Time) param);
                    builder.append("Time@").append(param);
                } else if (param instanceof Timestamp) {
                    stmt.setTimestamp(count, (Timestamp) param);
                    builder.append("Timestamp@").append(DateUtils.formatTransferDateTime((Timestamp) param));
                } else if (param instanceof String) {
                    stmt.setString(count, (String) param);
                    builder.append("String@").append(param);
                } else if (param instanceof InputStream) {
                    try {
                        InputStream inputStream = (InputStream) param;
                        stmt.setBinaryStream(count, inputStream, inputStream.available());
                        builder.append("BinaryStream@").append("InputStream");
                    } catch (IOException e) {
                        throw new ORMappingException(e);
                    }
                }
            }
        }
        return builder.toString();
    }

}
