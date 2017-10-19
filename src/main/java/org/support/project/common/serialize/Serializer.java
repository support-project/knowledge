package org.support.project.common.serialize;

import org.support.project.common.exception.SerializeException;


/**
 * オブジェクト/XMLの相互変換を行う独自のシリアライズクラスのインタフェース
 * @author Koda
 *
 */
public interface Serializer {
	
	/**
	 * オブジェクトのXML表現のバイトを取得する
	 * エンコードはUTF-8とすること
	 * 
	 * @param obj 変換するオブジェクト
	 * @return XMLのバイト
	 * @throws SerializeException XmlException
	 */
	byte[] objectTobytes(final Object obj) throws SerializeException;

	/**
	 * 入力(XMLのストリーム)からオブジェクトに変換する
	 * @param <T> クラスの型　
	 * @param bytes byte[]
	 * @param type Class
	 * @return オブジェクト
	 * @throws SerializeException XmlException
	 */
	<T> T bytesToObject(final byte[] bytes, final Class<? extends T> type) throws SerializeException;
	
}
