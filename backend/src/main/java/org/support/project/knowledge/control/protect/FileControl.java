package org.support.project.knowledge.control.protect;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.knowledge.vo.UploadResults;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;

@DI(instance = Instance.Prototype)
public class FileControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
    private UploadedFileLogic fileLogic = UploadedFileLogic.get();

    /**
     * アップロードされたファイルを保存する
     * 
     * @return
     * @throws Exception
     */
    @Post(subscribeToken = "knowledge")
    public Boundary upload() throws Exception {
        int limit = 10;
        SystemConfigsEntity config = SystemConfigsDao.get().selectOnKey(SystemConfig.UPLOAD_MAX_MB_SIZE, AppConfig.get().getSystemName());
        if (config != null && StringUtils.isInteger(config.getConfigValue())) {
            limit = Integer.parseInt(config.getConfigValue());
        }
        
        UploadResults results = new UploadResults();
        List<UploadFile> files = new ArrayList<UploadFile>();
        Object obj = getParam("files[]", Object.class);
        if (obj instanceof FileItem) {
            FileItem fileItem = (FileItem) obj;
            if (fileItem.getSize() > limit * 1024 * 1024) {
                ValidateError error = new ValidateError("errors.maxfilesize", limit + "MB");
                Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
                return send(HttpStatus.SC_400_BAD_REQUEST, msg);
            }
            UploadFile file = fileLogic.saveFile(fileItem, getLoginedUser(), getRequest().getContextPath());
            files.add(file);
        } else if (obj instanceof List) {
            @SuppressWarnings("unchecked")
            List<FileItem> fileItems = (List<FileItem>) obj;
            for (FileItem fileItem : fileItems) {
                if (fileItem.getSize() > limit * 1024 * 1024) {
                    ValidateError error = new ValidateError("errors.maxfilesize", limit + "MB");
                    Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
                    return send(HttpStatus.SC_400_BAD_REQUEST, msg);
                }
                UploadFile file = fileLogic.saveFile(fileItem, getLoginedUser(), getRequest().getContextPath());
                files.add(file);
            }
        }
        results.setFiles(files);
        return send(HttpStatus.SC_200_OK, results);
    }
    
    
    /**
     * アップロードされたファイルを保存する
     * 
     * 画面でプレビューを行った画像のアップロード。クリップボードから張り付けができる。
     * Base64形式で送られる（multipart-formdataでは無い）ので、デコードして保存する。
     * 
     * @return
     * @throws Exception
     */
    @Post(subscribeToken = "knowledge")
    public Boundary imgupload() throws Exception {
        int limit = 10;
        SystemConfigsEntity config = SystemConfigsDao.get().selectOnKey(SystemConfig.UPLOAD_MAX_MB_SIZE, AppConfig.get().getSystemName());
        if (config != null && StringUtils.isInteger(config.getConfigValue())) {
            limit = Integer.parseInt(config.getConfigValue());
        }

        UploadResults results = new UploadResults();
        List<UploadFile> files = new ArrayList<UploadFile>();
        
        String fileimg = getParam("fileimg");
        if (StringUtils.isEmpty(fileimg)) {
            ValidateError error = new ValidateError("errors.required", "Image");
            Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
            return send(HttpStatus.SC_400_BAD_REQUEST, msg);
        }
        
        if (fileimg.startsWith("data:image/png;base64,")) {
            fileimg = fileimg.substring("data:image/png;base64,".length());
            byte[] img = Base64.decodeBase64(fileimg);
            
            if (img.length > limit * 1024 * 1024) {
                ValidateError error = new ValidateError("errors.maxfilesize", limit + "MB");
                Msg msg = new Msg(error.getMsg(HttpUtil.getLocale(getRequest())));
                return send(HttpStatus.SC_400_BAD_REQUEST, msg);
            }
            
            UploadFile file = fileLogic.saveFile(img, getLoginedUser(), getRequest().getContextPath());
            files.add(file);
            results.setFiles(files);
            return send(HttpStatus.SC_200_OK, results);
        }
        return send(HttpStatus.SC_400_BAD_REQUEST, "data error");
    }
    
    

    @Delete(subscribeToken = "knowledge")
    public JsonBoundary delete() throws Exception {
        LOG.trace("delete()");
        Long fileNo = getPathLong();
        KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
        if (entity == null) {
            // 既に削除済
            return send(HttpStatus.SC_200_OK, "success: " + fileNo);
        }
        
        if (StringUtils.isEmpty(entity.getKnowledgeId()) || entity.getKnowledgeId() == 0) {
            // 下書き中は、登録者のみ削除可能
            if (entity.getInsertUser().intValue() != getLoginUserId().intValue()) {
                LOG.info("Login user is not created user.");
                return send(HttpStatus.SC_400_BAD_REQUEST, "fail: " + fileNo);
            }
        } else {
            if (!KnowledgeLogic.get().isEditor(getLoginedUser(), entity.getKnowledgeId())) {
                // アクセス権が無い
                return send(HttpStatus.SC_400_BAD_REQUEST, "fail: " + fileNo);
            }
        }
        // 削除実行
        fileLogic.removeFile(fileNo);
        return send(HttpStatus.SC_200_OK, "success: " + fileNo);
    }
    
    
    
    

}
