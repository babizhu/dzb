package org.bbz.dzb.module;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.bbz.dzb.bean.User;
import org.bbz.dzb.consts.ErrorCode;
import org.bbz.dzb.service.UserService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by liulaoye on 16-10-25.
 * UserModule
 */

@IocBean
@At("/user")

public class UserModule extends BaseModule{
    @Inject
    protected UserService userService;
    @Inject
    protected Dao dao;

    @At

//    @Filters // 覆盖UserModule类的@Filter设置,因为登陆可不能要求是个已经登陆的Session
    @GET
    public Object login( @Param("username") String username,
                         @Param("password") String password,
                         @Param("rememberMe") boolean rememberMe,
                         @Param("captcha") String captcha,
//                        @Attr(scope = Scope.SESSION, value = "nutz_captcha") String _captcha,
                         HttpSession session,
                         HttpServletRequest req,
                         HttpServletResponse response ){
        NutMap re = new NutMap();
//        if (!Toolkit.checkCaptcha(_captcha, captcha)) {
//            return re.setv("ok", false).setv("msg", "验证码错误");
//        }
        int userId = userService.fetch( username, password );
        if( userId < 0 ) {

            return buildErrorResponse( response, ErrorCode.LOGIN_ERROR );
        } else {
            session.setAttribute( "me", userId );
            // 完成nutdao_realm后启用.
            SecurityUtils.getSubject().login( new SimpleShiroToken( userId, rememberMe, req.getRemoteHost() ) );
            return re.setv( "ok", true );
        }
    }


    @At
    @GET
    @RequiresUser
    public Object count(){
        NutMap re = new NutMap();
        final int count = dao.count( User.class );
        return re.setv( "ok", true ).setv( "count", count );
    }

    @At
    public Object add( @Param("..") User user ){ // 两个点号是按对象属性一一设置
        NutMap re = new NutMap();
        String msg = checkUser( user, true );
        if( msg != null ) {
            return re.setv( "ok", false ).setv( "msg", msg );
        }
        user = userService.add( user.getName(), user.getPassword() );
        return re.setv( "ok", true ).setv( "data", user );
    }


    @At
    public Object update( @Param("password") String password, @Attr("me") int me ){
        if( Strings.isBlank( password ) || password.length() < 6 )
            return new NutMap().setv( "ok", false ).setv( "msg", "密码不符合要求" );
        userService.updatePassword( me, password );
        return new NutMap().setv( "ok", true );
    }

    private String checkUser( User user, boolean create ){
        if( user == null ) {
            return "空对象";
        }
        if( create ) {
            if( Strings.isBlank( user.getName() ) || Strings.isBlank( user.getPassword() ) )
                return "用户名/密码不能为空";
        } else {
            if( Strings.isBlank( user.getPassword() ) )
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if( 6 > passwd.length() || passwd.length() > 12 ) {
            return "密码长度错误";
        }
        user.setPassword( passwd );
        if( create ) {
            int count = dao.count( User.class, Cnd.where( "name", "=", user.getName() ) );
            if( count != 0 ) {
                return "用户名已经存在";
            }
        } else {
            if( user.getId() < 1 ) {
                return "用户Id非法";
            }
        }
        if( user.getName() != null )
            user.setName( user.getName().trim() );
        return null;
    }

}