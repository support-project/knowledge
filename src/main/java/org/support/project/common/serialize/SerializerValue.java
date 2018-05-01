package org.support.project.common.serialize;

/**
 * XmlSerializerに何を利用するかを表現する列挙型
 * @author Koda
 *
 */
public enum SerializerValue {
	/**
	 * Jaxbを利用してXMLにシリアライズする
	 */
	Jaxb,
	/**
	 * Simple Frameworkを利用してXMLにシリアライズする
	 */
	Simple,
	/**
	 * JSONICを利用してJSONにシリアライズする
	 */
	JSONIC,
	/**
	 * 独自に実装したシリアライズクラスでシリアライズする
	 */
	Serializer, 
	/**
	 * Xmlにしないでjava.io.Serializableでシリアライズする
	 */
	Serializable 
}
