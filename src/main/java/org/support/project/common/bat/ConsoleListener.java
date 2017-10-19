package org.support.project.common.bat;

/**
 * Listener for console out event
 * @author Koda
 */
public interface ConsoleListener {
	
	/**
	 * バッチでコンソールに出力された際に呼ばれるリスナー
	 * @param message std out message
	 */
	void write(String message);
}
