package org.support.project.ormapping.conv.impl;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.conv.DatabaseAccessType;
import org.support.project.ormapping.conv.ObjectToDatabaseConv;

public class ObjectToDatabaseConvDefaultImpl implements ObjectToDatabaseConv {
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    private static final Resources resources = Resources.getInstance(ORMappingParameter.OR_MAPPING_RESOURCE);

    @Override
    public DatabaseAccessType getObjectToDatabaseType(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return getObjectToDatabasePrimitiveType(clazz);
        } else if (clazz.isArray()) {
            return getObjectToDatabaseArayType(clazz);
        } else {
            return getObjectToDatabaseObjectType(clazz);
        }
    }

    private DatabaseAccessType getObjectToDatabaseObjectType(Class<?> clazz) {
        if (Boolean.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Boolean;
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Byte;
        } else if (Short.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Short;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Int;
        } else if (Long.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Long;
        } else if (Float.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Float;
        } else if (Double.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Double;
        } else if (String.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.String;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Date;
        } else if (java.sql.Date.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Date;
        } else if (Time.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Time;
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Timestamp;
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.BigDecimal;
        } else if (Blob.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Blob;
        } else if (InputStream.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.InputStream;
        }
        // LOG.warn(resources.getResource("errors.or.not.covered", clazz.getName()));
        if (Collection.class.isAssignableFrom(clazz)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("It is a class that can not be processed. " + clazz.getName());
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("It is a class that can not be processed. " + clazz.getName());
            }
        } else {
            LOG.warn("It is a class that can not be processed. " + clazz.getName());
        }
        return DatabaseAccessType.None;
        // throw new ORMappingException("errors.or.not.covered", clazz.getName());
    }

    private DatabaseAccessType getObjectToDatabaseArayType(Class<?> clazz) {
        // 配列の型を扱えるのはbyteのみ
        if (byte.class.isAssignableFrom(clazz.getComponentType())) {
            return DatabaseAccessType.Bytes;
        }
        return DatabaseAccessType.None;
    }

    private DatabaseAccessType getObjectToDatabasePrimitiveType(Class<?> clazz) {
        if (boolean.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Boolean;
        } else if (byte.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Byte;
        } else if (short.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Short;
        } else if (int.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Int;
        } else if (long.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Long;
        } else if (float.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Float;
        } else if (double.class.isAssignableFrom(clazz)) {
            return DatabaseAccessType.Double;
        }
        return DatabaseAccessType.None;
    }

    @Override
    public String getPropertyToClumnName(String propertyName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < propertyName.length(); i++) {
            char c = propertyName.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append('_');
            }
            builder.append(c);
        }
        return builder.toString().toUpperCase();
    }

}
