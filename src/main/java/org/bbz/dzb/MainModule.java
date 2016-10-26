package org.bbz.dzb;

import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.*;
/**
 * Created by liulaoye on 16-10-25.
 * MainModule
 */


@SetupBy(MainSetup.class)
@IocBy(args = {"*js", "ioc/",
        "*anno", "org.bbz.dzb",
        "*tx",
        "*async"})
@Modules(scanPackage = true)
@ChainBy(args = "mvc/chain.js")
@Ok("json:full")
@Fail("http:500")
@SessionBy(ShiroSessionProvider.class)
public class MainModule {


}