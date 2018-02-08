package org.support.project.common.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.MethodHandles;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.Serializer;


/**
 * オブジェクトをXMLにしないでjavaのシリアライズを使ってバイトにして送受信する
 * @author Koda
 *
 */
public class SerializerForSerializableImpl implements Serializer {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Override
    public byte[] objectTobytes(final Object obj) throws SerializeException {
        LOG.debug("Serialize object to byte[] on Serializable");
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream oos = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new SerializeException(e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T bytesToObject(final byte[] bytes, final Class<? extends T> type) throws SerializeException {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new SerializeException(e);
                }
            }
        }
    }

}
