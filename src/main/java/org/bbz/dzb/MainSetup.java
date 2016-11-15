package org.bbz.dzb;

import org.bbz.dzb.bean.Enterprise;
import org.bbz.dzb.bean.User;
import org.bbz.dzb.service.EnterpriseService;
import org.bbz.dzb.service.UserService;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * Created by liulaoye on 16-10-25.
 * MainSetup
 */
public class MainSetup implements Setup{
    public void init( NutConfig conf ){
        System.out.println("MainSetup.init()");
        Ioc ioc = conf.getIoc();
        Dao dao = ioc.get( Dao.class );
        Daos.createTablesInPackage( dao, "org.bbz.dzb", false );

        // 初始化默认根用户
        if (dao.count(User.class) == 0) {
            UserService us = ioc.get(UserService.class);
            us.add("admin", "123456");
        }

        if (dao.count(Enterprise.class) == 0) {
            EnterpriseService service = ioc.get(EnterpriseService.class);
            service.add("金蜜蜂", "一家装修公司","重庆市南岸区茶园玉马路");
            service.add("唐卡", "大数据解决方案提供商","重庆市渝中区解放西路45#");
        }

//        CREATE
//
//        VIEW `enterprise_view` AS
//                (SELECT
//                        `enterprise`.`id` AS `id`,
//        `enterprise`.`name` AS `name`,
//        `enterprise`.`address` AS `address`,
//        `enterprise`.`mapAddress` AS `mapAddress`,
//        `enterprise`.`contact` AS `contact`,
//        `enterprise`.`description` AS `description`,
//        `enterprise`.`ct` AS `ct`,
//        `enterprise`.`ut` AS `ut`,
//        `enterprise`.`mapX` AS `mapX`,
//        `enterprise`.`mapY` AS `mapY`,
//        `enterprise`.`areaType` AS `areaType`,
//        `enterprise`.`linkId` AS `linkId`,
//        `b`.`yearnum` AS `yearnum`,
//        `b`.`yearvalue` AS `yearvalue`
//        FROM
//                (`enterprise`
//                        LEFT JOIN `qyyxb` `b` ON ((`enterprise`.`linkId` = `b`.`id`))))


//        CREATE TABLE `dzb_map`.`qyyxb` (
//        `id` INT NOT NULL,
//        `yearnum` INT NULL,
//        `yearvalue` INT NULL,
//        PRIMARY KEY (`id`));
    }

    public void destroy( NutConfig nc ){

    }
}
