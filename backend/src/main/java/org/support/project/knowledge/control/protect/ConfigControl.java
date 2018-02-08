package org.support.project.knowledge.control.protect;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;

@DI(instance = Instance.Prototype)
public class ConfigControl extends Control {

    @Get
    public Boundary index() {
        List<LdapConfigsEntity> ldapConfigs = LdapConfigsDao.get().selectAll();
        Boolean ldapExists = ldapConfigs.size() > 0;
        setAttribute("ldapExists", ldapExists);
        return forward("index.jsp");
    }
}
