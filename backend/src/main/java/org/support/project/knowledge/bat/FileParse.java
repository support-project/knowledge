package org.support.project.knowledge.bat;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.parser.SlideShowParser;
import org.support.project.knowledge.parser.SlideShowParserFactory;

/**
 * スライドに表示する画像の抽出が、サーバーで実施するとうまくいかない事が多い
 * ローカルで実施できるようにツール化
 * 
 * @author Koda
 */
public class FileParse {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private static final String EXPORT_DIR = "/data/temp/slide/exports";
    private static final String FILE_PATH = "/data/temp/slide/hoge.pdf";
    private static final int FILE_NO = 999;
    
    public static void main(String[] main) throws IOException, ParseException {
        File slide = new File(FILE_PATH);
        LOG.info(FILE_PATH + " のスライドを抽出します");
        SlideShowParser showParser = SlideShowParserFactory.getParser(slide.getName());
        if (showParser != null) {
            File dir = new File(EXPORT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File slideDir = new File(dir, String.valueOf(FILE_NO));
            if (!slideDir.exists()) {
                slideDir.mkdirs();
            }
            showParser.parse(slide, slideDir);
            LOG.info(FILE_PATH + " のスライド抽出が完了しました");
        } else {
            LOG.info(FILE_PATH + " はスライドの抽出の対象ではありません");
        }
    }

}
