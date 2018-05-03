package org.support.project.common.classanalysis;

import java.util.HashMap;
import java.util.Map;

public class ClassAnalysisForDIFactory {
	/**
	 * クラスの解析結果を保持するマップ
	 */
	private static Map<Class<?>, ClassAnalysisForDI> classAnalysisMap = null;
	
	/**
	 * クラスの解析結果を保持するマップを取得
	 * @return
	 */
	private static Map<Class<?>, ClassAnalysisForDI> getClassAnalysisMap() {
		if (classAnalysisMap == null) {
			classAnalysisMap = new HashMap<Class<?>, ClassAnalysisForDI>();
		}
		return classAnalysisMap;
	}

	
	/**
	 * クラスの解析結果を取得
	 * @param clazz クラス
	 * @return 解析結果
	 */
	public static ClassAnalysisForDI getClassAnalysis(Class<?> clazz) {
		Map<Class<?>, ClassAnalysisForDI> classAnalysisMap = getClassAnalysisMap();
		if (!classAnalysisMap.containsKey(clazz)) {
			ClassAnalysisForDI analysis = new ClassAnalysisForDI(clazz);
			classAnalysisMap.put(clazz, analysis);
		}
		ClassAnalysisForDI analysis = classAnalysisMap.get(clazz);
		return analysis;
	}
}
