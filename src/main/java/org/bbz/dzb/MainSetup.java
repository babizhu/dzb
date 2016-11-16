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
//            service.add("金蜜蜂", "一家装修公司","重庆市南岸区茶园玉马路");
//            service.add("唐卡", "大数据解决方案提供商","重庆市渝中区解放西路45#");
            service.add("恒远晋通","","",0);
            service.add("鹏忆南联","","",0);
            service.add("普瑞晨","","",0);
            service.add("融讯实业","","",0);
            service.add("融讯供应链","","",0);
            service.add("宝捷讯","","",0);
            service.add("迪马工业","","",0);
            service.add("迪马专用车","","",0);
            service.add("兴汇通","","",0);
            service.add("明烁科技","","",0);
            service.add("乐话科技","","",0);
            service.add("烽昱科技","","",0);
            service.add("酷动电子","","",0);
            service.add("岐创光电","","",0);
            service.add("德炬实业","","",0);
            service.add("庭美科技","","",0);
            service.add("诺耀科技","","",0);
            service.add("中交兴路","","",0);
            service.add("中交兴路供应链","","",0);
            service.add("冠耀科技","","",0);
            service.add("景盛嘉年贸易","","",0);
            service.add("交凯信息","","",0);
            service.add("中科诺","","",0);
            service.add("长兴光电","","",0);
            service.add("天迈通讯","","",0);
            service.add("会凌电子","","",0);
            service.add("盟讯电子","","",0);
            service.add("翌辰光电","","",0);
            service.add("华森心时代","","",0);
            service.add("三木供应链","","",0);
            service.add("鸣洋警安","","",0);
            service.add("恒一通信","","",0);
            service.add("元征科技","","",0);
            service.add("中移物联网","","",0);
            service.add("高讯通讯","","",0);
            service.add("电信研究院","","",0);
            service.add("组源通信","","",0);
            service.add("鼎腾通讯","","",0);
            service.add("锐迪科","","",0);
            service.add("物联利浪","","",0);
            service.add("联合利丰","","",0);
            service.add("五锋科技","","",0);
            service.add("智慧思特","","",0);
            service.add("中交通信","","",0);
            service.add("闻泰","","",0);
            service.add("泓瀚","","",0);
            service.add("美景光电","","",0);
            service.add("欧胜德","","",0);
            service.add("正天环保","","",0);
            service.add("三峡电缆","","",0);
            service.add("国信通","","",0);
            service.add("国力通","","",0);
            service.add("兴伟顺电子","","",0);
            service.add("东昇智豆","","",0);

            service.add("康倍达","","",1);
            service.add("海达通","","",1);
            service.add("维沃通信","","",1);
            service.add("永惠通信","","",1);
            service.add("百立丰","","",1);
            service.add("鹏庆物流","","",1);
            service.add("亨庆电子","","",1);
            service.add("戈圣科技","","",1);
            service.add("恒讯联盛","","",1);
            service.add("友爵科技","","",1);
            service.add("恒盛达","","",1);
            service.add("宝威实业","","",1);
            service.add("凌进电子","","",1);
            service.add("蓝岸","","",1);
            service.add("泰泽","","",1);
            service.add("华夏睿创","","",1);
            service.add("典曜科技","","",1);
            service.add("菲泽","","",1);
            service.add("泰天科技","","",1);
            service.add("志裕通信","","",1);
            service.add("羽朗","","",1);
            service.add("中通福瑞","","",1);
            service.add("天与杰成","","",1);
            service.add("经纬众鸿","","",1);
            service.add("通尚科技","","",1);
            service.add("沣品","","",1);
            service.add("唐晟实业","","",1);
            service.add("德古智能","","",1);
            service.add("音乐一号","","",1);
            service.add("天行健","","",1);
            service.add("才箐电子","","",1);
            service.add("灿源电子","","",1);
            service.add("慧居智能","","",1);
            service.add("金马仕","","",1);
            service.add("西南计算机（新）","","",1);
            service.add("国能动力","","",1);
            service.add("东方丝路","","",1);
            service.add("合益光电","","",1);
            service.add("鹏亿南联","","",1);
            service.add("樱花电器","","",1);
            service.add("美顺电子","","",1);
            service.add("蟒蛇电子","","",1);
            service.add("维沃地块","","",1);
            service.add("美的","","",1);

            service.add("银杏光盘","","",2);
            service.add("远视科技","","",2);
            service.add("渝百家超市连锁","","",2);
            service.add("加加林","","",2);
            service.add("生活在线","","",2);
            service.add("西南集成电路","","",2);
            service.add("云网科技","","",2);
            service.add("嘉瑞电器","","",2);
            service.add("柒安电线电缆","","",2);
            service.add("巩诚电装","","",2);
            service.add("智翔铺道","","",2);
            service.add("七腾软件","","",2);
            service.add("西南计算机","","",2);
            service.add("赛丰基业","","",2);
            service.add("善雨医疗","","",2);
            service.add("赛迪工业","","",2);
            service.add("微品数字","","",2);
            service.add("齐汇科技","","",2);
            service.add("爱思网安","","",2);
            service.add("欧比迪科技（车内宝大数据）","","",2);
            service.add("康洲科贸","","",2);
            service.add("航伟光电","","",2);
            service.add("渝光科技","","",2);
            service.add("胜普电子","","",2);
            service.add("切普电子","","",2);
            service.add("集成电子","","",2);
            service.add("声光电","","",2);
            service.add("金坤实业","","",2);
            service.add("汉界供应链","","",2);
            service.add("易博数字","","",2);
            service.add("华驰交通","","",2);
            service.add("宝刷科技","","",2);
            service.add("聚禾富科技","","",2);
            service.add("喜马拉雅","","",2);
            service.add("凌立","","",2);
            service.add("晏亮科技","","",2);
            service.add("赛格车圣","","",2);
            service.add("车杰盟","","",2);
            service.add("忝翊服务","","",2);
            service.add("巴云科技","","",2);
            service.add("玉马医疗","","",2);


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
