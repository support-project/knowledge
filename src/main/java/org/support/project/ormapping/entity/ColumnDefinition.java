package org.support.project.ormapping.entity;


/**
 * 列定義情報
 * 
 * java.sql.DatabaseMetaData#getColumnsの結果を格納するBeanクラスである
 * 
 *
 */
public class ColumnDefinition {

	/**
getColumns
public ResultSet getColumns(String catalog,
                            String schemaPattern,
                            String tableNamePattern,
                            String columnNamePattern)
                     throws SQLException指定されたカタログで使用可能なテーブル列の記述を取得します。 
カタログ、スキーマ、テーブル名、および列名の条件に一致する列の記述だけが返されます。それらは、TABLE_SCHEM、TABLE_NAME、ORDINAL_POSITION によって順序付けられます。 

各列の説明を次にします。 

 TABLE_CAT String => テーブルカタログ (null の可能性がある) 
 TABLE_SCHEM String => テーブルスキーマ (null の可能性がある) 
 TABLE_NAME String => テーブル名 
 COLUMN_NAME String => 列名 
 DATA_TYPE short => java.sql.Types からの SQL の型 
 TYPE_NAME String => データソース依存の型名。UDT の場合、型名は完全指定 
 COLUMN_SIZE int => 列サイズ。char や date の型については最大文字数、numeric や decimal の型については精度 
BUFFER_LENGTH - 未使用 
 DECIMAL_DIGITS int => 小数点以下の桁数 
 NUM_PREC_RADIX int => 基数 (通常は、10 または 2 のどちらか) 
 NULLABLE int => NULL は許されるか 
    columnNoNulls - NULL 値を許さない可能性がある 
    columnNullable - 必ず NULL 値を許す 
    columnNullableUnknown - NULL 値を許すかどうかは不明 
 REMARKS String => コメント記述列 (null の可能性がある) 
 COLUMN_DEF String => デフォルト値 (null の可能性がある) 
SQL_DATA_TYPE int => 未使用 
SQL_DATETIME_SUB int => 未使用 
 CHAR_OCTET_LENGTH int => char の型については列の最大バイト数 
 ORDINAL_POSITION int => テーブル中の列のインデックス (1 から始まる) 
 IS_NULLABLE String => "NO" は、列は決して NULL 値を許さないことを意味する。"YES" は NULL 値を許す可能性があることを意味する。空の文字列は不明であることを意味する 
 SCOPE_CATLOG String => 参照属性のスコープであるテーブルのカタログ (DATA_TYPE が REF でない場合は null) 
 SCOPE_SCHEMA String => 参照属性のスコープであるテーブルのスキーマ (DATA_TYPE が REF でない場合は null) 
 SCOPE_TABLE String => 参照属性のスコープであるテーブル名 (DATA_TYPE が REF でない場合は null) 
 SOURCE_DATA_TYPE short => 個別の型またはユーザ生成 Ref 型、java.sql.Types の SQL 型のソースの型 (DATA_TYPE が DISTINCT またはユーザ生成 REF でない場合は null) 

パラメータ: 
catalog - カタログ名。データベースに格納されたカタログ名と一致しなければならない。"" はカタログなしでカタログ名を検索する。null は、カタログ名を検索の限定に使用してはならないことを意味する
schemaPattern - スキーマ名パターン。データベースに格納されたスキーマ名と一致しなければならない。"" はスキーマなしでスキーマ名を検索する。null は、スキーマ名を検索の限定に使用してはならないことを意味する
tableNamePattern - テーブル名パターン。データベースに格納されたテーブル名と一致しなければならない
columnNamePattern - 列名パターン。データベースに格納された列名と一致しなければならない 
戻り値: 
ResultSet。各行は列の記述 
例外: 
SQLException - データベースアクセスエラーが発生した場合
関連項目:
getSearchStringEscape()
	 */
	
	private String table_cat;
	private String table_schem;
	private String table_name;
	private String column_name;
	private short data_type;
	private String type_name;
	private int column_size;
	private int decimal_digits;
	private int num_prec_radix;
	private int nullable;
	private String remarks;
	private String column_def;
	private int char_octet_length;
	private int ordinal_position;
	private String is_nullable;
	private String is_autoincrement;
	private String scope_catlog;
	private String scope_schema;
	private String scope_table;
	private short source_data_type;
	
	private boolean primary;
	private int primary_no;
	
	private int character_maximum_length;
	
	
	public boolean isPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	public int getPrimary_no() {
		return primary_no;
	}
	public void setPrimary_no(int primary_no) {
		this.primary_no = primary_no;
	}
	/**
	 * char_octet_length を取得します
	 * @return the char_octet_length
	 */
	public int getChar_octet_length() {
		return char_octet_length;
	}
	/**
	 * char_octet_lengthを設定します
	 * @param char_octet_length the char_octet_length to set
	 */
	public void setChar_octet_length(int char_octet_length) {
		this.char_octet_length = char_octet_length;
	}
	/**
	 * column_def を取得します
	 * @return the column_def
	 */
	public String getColumn_def() {
		return column_def;
	}
	/**
	 * column_defを設定します
	 * @param column_def the column_def to set
	 */
	public void setColumn_def(String column_def) {
		this.column_def = column_def;
	}
	/**
	 * column_name を取得します
	 * @return the column_name
	 */
	public String getColumn_name() {
		return column_name;
	}
	/**
	 * column_nameを設定します
	 * @param column_name the column_name to set
	 */
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	/**
	 * column_size を取得します
	 * @return the column_size
	 */
	public int getColumn_size() {
		return column_size;
	}
	/**
	 * column_sizeを設定します
	 * @param column_size the column_size to set
	 */
	public void setColumn_size(int column_size) {
		this.column_size = column_size;
	}
	/**
	 * data_type を取得します
	 * @return the data_type
	 */
	public short getData_type() {
		return data_type;
	}
	/**
	 * data_typeを設定します
	 * @param data_type the data_type to set
	 */
	public void setData_type(short data_type) {
		this.data_type = data_type;
	}
	/**
	 * decimal_digits を取得します
	 * @return the decimal_digits
	 */
	public int getDecimal_digits() {
		return decimal_digits;
	}
	/**
	 * decimal_digitsを設定します
	 * @param decimal_digits the decimal_digits to set
	 */
	public void setDecimal_digits(int decimal_digits) {
		this.decimal_digits = decimal_digits;
	}
	/**
	 * is_nullable を取得します
	 * @return the is_nullable
	 */
	public String getIs_nullable() {
		return is_nullable;
	}
	/**
	 * is_nullableを設定します
	 * @param is_nullable the is_nullable to set
	 */
	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}
	/**
	 * nullable を取得します
	 * @return the nullable
	 */
	public int getNullable() {
		return nullable;
	}
	/**
	 * nullableを設定します
	 * @param nullable the nullable to set
	 */
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
	/**
	 * num_prec_radix を取得します
	 * @return the num_prec_radix
	 */
	public int getNum_prec_radix() {
		return num_prec_radix;
	}
	/**
	 * num_prec_radixを設定します
	 * @param num_prec_radix the num_prec_radix to set
	 */
	public void setNum_prec_radix(int num_prec_radix) {
		this.num_prec_radix = num_prec_radix;
	}
	/**
	 * ordinal_position を取得します
	 * @return the ordinal_position
	 */
	public int getOrdinal_position() {
		return ordinal_position;
	}
	/**
	 * ordinal_positionを設定します
	 * @param ordinal_position the ordinal_position to set
	 */
	public void setOrdinal_position(int ordinal_position) {
		this.ordinal_position = ordinal_position;
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
	 * scope_catlog を取得します
	 * @return the scope_catlog
	 */
	public String getScope_catlog() {
		return scope_catlog;
	}
	/**
	 * scope_catlogを設定します
	 * @param scope_catlog the scope_catlog to set
	 */
	public void setScope_catlog(String scope_catlog) {
		this.scope_catlog = scope_catlog;
	}
	/**
	 * scope_schema を取得します
	 * @return the scope_schema
	 */
	public String getScope_schema() {
		return scope_schema;
	}
	/**
	 * scope_schemaを設定します
	 * @param scope_schema the scope_schema to set
	 */
	public void setScope_schema(String scope_schema) {
		this.scope_schema = scope_schema;
	}
	/**
	 * scope_table を取得します
	 * @return the scope_table
	 */
	public String getScope_table() {
		return scope_table;
	}
	/**
	 * scope_tableを設定します
	 * @param scope_table the scope_table to set
	 */
	public void setScope_table(String scope_table) {
		this.scope_table = scope_table;
	}
	/**
	 * source_data_type を取得します
	 * @return the source_data_type
	 */
	public short getSource_data_type() {
		return source_data_type;
	}
	/**
	 * source_data_typeを設定します
	 * @param source_data_type the source_data_type to set
	 */
	public void setSource_data_type(short source_data_type) {
		this.source_data_type = source_data_type;
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
	 * @return is_autoincrement
	 */
	public String getIs_autoincrement() {
		return is_autoincrement;
	}
	/**
	 * @param is_autoincrement セットする is_autoincrement
	 */
	public void setIs_autoincrement(String is_autoincrement) {
		this.is_autoincrement = is_autoincrement;
	}
	
	public int getCharacter_maximum_length() {
		return character_maximum_length;
	}
	public void setCharacter_maximum_length(int character_maximum_length) {
		this.character_maximum_length = character_maximum_length;
	}

	
	
	@Override
	public String toString() {
		return "ColumnDefinition [table_cat=" + table_cat + ", table_schem=" + table_schem + ", table_name=" + table_name + ", column_name="
				+ column_name + ", data_type=" + data_type + ", type_name=" + type_name + ", column_size=" + column_size + ", decimal_digits="
				+ decimal_digits + ", num_prec_radix=" + num_prec_radix + ", nullable=" + nullable + ", remarks=" + remarks + ", column_def="
				+ column_def + ", char_octet_length=" + char_octet_length + ", ordinal_position=" + ordinal_position + ", is_nullable=" + is_nullable
				+ ", scope_catlog=" + scope_catlog + ", scope_schema=" + scope_schema + ", scope_table=" + scope_table + ", source_data_type="
				+ source_data_type + ", is_autoincrement=" + is_autoincrement + ", character_maximum_length=" + character_maximum_length + "]";
	}
	
	
	
	
	
	
}
