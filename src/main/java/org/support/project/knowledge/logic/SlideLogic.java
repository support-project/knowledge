package org.support.project.knowledge.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
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
        String slidePath = AppConfig.get().getSlidePath();
        File dir = new File(slidePath);
        if (!dir.exists()) {
            return slideInfo;
        }
        File slideDir = new File(dir, fileNo);
        if (!slideDir.exists()) {
            return slideInfo;
        }
        File[] slides = slideDir.listFiles();
        if (slides != null) {
            List<String> files = new ArrayList<>();
            for (File file : slides) {
                files.add(FileUtil.getFileName(file));
            }
            slideInfo.setFiles(files);
        }
        return slideInfo;
    }
    
    
    public DownloadInfo getSlideImage(String fileNo, String slideImage, LoginedUser loginedUser) {
        // TODO Auto-generated method stub
        return null;
    }

}
