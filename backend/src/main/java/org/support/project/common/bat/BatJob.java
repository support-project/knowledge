package org.support.project.common.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * Javaからコマンドラインのバッチ処理を呼び出すクラス
 * 
 * @author Koda
 */
@DI(instance = Instance.Prototype)
public class BatJob implements Job {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** バッチのパラメータのリスト */
    private List<String> list = new ArrayList<>();

    /** 標準出力の値を格納するバッファ */
    private StringBuilder out = new StringBuilder();
    /** 環境変数 */
    private Map<String, String> environment = new LinkedHashMap<>();
    /** カレントディレクトリ */
    private File currentDirectory = null;
    /** ジョブのタイムアウト(ミリ秒 / 0以下でタイムアウトしない) */
    private int timeOutMilliSecond = -1;

    /** コンソール出力を渡すリスナー */
    private ConsoleListener consoleListener = null;

    /**
     * Get instance.
     * 
     * @return BatJob instance.
     */
    public static BatJob get() {
        return Container.getComp(BatJob.class);
    }

    /**
     * Add command.
     * 
     * @param commands
     *            commands
     * @return myself
     */
    public BatJob addCommand(String... commands) {
        for (String command : commands) {
            this.list.add(command);
        }
        return this;
    }

    /**
     * Add commands.
     * 
     * @param commands
     *            commands
     * @return myself
     */
    public BatJob addCommand(List<String> commands) {
        for (String command : commands) {
            this.list.add(command);
        }
        return this;
    }

    /**
     * Add env value.
     * 
     * @param key
     *            key
     * @param value
     *            value
     * @return myself
     */
    public BatJob addEnvironment(String key, String value) {
        this.environment.put(key, value);
        return this;
    }

    /**
     * カレントディレクトリを指定
     * 
     * @param currentDirectory
     *            currentDirectory
     * @return myself
     */
    public BatJob setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
        return this;
    }

    /**
     * ジョブのタイムアウト(ミリ秒 / 0以下でタイムアウトしない)を設定
     * 
     * @param timeOutMilliSecond
     *            timeOutMilliSecond
     * @return myself
     */
    public BatJob setTimeOutMilliSecond(int timeOutMilliSecond) {
        this.timeOutMilliSecond = timeOutMilliSecond;
        return this;
    }

    /**
     * execute
     * 
     * @return JobResult
     * @throws IOException
     *             IOException
     * @throws InterruptedException
     *             InterruptedException
     */
    public JobResult execute() throws IOException, InterruptedException {
        if (list == null || list.isEmpty()) {
            throw new SystemException("param list is wrong.");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(StringUtils.join(list, " "));
        }

        ProcessBuilder pb = new ProcessBuilder(list);
        printProcess(list);

        /*
         * 参考：http://www.ne.jp/asahi/hishidama/home/tech/java/process.html#Process 標準出力／エラー出力を別々に取得しようとすると、プログラムの処理がブロッキングに入り、 終了しなくなることがある。
         * 標準出力／エラー出力を別々のスレッドにすれば取得できるが、Webアプリなどでは、 新たにスレッドを生成することができない(エラーになる)。 このため、標準出力／エラー出力は同じストリームで取得する。
         */
        pb.redirectErrorStream(true); //// デフォルトはfalse：マージしない（標準出力と標準エラーは別々）

        // 環境変数にセット
        Map<String, String> env = pb.environment(); // 環境変数を取得
        Iterator<String> iterator = environment.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = environment.get(key);
            env.put(key, value);
        }

        // カレントディレクトリ指定
        if (currentDirectory != null) {
            pb.directory(currentDirectory);
        }

        Process p = null;
        try {
            p = pb.start();
            InputStream is = p.getInputStream();

            int result = -99;
            if (this.timeOutMilliSecond > 0) {
                long tm = System.currentTimeMillis();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
                try {
                    while (true) {
                        if (System.currentTimeMillis() - tm > this.timeOutMilliSecond) { // タイムアウトチェック
                            p.destroy(); // プロセス終了
                            break;
                        }
                        if (br.ready()) {
                            // br.read();// readyを使って出力可能か判定しながら吸い出す
                            String line = br.readLine();
                            out.append(line);
                            LOG.debug(line);
                            if (consoleListener != null) {
                                consoleListener.write(line);
                            }
                        }
                        // try { // 終了チェック
                        // result = p.exitValue();
                        // // end process
                        // break;
                        // } catch (IllegalThreadStateException ex) {
                        // // running
                        // Thread.sleep(100);
                        // }

                        if (p.isAlive()) {
                            Thread.sleep(100);
                        } else {
                            result = p.exitValue();
                            break;
                        }
                    }
                } finally {
                    br.close();
                }

                // MEMO Java8からのタイムアウトはタイムアウトしない、、、
                // // Java8からタイムアウトが実装されたので、それを使う
                // printInputStream(is);
                // boolean end = p.waitFor(timeOutMilliSecond, TimeUnit.MILLISECONDS);
                // if (end) {
                // result = p.exitValue();
                // } else {
                // LOG.info("process timeout");
                // p.destroy(); // プロセスを強制終了
                // }
            } else {
                printInputStream(is);
                p.waitFor(); // 終了まで待機 ←必要無し(ストリームの読み込みが終了するとき、起動したプロセスが終了)
                result = p.exitValue();
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("[Process exit] " + result);
            }
            return new JobResult(result, out.toString());
        } finally {
            if (p != null) {
                // p.getInputStream().close();
                try {
                    p.getInputStream().close();
                } catch (IOException e) {
                    LOG.debug(e.getMessage());
                }
                p.getErrorStream().close();
                p.getOutputStream().close();
                p.destroy();
            }
        }

    }

    /**
     * 実行するプロセスを出力
     * 
     * @param list
     */
    private void printProcess(List<String> list) {
        if (LOG.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("[Process start]");
            for (String string : list) {
                builder.append(string).append(" ");
            }
            LOG.debug(builder.toString());
        }
    }

    /**
     * 標準出力をログに出力
     * 
     * @param is
     * @throws IOException
     */
    private void printInputStream(InputStream is) throws IOException {
        LOG.debug("[Process output start]");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
        // BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("MS932")));
        try {
            for (;;) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                out.append(line).append("\n");
                LOG.debug("  " + line);
                if (consoleListener != null) {
                    consoleListener.write(line);
                }
            }
        } finally {
            br.close();
        }
        LOG.debug("[Process output end]");
    }

    /**
     * @param consoleListener
     *            the consoleListener to set
     */
    public void setConsoleListener(ConsoleListener consoleListener) {
        this.consoleListener = consoleListener;
    }

}
