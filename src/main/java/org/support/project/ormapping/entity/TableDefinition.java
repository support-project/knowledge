package org.support.project.ormapping.entity;

import java.util.List;

/**
 * テーブルの定義情報
 * 
 * java.sql.DatabaseMetaData#getTablesの結果を格納するBeanクラスである
 * 
 */
public class TableDefinition {

	
	/**
getTables
public ResultSet getTables(String catalog,
                           String schemaPattern,
                           String tableNamePattern,
                           String[] types)
                    throws SQLException指定されたカタログで使用可能なテーブルに関する記述を取得します。カタログ、スキーマ、テーブル名および型の条件に一致するテーブルの記述だけが返されます。それらは、TABLE_TYPE、TABLE_SCHEM、TABLE_NAME によって順序付けられます。 
各テーブルの記述には次の列があります。 

TABLE_CAT String => テーブルカタログ (null の可能性がある) 
TABLE_SCHEM String => テーブルスキーマ (null の可能性がある) 
TABLE_NAME String => テーブル名 
TABLE_TYPE String => テーブルの型。典型的な型は、"TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS"、"SYNONYM" である 
REMARKS String => テーブルに関する説明 
TYPE_CAT String => の型のカタログ (null の可能性がある) 
TYPE_SCHEM String => の型のスキーマ (null の可能性がある) 
TYPE_NAME String => の型名 (null の可能性がある) 
SELF_REFERENCING_COL_NAME String => 型付きテーブルの指定された「識別子」列の名前 (null の可能性がある) 
REF_GENERATION String => SELF_REFERENCING_COL_NAME の値の作成方法を指定する。値は、"SYSTEM"、"USER"、"DERIVED" (null の可能性がある) 
注: データベースによっては、すべてのテーブルに関する情報を返さないものがあります。 


パラメータ: 
catalog - カタログ名。データベースに格納されたカタログ名と一致しなければならない。"" はカタログなしでカタログ名を検索する。null は、カタログ名を検索の限定に使用してはならないことを意味する
schemaPattern - スキーマ名パターン。データベースに格納されたスキーマ名と一致しなければならない。"" はスキーマなしでスキーマ名を検索する。null は、スキーマ名を検索の限定に使用してはならないことを意味する
tableNamePattern - テーブル名パターン。データベースに格納されたテーブル名と一致しなければならない
types - 組み込むテーブルの型のリスト。null はすべての型を返す 
戻り値: 
ResultSet。各行はテーブルの記述 
例外: 
SQLException - データベースアクセスエラーが発生した場合
関連項目:
getSearchStringEscape()

	 * 
	 */
	
	
	private String table_cat;
	private String table_schem;
	private String table_name;
	private String table_type;
	private String remarks;
	private String type_cat;
	private String type_schem;
	private String type_name;
	private String self_referencing_col_name;
	private String ref_generation;
	/** 列情報 */
	private List<ColumnDefinition> columns;
	
	/**
	 * ref_generation を取得します
	 * @return the ref_generation
	 */
	public String getRef_generation() {
		return ref_generation;
	}
	/**
	 * ref_generationを設定します
	 * @param ref_generation the ref_generation to set
	 */
	public void setRef_generation(String ref_generation) {
		this.ref_generation = ref_generation;
	}
	/**
	 * remarks を取得します
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * remarksを設定します
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * self_referencing_col_name を取得します
	 * @return the self_referencing_col_name
	 */
	public String getSelf_referencing_col_name() {
		return self_referencing_col_name;
	}
	/**
	 * self_referencing_col_nameを設定します
	 * @param self_referencing_col_name the self_referencing_col_name to set
	 */
	public void setSelf_referencing_col_name(String self_referencing_col_name) {
		this.self_referencing_col_name = self_referencing_col_name;
	}
	/**
	 * table_cat を取得します
	 * @return the table_cat
	 */
	public String getTable_cat() {
		return table_cat;
	}
	/**
	 * table_catを設定します
	 * @param table_cat the table_cat to set
	 */
	public void setTable_cat(String table_cat) {
		this.table_cat = table_cat;
	}
	/**
	 * table_name を取得します
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}
	/**
	 * table_nameを設定します
	 * @param table_name the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	/**
	 * table_schem を取得します
	 * @return the table_schem
	 */
	public String getTable_schem() {
		return table_schem;
	}
	/**
	 * table_schemを設定します
	 * @param table_schem the table_schem to set
	 */
	public void setTable_schem(String table_schem) {
		this.table_schem = table_schem;
	}
	/**
	 * table_type を取得します
	 * @return the table_type
	 */
	public String getTable_type() {
		return table_type;
	}
	/**
	 * table_typeを設定します
	 * @param table_type the table_type to set
	 */
	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}
	/**
	 * type_cat を取得します
	 * @return the type_cat
	 */
	public String getType_cat() {
		return type_cat;
	}
	/**
	 * type_catを設定します
	 * @param type_cat the type_cat to set
	 */
	public void setType_cat(String type_cat) {
		this.type_cat = type_cat;
	}
	/**
	 * type_name を取得します
	 * @return the type_name
	 */
	public String getType_name() {
		return type_name;
	}
	/**
	 * type_nameを設定します
	 * @param type_name the type_name to set
	 */
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	/**
	 * type_schem を取得します
	 * @return the type_schem
	 */
	public String getType_schem() {
		return type_schem;
	}
	/**
	 * type_schemを設定します
	 * @param type_schem the type_schem to set
	 */
	public void setType_schem(String type_schem) {
		this.type_schem = type_schem;
	}
	
	
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}
	
	
	@Override
	public String toString() {
		return "TableDefinition [table_cat=" + table_cat + ", table_schem=" + table_schem + ", table_name=" + table_name + ", table_type="
				+ table_type + ", remarks=" + remarks + ", type_cat=" + type_cat + ", type_schem=" + type_schem + ", type_name=" + type_name
				+ ", self_referencing_col_name=" + self_referencing_col_name + ", ref_generation=" + ref_generation + "]";
	}
	
	
	
}
