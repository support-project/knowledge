package org.support.project.common.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.serialize.Serializer;

import net.arnx.jsonic.JSON;

public class SerializerForJSONOnJSONICImpl implements Serializer {

    @Override
    public byte[] objectTobytes(Object obj) throws SerializeException {
        try {
            String json = JSON.encode(obj);
            byte[] bytes = json.getBytes("UTF-8");
            return bytes;
        } catch (UnsupportedEncodingException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public <T> T bytesToObject(byte[] bytes, Class<? extends T> type) throws SerializeException {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            return JSON.decode(inputStream, type);
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
