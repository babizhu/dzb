package org.bbz.dzb.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bbz.dzb.bean.Permission;
import org.bbz.dzb.bean.Role;
import org.bbz.dzb.bean.User;
import org.nutz.dao.Dao;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.mvc.Mvcs;

public class SimpleAuthorizingRealm extends AuthorizingRealm{

    protected Dao dao; // ShiroFilter先于NutFilter初始化化,所以无法使用注入功能

    protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals ){
        // null usernames are invalid
        if( principals == null ) {
            throw new AuthorizationException( "PrincipalCollection method argument cannot be null." );
        }
        int userId = (Integer) principals.getPrimaryPrincipal();
        User user = dao().fetch( User.class, userId );
        if( user == null )
            return null;
        if( user.isLocked() )
            throw new LockedAccountException( "Account [" + user.getName() + "] is locked." );

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        user = dao().fetchLinks( user, null );
        if( user.getRoles() != null ) {
            dao().fetchLinks( user.getRoles(), null );
            for( Role role : user.getRoles() ) {
                auth.addRole( role.getName() );
                if( role.getPermissions() != null ) {
                    for( Permission p : role.getPermissions() ) {
                        auth.addStringPermission( p.getName() );
                    }
                }
            }
        }
        if( user.getPermissions() != null ) { // 特许/临时分配的权限
            for( Permission p : user.getPermissions() ) {
                auth.addStringPermission( p.getName() );
            }
        }
        return auth;
    }

    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken token ) throws AuthenticationException{
        SimpleShiroToken upToken = (SimpleShiroToken) token;
        // upToken.getPrincipal() 的返回值就是SimpleShiroToken构造方法传入的值
        // 可以是int也可以是User类实例,或任何你希望的值,自行处理一下就好了
        User user = dao().fetch( User.class, ((Integer) upToken.getPrincipal()).longValue() );
        if( user == null )
            return null;
        if( user.isLocked() )
            throw new LockedAccountException( "Account [" + user.getName() + "] is locked." );
        return new SimpleAccount( user.getId(), user.getPassword(), getName() );
    }

    /**
     * 覆盖父类的验证,直接pass. 在shiro内做验证的话, 出错了都不知道哪里错
     */
    protected void assertCredentialsMatch( AuthenticationToken token, AuthenticationInfo info ) throws AuthenticationException{
    }

    public SimpleAuthorizingRealm(){
        this( null, null );
    }

    public SimpleAuthorizingRealm( CacheManager cacheManager, CredentialsMatcher matcher ){
        super( cacheManager, matcher );
        setAuthenticationTokenClass( SimpleShiroToken.class ); // 非常非常重要,与SecurityUtils.getSubject().login是对应关系!!!
    }

    public SimpleAuthorizingRealm( CacheManager cacheManager ){
        this( cacheManager, null );
    }

    public SimpleAuthorizingRealm( CredentialsMatcher matcher ){
        this( null, matcher );
    }

    public Dao dao(){
        if( dao == null ) {
            dao = Mvcs.ctx().getDefaultIoc().get( Dao.class, "dao" );
            return dao;
        }
        return dao;
    }

    public void setDao( Dao dao ){
        this.dao = dao;
    }

}