package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.Compare;
import org.support.project.common.util.FileUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.bat.FileParseBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.parser.SlideShowParser;
import org.support.project.knowledge.parser.SlideShowParserFactory;
import org.support.project.knowledge.vo.SlideInfo;
import org.support.project.web.bean.DownloadInfo;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class SlideLogic {
    public static final long FILE_NO_NOT_PARSED = -1;
    public static final long FILE_NO_ERROR_PARED = -2;
    
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SlideLogic.class);
    /**
     * インスタンスを取得
     * @return
     */
    public static SlideLogic get() {
        return Container.getComp(SlideLogic.class);
    }
    
    /**
     * スライド格納ディレクトリの初期化
     * @throws IOException 
     */
    public void initSlideDir() throws IOException {
        String slidePath = AppConfig.get().getSlidePath();
        File dir = new File(slidePath);
        if (!dir.exists()) {
            dir.mkdirs();
            LOG.info("Create slide dir. " + dir.getAbsolutePath());
        }
        File slideDir = new File(dir, String.valueOf(FILE_NO_NOT_PARSED));
        if (!slideDir.exists()) {
            slideDir.mkdirs();
        }
        File waitimg = new File(slideDir, "wait_parsing.png");
        if (!waitimg.exists()) {
            InputStream inputStream = this.getClass().getResourceAsStream("/org/support/project/knowledge/logic/wait_parsing.png");
            OutputStream outputStream = new FileOutputStream(waitimg);
            try {
                FileUtil.copy(inputStream, outputStream);
            } finally {
                inputStream.close();
                outputStream.close();
            }
        }
        
        slideDir = new File(dir, String.valueOf(FILE_NO_ERROR_PARED));
        if (!slideDir.exists()) {
            slideDir.mkdirs();
        }
        File errorimg = new File(slideDir, "error_parsed.png");
        if (!errorimg.exists()) {
            InputStream inputStream = this.getClass().getResourceAsStream("/org/support/project/knowledge/logic/error_parsed.png");
            OutputStream outputStream = new FileOutputStream(errorimg);
            try {
                FileUtil.copy(inputStream, outputStream);
            } finally {
                inputStream.close();
                outputStream.close();
            }
        }
    }
    
    
    
    /**
     * スライドから画像を抽出し、スライドディレクトリに格納
     * @param file
     * @param knowledgeFilesEntity
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public boolean parseSlide(File file, KnowledgeFilesEntity knowledgeFilesEntity) throws IOException, ParseException {
        // スライドだったら画像を抽出
        SlideShowParser showParser = SlideShowParserFactory.getParser(knowledgeFilesEntity.getFileName());
        if (showParser != null) {
            initSlideDir();
            String slidePath = AppConfig.get().getSlidePath();
            File dir = new File(slidePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File slideDir = new File(dir, String.valueOf(knowledgeFilesEntity.getFileNo()));
            if (!slideDir.exists()) {
                slideDir.mkdirs();
            }
            showParser.parse(file, slideDir);
            return true;
        }
        return false;
    }
    /**
     * スライドショウの情報を取得
     * @param fileNo
     * @param loginedUser
     * @return
     */
    public SlideInfo getSlideInfo(String fileNo, LoginedUser loginedUser) {
        SlideInfo slideInfo = new SlideInfo();
        
        KnowledgeFilesEntity entity = KnowledgeFilesDao.get().selectOnKeyWithoutBinary(new Long(fileNo));
        if (entity == null) {
            return null;
        }
        // 権限チェック
        if (entity.getKnowledgeId() != null && 0 != entity.getKnowledgeId().longValue()) {
            // ナレッジに紐づいている場合、そのナレッジにアクセスできれば添付ファイルにアクセス可能
            KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
            if (knowledgeLogic.select(entity.getKnowledgeId(), loginedUser) == null) {
                return null;
            }
        } else {
            // ナレッジに紐づいていない場合、登録者のみがアクセス可能
            if (loginedUser == null) {
                return null;
            }
            if (entity.getInsertUser().intValue() != loginedUser.getUserId().intValue()) {
                return null;
            }
        }
        slideInfo.setParseStatus(entity.getParseStatus());
        slideInfo.setFileNo(entity.getFileNo());
        
        if (Compare.equal(entity.getParseStatus(), FileParseBat.PARSE_STATUS_ERROR_FINISHED)) {
            // パースがエラーになった
            LOG.debug("error parsed.");
            slideInfo.setFileNo(FILE_NO_ERROR_PARED);
        } else if (Compare.equal(entity.getParseStatus(), FileParseBat.PARSE_STATUS_WAIT)) {
            // パースがエラーになった
            LOG.debug("wait parsing.");
            slideInfo.setFileNo(FILE_NO_NOT_PARSED);
        }
        
        String slidePath = AppConfig.get().getSlidePath();
        File dir = new File(slidePath);
        if (!dir.exists()) {
            return slideInfo;
        }
        File slideDir = new File(dir, String.valueOf(slideInfo.getFileNo()));
        if (!slideDir.exists()) {
            return slideInfo;
        }
        File[] slides = slideDir.listFiles();
        if (slides != null) {
            List<String> files = new ArrayList<>();
            for (File file : slides) {
                files.add(FileUtil.getFileName(file));
            }
            Collections.sort(files, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    Integer num1 = new Integer(o1.substring(0, o1.indexOf(".")));
                    Integer num2 = new Integer(o2.substring(0, o2.indexOf(".")));
                    return num1.compareTo(num2);
                }
            });
            
            slideInfo.setFiles(files);
        }
        return slideInfo;
    }
    
    /**
     * 画像取得
     * @param fileNo
     * @param slideImage
     * @param loginedUser
     * @return
     * @throws FileNotFoundException
     */
    public DownloadInfo getSlideImage(String fileNo, String slideImage, LoginedUser loginedUser) throws FileNotFoundException {
        if (FILE_NO_NOT_PARSED != new Long(fileNo).longValue() 
                && FILE_NO_ERROR_PARED != new Long(fileNo).longValue()) {
            KnowledgeFilesEntity entity = KnowledgeFilesDao.get().selectOnKeyWithoutBinary(new Long(fileNo));
            if (entity == null) {
                return null;
            }
            // 権限チェック
            if (entity.getKnowledgeId() != null && 0 != entity.getKnowledgeId().longValue()) {
                // ナレッジに紐づいている場合、そのナレッジにアクセスできれば添付ファイルにアクセス可能
                KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
                if (knowledgeLogic.select(entity.getKnowledgeId(), loginedUser) == null) {
                    return null;
                }
            } else {
                // ナレッジに紐づいていない場合、登録者のみがアクセス可能
                if (loginedUser == null) {
                    return null;
                }
                if (entity.getInsertUser().intValue() != loginedUser.getUserId().intValue()) {
                    return null;
                }
            }
        }
        
        String slidePath = AppConfig.get().getSlidePath();
        File dir = new File(slidePath);
        if (!dir.exists()) {
            return null;
        }
        File slideDir = new File(dir, fileNo);
        if (!slideDir.exists()) {
            return null;
        }
        File file = new File(slideDir, slideImage);
        if (!file.exists()) {
            return null;
        }
        DownloadInfo down = new DownloadInfo();
        down.setContentType("image/png");
        down.setFileName(slideImage);
        down.setSize(file.length());
        down.setInputStream(new FileInputStream(file));
        return down;
    }


}
