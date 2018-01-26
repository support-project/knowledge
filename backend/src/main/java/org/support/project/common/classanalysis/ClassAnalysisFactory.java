package org.support.project.common.classanalysis;

import java.util.HashMap;
import java.util.Map;

public class ClassAnalysisFactory {
	
	/**
	 * クラスの解析結果を保持するマップ
	 */
	private static Map<Class<?>, ClassAnalysis> classAnalysisMap = null;
	
	/**
	 * クラスの解析結果を保持するマップを取得
	 * @return
	 */
	private static Map<Class<?>, ClassAnalysis> getClassAnalysisMap() {
		if (classAnalysisMap == null) {
			classAnalysisMap = new HashMap<Class<?>, ClassAnalysis>();
		}
		return classAnalysisMap;
	}

	
	/**
	 * クラスの解析結果を取得
	 * @param clazz クラス
	 * @return 解析結果
	 */
	public static ClassAnalysis getClassAnalysis(Class<?> clazz) {
		Map<Class<?>, ClassAnalysis> classAnalysisMap = getClassAnalysisMap();
		if (!classAnalysisMap.containsKey(clazz)) {
			ClassAnalysis analysis = new ClassAnalysis(clazz);
			classAnalysisMap.put(clazz, analysis);
		}
		ClassAnalysis analysis = classAnalysisMap.get(clazz);
		return analysis;
	}
	
	
}
