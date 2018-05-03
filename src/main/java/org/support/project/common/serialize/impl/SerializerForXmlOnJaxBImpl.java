package org.support.project.common.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.Serializer;


/**
 * XMLとオブジェクトの変換をJaxbを用いて実施する
 * @author Koda
 *
 */
public class SerializerForXmlOnJaxBImpl implements Serializer {
	/** ログ */
	private static final Log LOG = LogFactory.getLog(SerializerForXmlOnJaxBImpl.class);

	@Override
	public byte[] objectTobytes(final Object obj) throws SerializeException {
		LOG.debug("Serialize object to xml on Jaxb");
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // 改行を入れる
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); // 文字コードの指定
//			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.marshal(obj, byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		} catch (JAXBException e) {
			throw new SerializeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T bytesToObject(final byte[] bytes, final Class<? extends T> type) throws SerializeException {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			JAXBContext context = JAXBContext.newInstance(type);
			Unmarshaller u = context.createUnmarshaller();
			return (T) u.unmarshal(inputStream);
		} catch (JAXBException e) {
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
