package org.support.project.knowledge.control.admin;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.PinsDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.PinsEntity;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class PinControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PinControl.class);
    /**
     * ピンどめ管理画面表示
     * @return
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary index() {
        return forward("index.jsp");
    }
    /**
     * ピンどめの一覧を取得
     * @return
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary list() {
        List<PinsEntity> pins = PinsDao.get().selectAll();
        return send(pins);
    }
    
    /**
     * ピンどめの一覧を取得
     * @return
     */
    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary add() throws InvalidParamException {
        Long id = super.getPathLong();
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(id);
        if (knowledge == null) {
            return sendMsg(MessageStatus.Warning, HttpStatus.SC_400_BAD_REQUEST,
                    "BAD REQUEST",
                    "knowledge.admin.pin.not.exist.id");

        }
        PinsEntity pin = PinsDao.get().getPinByAdmin(id);
        if (pin == null) {
            PinsDao.get().insertPinByAdmin(id);
        }
        return send("OK");
    }
    /**
     * ピンどめの一覧を取得
     * @return
     */
    @Delete(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary remove() throws InvalidParamException {
        Long id = super.getPathLong();
        PinsEntity pin = PinsDao.get().getPinByAdmin(id);
        if (pin != null) {
            PinsDao.get().delete(pin);
        }
        return send("OK");
    }
    
    
}
