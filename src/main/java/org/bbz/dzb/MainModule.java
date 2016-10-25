package org.bbz.dzb;

/**
 * Created by liulaoye on 16-10-25.
 */

//import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.*;

@IocBy(args={"*js", "ioc/",
        "*anno", "org.bbz.dzb",
        "*tx",
        "*async"})
@Modules(scanPackage=true)
@SetupBy(MainSetup.class)
@Ok("json:full")
@Fail("http:500")
//@SessionBy(ShiroSessionProvider.class)
public class MainModule {


}