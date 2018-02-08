package org.support.project.common.classanalysis;

import java.util.List;

public class ClassAnalysisTestTarget {
	
	public String string1;
	
	private int num1;

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}
	
	
	public List<String> strings;
	
	
	private List<Integer> integers;

	public List<Integer> getIntegers() {
		return integers;
	}

	public void setIntegers(List<Integer> integers) {
		this.integers = integers;
	}
	
	private ClassAnalysisTestTarget target;

	public ClassAnalysisTestTarget getTarget() {
		return target;
	}

	public void setTarget(ClassAnalysisTestTarget target) {
		this.target = target;
	}
	
	
	public String[] strings2;
	
	
	private int[] is;

	public int[] getIs() {
		return is;
	}

	public void setIs(int[] is) {
		this.is = is;
	}
	
	
}
