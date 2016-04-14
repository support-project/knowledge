package org.support.project.knowledge.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.universalchardet.UniversalDetector;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.vo.ParseResult;
import org.support.project.knowledge.vo.Sentence;

/**
 * テキストファイル用のクローラー
 */
@DI(instance = Instance.Prototype)
public class TextParser implements Parser {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(TextParser.class);

    public static TextParser get() {
        return Container.getComp(TextParser.class);
    }

    public List<Sentence> parse(File file, String encode) throws Exception {
        BufferedReader reader = null;
        List<Sentence> sentenceInFileVos = new ArrayList<Sentence>();
        try {

            reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StringUtils.isEmpty(encode) ? this.getEncoding(file) : encode));
            String line;
            int count = 1;
            // 読み込みデータがなくなるまで、読み込み
            while ((line = reader.readLine()) != null) {
                Sentence sentence = new Sentence();
                sentence.setPlace(count + " 行目");
                sentence.setSentence(line);
                sentenceInFileVos.add(sentence);
                count++;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return sentenceInFileVos;
    }

    /**
     * テキストの文字コードを判定する 判定にはjuniversalchardet-1.0.3.jarを用いている
     * 
     * @param file
     * @return
     * @throws IOException
     */
    private String getEncoding(File file) throws IOException {
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        UniversalDetector detector = null;
        try {
            fis = new FileInputStream(file);
            detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();

            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                LOG.debug("Detected encoding = " + encoding);
            } else {
                LOG.warn("No encoding detected. set default UTF-8");
                encoding = "UTF-8";
            }
            return encoding;
        } finally {
            if (detector != null) {
                detector.reset();
            }
            if (fis != null) {
                fis.close();
            }

        }

    }

    @Override
    public ParseResult parse(File file) throws Exception {
        String encode = this.getEncoding(file);
        List<Sentence> sentences = parse(file, encode);

        ParseResult parseResult = new ParseResult();
        parseResult.setSentences(sentences);

        StringBuilder builder = new StringBuilder();
        for (Sentence sentence : sentences) {
            builder.append(sentence.getSentence());
        }
        parseResult.setText(builder.toString());
        return parseResult;
    }

}
