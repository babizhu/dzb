package org.bbz.dzb.module;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.bbz.dzb.service.EnterpriseService;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liulaoye on 16-10-25.
 * EnterpriseModule
 */

@IocBean
@At("/enterprise")

public class EnterpriseModule extends BaseModule{
    @Inject
    protected EnterpriseService enterpriseService;
    @Inject
    protected Dao dao;

    @At
    @GET
    @RequiresUser
    public Object query(
                         HttpServletRequest req,
                         HttpServletResponse response ){

        return enterpriseService.getAll();
    }



}