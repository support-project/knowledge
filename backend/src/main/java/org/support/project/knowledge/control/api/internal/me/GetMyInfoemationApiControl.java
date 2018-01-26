package org.support.project.knowledge.control.api.internal.me;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.vo.api.Config;
import org.support.project.knowledge.vo.api.User;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.UserConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UserConfigsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Prototype)
public class GetMyInfoemationApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * ログインしている自分のユーザ情報を取得
     * @throws Exception
     */
    @Get(path = "_api/me")
    public Boundary execute() throws Exception {
        LOG.trace("call _api/users");
        UsersEntity user = UsersDao.get().selectOnKey(getLoginUserId());
        if (user == null) {
            return send(HttpStatus.SC_403_FORBIDDEN, new Msg("FORBIDDEN"));
        }
        User u = new User();
        PropertyUtil.copyPropertyValue(user, u, true);
        if (StringUtils.isEmpty(u.getLocaleKey())) {
            u.setLocaleKey("en");
        }
        List<UserConfigsEntity> userConfigs = UserConfigsDao.get().selectOnUserId(getLoginUserId());
        List<Config> configs = new ArrayList<>();
        for (UserConfigsEntity userConfigsEntity : userConfigs) {
            Config config = new Config();
            PropertyUtil.copyPropertyValue(userConfigsEntity, config, true);
            configs.add(config);
        }
        u.setConfigs(configs);
        return send(HttpStatus.SC_200_OK, u);
    }
}
