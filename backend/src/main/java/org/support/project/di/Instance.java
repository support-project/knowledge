package org.support.project.di;


/**
 * インスタンスを表す列挙型
 *
 */
public enum Instance {
	/** 唯一のインスタンス */
	Singleton,
	
	/** 必要とされる度に異なるインスタンス */
	Prototype
	
}

