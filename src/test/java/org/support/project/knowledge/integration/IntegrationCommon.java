package org.support.project.knowledge.integration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.AuthType;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.exception.CallControlException;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.CallControlLogic;

import net.arnx.jsonic.JSONException;

public abstract class IntegrationCommon extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationCommon.class);
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        String controlPackage = "org.support.project.knowledge.control,org.support.project.web.control";
        String classSuffix = "Control";
        String ignoreRegularExpression = "^/ws|^/template|^/bower|css$|js$|ico$|html$";
        CallControlLogic.get().init(controlPackage, classSuffix, true, ignoreRegularExpression);
        
        MailConfigsEntity mailConfig = new MailConfigsEntity(AppConfig.get().getSystemName());
        mailConfig.setHost("example.com");
        mailConfig.setPort(25);
        mailConfig.setAuthType(AuthType.None.getValue());
        MailConfigsDao.get().insert(mailConfig); // メール送信設定
    }
    /**
     * コントローラーを呼び出し
     * @param request
     * @param response
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws JSONException
     * @throws NoSuchAlgorithmException
     * @throws CallControlException
     * @throws IOException
     * @throws InvalidParamException
     */
    protected <T> T invoke(HttpServletRequest request, HttpServletResponse response, Class<T> clazz)
            throws InstantiationException, IllegalAccessException, JSONException, NoSuchAlgorithmException,
            CallControlException, IOException, InvalidParamException {
        InvokeTarget invoke = CallControlLogic.get().searchInvokeTarget(request, response);
        Assert.assertNotNull(invoke);
        Object result = invoke.invoke();
        LOG.info(result);
        Assert.assertNotNull(result);
        if (!result.getClass().isAssignableFrom(clazz)) {
            Assert.fail("Result is not " + clazz.getSimpleName());
        }
        return (T) result;
    }

}
