package org.support.project.ormapping.conv;

public interface ObjectToDatabaseConv {
	
	/**
	 * Javaのオブジェクトの型が、データベースにアクセスする際のどのタイプになるかを取得する。
	 * このタイプにより、DBから値を取得／セットのgetInt/setStringなどの呼び出しを制御する。
	 * 
	 * @param clazz 型
	 * @return データベースにアクセスする際のタイプ
	 */
	DatabaseAccessType getObjectToDatabaseType(Class<?> clazz);
	
	
	/**
	 * Javaのプロパティ名からデータベースの列名を取得する
	 * 基本的に、Javaでは区切りはで小文字→大文字になるが、
	 * データベースでは区切りは「_」でつける。
	 * その変換を行う
	 * 
	 * @param propertyName プロパティ名
	 * @return 列名
	 */
	String getPropertyToClumnName(String propertyName); 
	
}
