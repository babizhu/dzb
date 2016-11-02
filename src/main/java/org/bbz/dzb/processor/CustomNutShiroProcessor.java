package org.bbz.dzb.processor;

import org.apache.shiro.authz.UnauthenticatedException;
import org.bbz.dzb.consts.ErrorCode;
import org.nutz.integration.shiro.NutShiro;
import org.nutz.integration.shiro.NutShiroProcessor;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.view.ServerRedirectView;

/**
 * Created by liulaoye on 16-11-2.
 */
public class CustomNutShiroProcessor extends NutShiroProcessor{
    @Override
    protected void whenUnauthenticated( ActionContext ac, UnauthenticatedException e ) throws Exception{
//        super.whenUnauthenticated( ac, e );
        if( NutShiro.isAjax( ac.getRequest() ) ) {
            ac.getResponse().addHeader( "loginStatus", "accessDenied" );
            ac.getResponse().setStatus( 500 );
            NutShiro.rendAjaxResp( ac.getRequest(), ac.getResponse(), new NutMap().addv( "errId", ErrorCode.NOT_LOGIN.toNum() ) );
        } else {
            new ServerRedirectView( loginUri() ).render( ac.getRequest(), ac.getResponse(), null );
        }
    }


}
