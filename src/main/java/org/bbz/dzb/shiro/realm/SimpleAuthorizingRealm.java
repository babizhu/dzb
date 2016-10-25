package org.bbz.dzb.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bbz.dzb.bean.User;
import org.nutz.dao.Dao;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.mvc.Mvcs;


public class SimpleAuthorizingRealm extends AuthorizingRealm {
    
    protected Dao dao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        int userId = (Integer) principals.getPrimaryPrincipal();
        User user = dao().fetch(User.class, userId);
        if (user == null)
            return null;

        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        user = dao().fetchLinks(user, null);
        return auth;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleShiroToken upToken = (SimpleShiroToken) token;

        User user = dao().fetch(User.class, ((Integer)upToken.getPrincipal()).longValue());
        if (user == null)
            return null;
        return new SimpleAccount(user.getId(), user.getPassword(), getName());
    }
    
    /**
     * 覆盖父类的验证,直接pass
     */
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
    }

    public SimpleAuthorizingRealm() {
        this(null, null);
    }

    public SimpleAuthorizingRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
        setAuthenticationTokenClass(SimpleShiroToken.class);
    }

    public SimpleAuthorizingRealm(CacheManager cacheManager) {
        this(cacheManager, null);
    }

    public SimpleAuthorizingRealm(CredentialsMatcher matcher) {
        this(null, matcher);
    }

    public Dao dao() {
        if (dao == null) {
            dao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
            return dao;
        }
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

}
