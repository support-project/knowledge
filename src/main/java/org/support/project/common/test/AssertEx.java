package org.support.project.common.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;

/**
 * 比較ロジック
 * @author Koda
 */
public class AssertEx {
	
	/**
	 * DB格納した意味ある値が等しいか
	 * @param expected expected
	 * @param actual actual
	 */
	public static void eqdb(Object expected, Object actual) {
		eq(expected, actual, "", true, null);
	}
	/**
	 * DB格納した意味ある値が等しいか
	 * @param expected expected
	 * @param actual actual
	 * @param ignores ignores
	 */
	public static void eqdb(Object expected, Object actual, List<String> ignores) {
		eq(expected, actual, "", true, ignores);
	}
	
	/**
	 * オブジェクトが等しいか
	 * @param expected 期待値
	 * @param actual 実際の値
	 */
	public static void eq(Object expected, Object actual) {
		eq(expected, actual, "");
	}

	/**
	 * オブジェクトが等しいか
	 * @param expected 期待値
	 * @param actual 実際の値
	 * @param parent parent
	 */
	public static void eq(Object expected, Object actual, String parent) {
		eq(expected, actual, parent, false, null);
	}

	
	/**
	 * オブジェクトが等しいか
	 * @param expected 期待値
	 * @param actual 実際の値
	 * @param parent 親ラベル
	 * @param db db
	 * @param ignores ignores
	 */
	private static void eq(Object expected, Object actual, String parent, boolean db, List<String> ignores) {
		if (expected == null) {
			if (!StringUtils.isEmpty(actual)) {
				throw new AssertionError("[" + parent + "] expected is null. but actual is not null.");
			}
		} else {
			if (StringUtils.isEmpty(expected) && StringUtils.isEmpty(actual)) {
				return;
			}
			if (expected != null && StringUtils.isEmpty(actual)) {
				throw new AssertionError("[" + parent + "] actual is null. but expected is not null.");
			}
			
			if (!expected.getClass().isAssignableFrom(actual.getClass())) {
				throw new AssertionError("[" + parent + "] actual typeis " + actual.getClass().getName() 
						+ ". but expected type is " + expected.getClass().getName() + ".");
			}
			if (expected.getClass().isPrimitive() && actual.getClass().isPrimitive()) {
				//比較の対象はプリミティブ型
				if (!expected.equals(actual)) {
					throw new AssertionError("[" + parent + "] actual is " + actual + ". but expected is " + expected + ".");
				} else {
					return;
				}
			} else {
				// 直接あたいを持つクラスであれば、そのまま比較
				for (Class<?> clazz : PropertyUtil.VALUE_CLASSES) {
					if (clazz.isAssignableFrom(expected.getClass())) {
						if (!expected.equals(actual)) {
							throw new AssertionError("[" + parent + "] actual is " + actual + ". but expected is " + expected + ".");
						} else {
							return;
						}
					}
				}
			}
			
			if (Collection.class.isAssignableFrom(expected.getClass())) {
				Collection<?> collection1 = (Collection<?>) expected;
				Collection<?> collection2 = (Collection<?>) actual;
				
				if (collection1.size() != collection2.size()) {
					throw new AssertionError("[" + parent + "] actual size is " + collection2.size()
						+ ". but expected size is " + collection1.size() + ".");
				}
				for (int i = 0; i < collection1.size(); i++) {
					eq(collection1.toArray()[i], collection2.toArray()[i], parent + "(" + i + ")", db, ignores);
				}
			}
			
			removeIgnoreProp(expected, actual, ignores);
			
			if (db) {
				removeNotTargetDbProp(expected, actual);
			}
			
			//値を直接持たないオブジェクトの場合
			List<String> props1 = PropertyUtil.getPropertyNames(expected.getClass());
			List<String> props2 = PropertyUtil.getPropertyNames(actual.getClass());
			for (String prop : props1) {
				if (props2.contains(prop)) {
					//同一のプロパティが存在する
					Object value1 = PropertyUtil.getPropertyValue(expected, prop);
					Object value2 = PropertyUtil.getPropertyValue(actual, prop);
					eq(value1, value2, parent + "." + prop, db, ignores);
				}
			}
		}
	}


	/**
	 * テストで比較対象にしたくない項目をチェック対象外にする
	 * @param expected expected
	 * @param actual actual
	 * @param ignores ignores
	 */
	private static void removeIgnoreProp(Object expected, Object actual, List<String> ignores) {
		if (ignores != null && !ignores.isEmpty()) {
			for (String string : ignores) {
				// チェック対象外の値を消す
				List<String> props1 = PropertyUtil.getPropertyNames(expected.getClass());
				if (props1.contains(string)) {
					PropertyUtil.setPropertyValue(expected, string, null);
					PropertyUtil.setPropertyValue(actual, string, null);
				}
			}
		}
	}

	/**
	 * DBで自動的に入る項目など、チェック対象外の項目を消す
	 * @param expected expected
	 * @param actual actual
	 */
	private static void removeNotTargetDbProp(Object expected, Object actual) {
		List<String> ignores = new ArrayList<String>();
		ignores.add("insertUser");
		ignores.add("insertDatetime");
		ignores.add("updateUser");
		ignores.add("updateDatetime");
		removeIgnoreProp(expected, actual, ignores);
	}
	
}
