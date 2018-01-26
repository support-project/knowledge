package org.support.project.common.util;

import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

/**
 * システム系のユーティリティクラス
 * @author Koda
 */
public class SystemUtils {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * システム情報を取得
     * 
     * String getArch() オペレーティングシステムのアーキテクチャーを返します。 int getAvailableProcessors() Java 仮想マシンが使用できるプロセッサの数を返します。 String getName() オペレーティングシステム名を返します。
     * double getSystemLoadAverage() 最後の 1 分のシステム負荷平均を返します。 String getVersion() オペレーティングシステムのバージョンを返します。
     * 
     * long getCommittedVirtualMemorySize() プロセスの実行に利用可能な仮想メモリーの容量をバイト単位で返します。この操作がサポートされていない場合は -1 を返します。 long getFreePhysicalMemorySize()
     * 空き物理メモリーの容量をバイト単位で返します。 long getFreeSwapSpaceSize() 空きスワップ空間の容量をバイト単位で返します。 double getProcessCpuLoad() Java 仮想マシンプロセスの「最近の CPU 使用率」を返します。 long
     * getProcessCpuTime() Java 仮想マシンが実行されているプロセスで使用される CPU 時間をナノ秒単位で返します。 double getSystemCpuLoad() システム全体の「最新の CPU 使用率」を返します。 long
     * getTotalPhysicalMemorySize() 物理メモリーの合計容量をバイト単位で返します。 long getTotalSwapSpaceSize() スワップ空間の合計容量をバイト単位
     * 
     * @return system info
     */
    public static String systemInfo() {
        StringBuilder info = new StringBuilder();
        try {
            OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
            info.append("System Info:\n");
            info.append("OS                          : ").append(bean.getName()).append(" ").append(bean.getVersion()).append("\n");
            info.append("Arch                        : ").append(bean.getArch()).append("\n");
            info.append("AvailableProcessors         : ").append(bean.getAvailableProcessors()).append("\n");
            info.append("SystemLoadAverage           : ").append(bean.getSystemLoadAverage()).append("\n");
            if (bean instanceof com.sun.management.OperatingSystemMXBean) {
                com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) bean;
                info.append("CommittedVirtualMemorySize  : ").append(mxbean.getCommittedVirtualMemorySize()).append("\n");
                info.append("FreePhysicalMemorySize      : ").append(mxbean.getFreePhysicalMemorySize()).append("\n");
                info.append("FreeSwapSpaceSize           : ").append(mxbean.getFreeSwapSpaceSize()).append("\n");
                info.append("ProcessCpuLoad              : ").append(mxbean.getProcessCpuLoad()).append("\n");
                info.append("ProcessCpuTime              : ").append(mxbean.getProcessCpuTime()).append("\n");
                info.append("SystemCpuLoad               : ").append(mxbean.getSystemCpuLoad()).append("\n");
                info.append("TotalPhysicalMemorySize     : ").append(mxbean.getTotalPhysicalMemorySize()).append("\n");
                info.append("TotalSwapSpaceSize          : ").append(mxbean.getTotalSwapSpaceSize()).append("\n");
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        return info.toString();
    }
    
    /**
     * 環境変数の値を取得
     * 環境変数が無い場合、System.getPropertyにセットされているかも試す
     * 
     * 存在しない場合、「カラ文字」を返す
     * 
     * @param envKey env key
     * @return env value
     */
    public static String getenv(String envKey) {
        if (StringUtils.isEmpty(envKey)) {
            return "";
        }
        String envValue = System.getenv(envKey);
        if (StringUtils.isEmpty(envValue)) {
            envValue = System.getProperty(envKey, "");
        }
        return envValue;
    }

}
