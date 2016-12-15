package org.bbz.dzb.module;

import com.bbz.tool.common.FileUtil;
import com.bbz.tool.common.StrUtil;
import com.google.common.base.Strings;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.bbz.dzb.bean.Enterprise;
import org.bbz.dzb.consts.ErrorCode;
import org.bbz.dzb.service.EnterpriseService;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liulaoye on 16-10-25.
 * EnterpriseModule
 */

@IocBean
@At("/api/enterprise")

public class EnterpriseModule extends BaseModule{
    @Inject
    protected EnterpriseService enterpriseService;
    @Inject
    protected Dao dao;

    @At
    @GET
    @RequiresUser
    public Object query(){

        return enterpriseService.getAll();
    }

    /**0.5765
     * http://j.map.baidu.com/fdLxH
     * 0.1996
     * 0.2229
     * 数据操作（增删改）统一到这里处理
     *
     * @param op         操作类型1:增 改（通过id是否等于-1区分） 2:删除
     * @param enterprise 当前要操作的元素
     */
    @At
    @GET
    @RequiresUser
    public Object operation( @Param("op") int op, @Param("..") Enterprise enterprise,
                             HttpServletResponse response ){
        Object result;

        switch( op ) {
            case 1:

                if( enterprise.getId() == -1 ) {
                    result = enterpriseService.add( enterprise );
                } else {
                    enterpriseService.updateIgnoreNull( enterprise );
                    result = enterprise;
                }
                break;
            case 2:
                enterpriseService.delete( enterprise );
                result = new NutMap( "id", enterprise.getId() );
                break;

            default:
                result = this.buildErrorResponse( response, ErrorCode.OPERATION_NOT_FOUND, op + "" );
        }
        return result;


//        return result;
    }

    @At
    @Ok("raw")
    public String buildMapJson(HttpServletResponse response){
        String filePath = this.getClass().getClassLoader().getResource( "map.template" ).getPath();

        String result = FileUtil.readTextFile( filePath );

        String enterpriseData = "";
        final List<Enterprise> all = enterpriseService.getAll();
        for( Enterprise enterprise : all ) {
            enterpriseData += buildEnterpriseJson( enterprise,true );
        }


//        enterpriseData = ;
        result = result.replace( "##areaType0##", "茶园片区" );
        result = result.replace( "##areaType1##", "经开区片区" );
        result = result.replace( "##areaType2##", "南坪片区" );
        result = result.replace( "##data##", StrUtil.removeLastChar( enterpriseData ) );
        response.setContentType("text/html;charset=UTF-8");
        return result;
    }
    @At
    @Ok("raw")
    public String buildMapJson2(HttpServletResponse response){
        String filePath = this.getClass().getClassLoader().getResource( "map2.template" ).getPath();

        String result = FileUtil.readTextFile( filePath );

        String enterpriseData = "";
        final List<Enterprise> all = enterpriseService.getAll();
        for( Enterprise enterprise : all ) {
            enterpriseData += buildEnterpriseJson( enterprise,false );
        }


        result = result.replace( "##data##", StrUtil.removeLastChar( enterpriseData ) );
        response.setContentType("text/html;charset=UTF-8");
        return result;
    }

    private String buildEnterpriseJson( Enterprise enterprise, boolean actionIsTooltip ){
        String result = "";
        String action = actionIsTooltip? "\"action\": \"tooltip\",\n": "\"action\": \"lightbox\",\n";
                result += "{" +
                "\"id\": \"" + enterprise.getId() + "\",\n" +
                "\"title\": \"" + enterprise.getName() + "\",\n" +
//                "\"about\": \"" + enterprise.getName() + "\",\n" +
                "\"description\": \"" + buildDesc(enterprise) + "\",\n" +
                "\"category\": \"" + enterprise.getAreaType() + "\",\n" +
                "\"link\": \"" + enterprise.getMapAddress() + "\",\n" +
                "\"pin\": \"red\",\n" +
                action +
                "\"x\": \"" + enterprise.getMapX() + "\",\n" +
                "\"y\": \"" + enterprise.getMapY() + "\",\n" +
                "\"zoom\": \"3\"\n" +
                "},";
        return result;
    }

    private String buildDesc(Enterprise enterprise ){


        String result = enterprise.getDescription();
        result += enterprise.getYearValue()<0.0001?"":"<br/>当年累计产值： " + enterprise.getYearValue() + " 万元";
        result += enterprise.getYearNum()==0?"":"<br/>当年累计产量： " + enterprise.getYearNum() + " (台/套)";
        result += Strings.isNullOrEmpty(enterprise.getAddress())?"":"<br/>地址： " + enterprise.getAddress();
        result += Strings.isNullOrEmpty(enterprise.getContact())?"":"<br/>联系： " + enterprise.getContact();

        return result;
    }



}