package org.support.project.common.bat;

/**
 * result of job
 * 
 * @author Koda
 *
 */
public class JobResult {
    /** resultCode */
    private int resultCode;
    /** stdout */
    private String stdout;

    /**
     * Constractor
     * @param resultCode resultCode
     * @param stdout stdout
     */
    public JobResult(int resultCode, String stdout) {
        super();
        this.resultCode = resultCode;
        this.stdout = stdout;
    }
    
    /**
     * get resultCode
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }
    /**
     * get stdout
     * @return stdout
     */
    public String getStdout() {
        return stdout;
    }

}
