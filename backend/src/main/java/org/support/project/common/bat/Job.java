package org.support.project.common.bat;

/**
 * ジョブ
 */
public interface Job {
    /**
     * ジョブを実行
     * 
     * @return ジョブの実行結果
     * @throws Exception
     *             Exception
     */
    JobResult execute() throws Exception;
}
