package org.support.project.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * ファイル操作のユーティリティ
 * 
 * @author Koda
 */
public class FileUtil {
    /**
     * ファイルを削除します ディレクトリの場合、中身をまず削除し、確実に削除ができます。
     * 
     * @param f
     *            file
     */
    public static void delete(File f) {
        if (!f.exists()) {
            return;
        }

        if (f.isFile()) {
            if (!f.delete()) {
                f.deleteOnExit();
            }
        }
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            f.delete();
        }
    }

    /**
     * ファイル名から拡張子を取得する
     * 
     * @param file
     *            ファイル
     * @return 拡張子
     */
    public static String getExtension(File file) {
        return StringUtils.getExtension(file.getPath());
    }

    /**
     * ファイル名称の取得(パスの最後のファイル名)
     * 
     * @param file
     *            ファイル
     * @return ファイル名称
     */
    public static String getFileName(File file) {
        return StringUtils.getFileName(file.getPath());
    }

    /**
     * コピー
     * 
     * @param inputStream
     *            inputStream
     * @param outputStream
     *            outputStream
     * @throws IOException
     *             IOException
     */
    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        // int count = 0;
        byte[] buff = new byte[2048];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
            // count += len;
            outputStream.flush();
        }
        // log.info("copy : " + count + " bytes");
    }

    /**
     * ファイルへテキストを書き込み(UTF-8)
     * 
     * @param file
     *            File
     * @param string
     *            String
     * @throws FileNotFoundException
     *             FileNotFoundException
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     */
    public static void write(File file, String string) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        PrintWriter pw = null;
        try {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            // 出力ストリームの生成（文字コード指定）
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            pw = new PrintWriter(osw);
            // ファイルへの書き込み
            pw.write(string);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * テキストを書き込み
     * 
     * @param out
     *            OutputStream
     * @param string
     *            String
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws IOException
     *             IOException
     */
    public static void write(OutputStream out, String string) throws UnsupportedEncodingException, IOException {
        write(out, string, "UTF-8");
    }

    /**
     * テキストを書き込み
     * 
     * @param out
     *            OutputStream
     * @param string
     *            String
     * @param encode
     *            encode
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws IOException
     *             IOException
     */
    public static void write(OutputStream out, String string, String encode) throws UnsupportedEncodingException, IOException {
        PrintWriter pw = null;
        try {
            // 出力ストリームの生成（文字コード指定）
            OutputStreamWriter osw = new OutputStreamWriter(out, encode);
            pw = new PrintWriter(osw);
            // ファイルへの書き込み
            pw.write(string);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * テキストを読み込み
     * 
     * @param in
     *            InputStream
     * @return String
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws IOException
     *             IOException
     */
    public static String read(InputStream in) throws UnsupportedEncodingException, IOException {
        return read(in, "UTF-8");
    }

    /**
     * テキストを読み込み
     * 
     * @param in
     *            InputStream
     * @param encode
     *            encode
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws IOException
     *             IOException
     * @return string
     */
    public static String read(InputStream in, String encode) throws UnsupportedEncodingException, IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, encode));
            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                builder.append(s);
                builder.append("\n");
            }
            return builder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
