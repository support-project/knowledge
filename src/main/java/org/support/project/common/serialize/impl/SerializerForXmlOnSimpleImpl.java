package org.support.project.common.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;


/**
 * XMLとオブジェクトの変換をSimpleを用いて実施する
 * @author Koda
 *
 */
public class SerializerForXmlOnSimpleImpl implements org.support.project.common.serialize.Serializer {
	/** ログ */
	private static final Log LOG = LogFactory.getLog(SerializerForXmlOnSimpleImpl.class);
	
	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; 
	private Format format = new Format(XML_DECLARATION);
	
	@Override
	public byte[] objectTobytes(final Object obj) throws SerializeException {
		LOG.debug("Serialize object to xml on Simple");
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Persister serializer = new Persister(format);
			serializer.write(obj, byteArrayOutputStream, "UTF-8");
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			throw new SerializeException(e);
		}
	}

	@Override
	public <T> T bytesToObject(final byte[] bytes, final Class<? extends T> type) throws SerializeException {
		InputStream inputStream = null;
		try {
			InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8");
			Persister serializer = new Persister();
			return serializer.read(type, reader);
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
