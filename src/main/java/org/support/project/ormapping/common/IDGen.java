package org.support.project.ormapping.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.support.project.common.exception.SystemException;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.exception.ORMappingException;

@DI(instance = Instance.Singleton)
public class IDGen {
    private static final String ALGORITHM = "MD5";

    private long count = 0;

    public static IDGen get() {
        return Container.getComp(IDGen.class);
    }

    public IDGen() {
        super();
        Date now = DateUtils.now();
        count = now.getTime();
    }

    public String gen(String key) {
        count++;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(ALGORITHM);
            StringBuilder builder = new StringBuilder();
            builder.append(key);
            builder.append(DateUtils.getTransferDateFormat().format(DateUtils.now()));
            builder.append(count);
            byte[] hash = digest.digest(builder.toString().getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new ORMappingException(e);
        }
    }

}
