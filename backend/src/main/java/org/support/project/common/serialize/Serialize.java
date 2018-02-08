package org.support.project.common.serialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.support.project.common.serialize.impl.SerializerForSerializableImpl;


/**
 * Xmlのシリアライズを何で行うかを指定するアノテーション
 * 
 * @author Koda
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Serialize {
	/**
	 * Xmlシリアライズに何を使うか選択値を取得する
	 * @return Serializer
	 */
	SerializerValue value() default SerializerValue.Jaxb;
	/**
	 * シリアライズが独自クラスの指定だった際に、そのクラスを取得する
	 * @return implement class type
	 */
	Class<? extends Serializer> serializerClass() default SerializerForSerializableImpl.class;

	/**
	 * シリアライズが独自クラスの場合、出力の形式を指定する
	 * @return output type
	 */
	SerializeOutputType serializeOutputType() default SerializeOutputType.Null;

}
