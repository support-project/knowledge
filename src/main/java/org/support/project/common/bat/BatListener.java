package org.support.project.common.bat;

/**
 * Listener for batch event
 * @author Koda
 */
public interface BatListener {
	
	/**
	 * バッチを非同期(別Thread)で実行した際の処理終了後に呼ばれるリスナー
	 * @param result result
	 */
	void finish(JobResult result);
	
	
}
