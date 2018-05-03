package org.support.project.common.serialize;


/**
 * シリアライズされた出力の形式
 * 独自のシリアライズクラスを用いるときは必須とする
 * @author Koda
 *
 */
public enum SerializeOutputType {
	/**
	 * XMLでシリアライズ
	 */
	XML,
	/**
	 * JSON形式でシリアライズ
	 */
	JSON, 
	/**
	 * java.io.Serializable形式(byte配列とみなす)でシリアライズ
	 * →文字列にする時にはBase64エンコードする
	 */
	Serializable,
	/**
	 * 独自クラスを用いていない為、指定しない(デフォルト)
	 */
	Null 

}
