package org.support.project.knowledge.control.api.internal;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.logic.invoke.Close;
import org.support.project.web.logic.invoke.CloseAble;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class SampleInternalApiControl extends Control {
    /**
     * サンプル
     */
    @Get(path="_api/sample", publishToken="hogefuga")
    @Close
    public Boundary sample() {
        Msg msg = new Msg("Hi, " + getLoginedUser().getUserInfomation().getUserName()  + ", this is internal api sample.");
        return send(msg);
    }
    /**
     * サンプル
     */
    @Get(path="_api/sample2")
    @CloseAble
    public Boundary sample2() {
        String username = "anonymous";
        if (getLoginedUser() != null) {
            username = getLoginedUser().getUserInfomation().getUserName();
        }
        Msg msg = new Msg("Hi, " + username + ", this is internal api sample2.");
        return send(msg);
    }
    /**
     * サンプル
     */
    @Get(path="_api/sample3")
    @Open
    public Boundary sample3() {
        String username = "anonymous";
        if (getLoginedUser() != null) {
            username = getLoginedUser().getUserInfomation().getUserName();
        }
        Msg msg = new Msg("Hi, " + username + ", this is internal api sample3.");
        return send(msg);
    }
    /**
     * サンプル
     */
    @Post(path="_api/samplepost", subscribeToken="hogefuga")
    @Close
    public Boundary samplepost() {
        String username = "anonymous";
        if (getLoginedUser() != null) {
            username = getLoginedUser().getUserInfomation().getUserName();
        }
        Msg msg = new Msg("Hi, " + username + ", this is internal api post sample.");
        return send(msg);
    }

}
