package org.support.project.ormapping.common;

public class NameConvertor {
	
	
	/**
	 * テーブル名からクラス名を作成
	 * @param tableName テーブル名
	 * @return クラス名
	 */
	public String tableNameToClassName(String tableName) {
		tableName = tableName.toLowerCase();
		StringBuilder entityClassName = new StringBuilder();
		boolean upper = true;
		for (int i = 0; i < tableName.length(); i++) {
			char c = tableName.charAt(i);
			if (upper) {
				entityClassName.append(Character.toUpperCase(c));
				upper = false;
			} else {
				if (c == '_') {
					upper = true;
				} else {
					entityClassName.append(c);
					upper = false;
				}
			}
		}
		return entityClassName.toString();
	}
	
	/**
	 * 列の名称からフィールドの名称を取得
	 * @param columnName 列の名称
	 * @return フィールド名
	 */
	public String colmnNameToFeildName(String columnName) {
		return colmnNameToFeildName(columnName, false);
	}
	
	/**
	 * 列の名称からフィールドの名称を取得
	 * @param columnName 列の名称
	 * @param firstUpper 開始を大文字にするか
	 * @return フィールド名
	 */
	public String colmnNameToFeildName(String columnName, boolean firstUpper) {
		columnName = columnName.toLowerCase();
		StringBuilder feildName = new StringBuilder();
		boolean upper = firstUpper;
		for (int i = 0; i < columnName.length(); i++) {
			char c = columnName.charAt(i);
			if (upper) {
				feildName.append(Character.toUpperCase(c));
				upper = false;
			} else {
				if (c == '_') {
					upper = true;
				} else {
					feildName.append(c);
					upper = false;
				}
			}
		}
		return feildName.toString();
	}


	/**
	 * 列の名称からフィールドの名称を取得
	 * @param columnName 列の名称
	 * @return フィールド名
	 */
	public String colmnNameToLabelName(String columnName) {
		return colmnNameToLabelName(columnName, true);
	}
	
	/**
	 * 列の名称からフィールドの名称を取得
	 * @param columnName 列の名称
	 * @param firstUpper 開始を大文字にするか
	 * @return フィールド名
	 */
	public String colmnNameToLabelName(String columnName, boolean firstUpper) {
		columnName = columnName.toLowerCase();
		StringBuilder feildName = new StringBuilder();
		boolean upper = firstUpper;
		for (int i = 0; i < columnName.length(); i++) {
			char c = columnName.charAt(i);
			if (upper) {
				feildName.append(Character.toUpperCase(c));
				upper = false;
			} else {
				if (c == '_') {
					upper = true;
					feildName.append(' ');
				} else {
					feildName.append(c);
					upper = false;
				}
			}
		}
		return feildName.toString();
	}
	
	
	
	

	
	
	
	
}
