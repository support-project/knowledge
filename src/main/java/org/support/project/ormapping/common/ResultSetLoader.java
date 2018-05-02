package org.support.project.ormapping.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.classanalysis.ClassAnalysis;
import org.support.project.common.classanalysis.ClassAnalysisFactory;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.conv.DatabaseAccessType;
import org.support.project.ormapping.conv.ObjectToDatabaseConv;
import org.support.project.ormapping.conv.ObjectToDatabaseConvFactory;
import org.support.project.ormapping.exception.ORMappingException;

public class ResultSetLoader {
    private static final Log LOG = LogFactory.getLog(ResultSetLoader.class);

    public static void load(ResultSet rs, Object object, String driverClass) {
        try {
            List<String> columns = new ArrayList<String>();
            int count = rs.getMetaData().getColumnCount();
            if (LOG.isTraceEnabled()) {
                LOG.trace("Check colmn start");
            }
            for (int i = 1; i <= count; i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                columns.add(columnName.toLowerCase());
                if (LOG.isTraceEnabled()) {
                    LOG.trace(columnName);
                }
                String columnLabel = rs.getMetaData().getColumnLabel(i);
                if (!columns.contains(columnLabel)) {
                    columns.add(columnLabel.toLowerCase());
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(columnLabel);
                    }
                }
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("Check colmn end");
            }

            List<String> props = PropertyUtil.getPropertyNames(object);
            for (String prop : props) {
                Class<?> type = PropertyUtil.getPropertyType(object, prop);
                ObjectToDatabaseConv conv = ObjectToDatabaseConvFactory.getObjectToDatabaseConv(rs);
                DatabaseAccessType accessType = conv.getObjectToDatabaseType(type);
                String column = conv.getPropertyToClumnName(prop);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Read column : " + column);
                }
                if (columns.contains(column.toLowerCase())) {
                    // int idx = rs.findColumn(column);
                    if (accessType == DatabaseAccessType.BigDecimal) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getBigDecimal(column));
                    } else if (accessType == DatabaseAccessType.Blob) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getBlob(column));
                    } else if (accessType == DatabaseAccessType.Boolean) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getBoolean(column));
                    } else if (accessType == DatabaseAccessType.Byte) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getByte(column));
                    } else if (accessType == DatabaseAccessType.Bytes) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getBytes(column));
                    } else if (accessType == DatabaseAccessType.Timestamp) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getTimestamp(column));
                    } else if (accessType == DatabaseAccessType.Time) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getTime(column));
                    } else if (accessType == DatabaseAccessType.Date) {

                        // Timestampがここにないってしまいエラーになるので、オブジェクトの型を見て判定する
                        ClassAnalysis analysis = ClassAnalysisFactory.getClassAnalysis(object.getClass());
                        Method setter = analysis.getSetterPropertyMethod(prop);
                        if (setter.getParameterTypes()[0].isAssignableFrom(Timestamp.class)) {
                            PropertyUtil.setPropertyValue(object, prop, rs.getTimestamp(column));
                        } else if (setter.getParameterTypes()[0].isAssignableFrom(Time.class)) {
                            PropertyUtil.setPropertyValue(object, prop, rs.getTime(column));
                        } else {
                            // 通常の日付型でセット
                            PropertyUtil.setPropertyValue(object, prop, rs.getDate(column));
                        }
                    } else if (accessType == DatabaseAccessType.Double) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getDouble(column));
                    } else if (accessType == DatabaseAccessType.Float) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getFloat(column));
                    } else if (accessType == DatabaseAccessType.Int) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getInt(column));
                    } else if (accessType == DatabaseAccessType.Long) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getLong(column));
                    } else if (accessType == DatabaseAccessType.Short) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getShort(column));
                    } else if (accessType == DatabaseAccessType.String) {
                        PropertyUtil.setPropertyValue(object, prop, rs.getString(column));
                    } else if (accessType == DatabaseAccessType.InputStream) {
                        if (ORMappingParameter.DRIVER_NAME_H2.equals(driverClass)) {
                            LOG.trace("H2SQL LOB");
                            Blob blob = rs.getBlob(column);
                            // いったんメモリ内のみで制御
                            // TODO Out of Memory になる可能性があるため、必要に応じファイルIOで処理する
                            if (blob != null && blob.getBinaryStream() != null) {
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                FileUtil.copy(blob.getBinaryStream(), out);
                                ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
                                PropertyUtil.setPropertyValue(object, prop, inputStream);
                            }
                        } else {
                            // Postgresql
                            LOG.trace("Postgresql LOB");
                            byte[] bytes = rs.getBytes(column);
                            if (bytes != null) {
                                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                                PropertyUtil.setPropertyValue(object, prop, inputStream);
                            }
                        }
                    } else {
                        LOG.warn("処理出来ない型のデータです:" + accessType);
                        // ロードするタイプが見つからないため、読み飛ばす
                    }
                } else {
                    LOG.debug("column : " + column + " is not exists.");
                }
            }
        } catch (SQLException e) {
            throw new ORMappingException(e);
        } catch (IOException e) {
            throw new ORMappingException(e);
        }
    }

}
