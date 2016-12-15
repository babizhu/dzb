package org.bbz.dzb.service;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.bbz.dzb.bean.User;
import org.bbz.dzb.consts.ErrorCode;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;
import org.nutz.service.IdNameEntityService;

import java.util.Date;
import java.util.List;

/**
 * Created by liulaoye on 16-10-25.
 * UserService
 */
@IocBean(fields = "dao")
public class UserService extends IdNameEntityService<User>{

    public List<User> getAll(){
        return dao().query( User.class, null, null );
    }

    public User add( String name, String password ){
        User user = new User();
        user.setName( name.trim() );
        user.setSalt( R.UU16() );
        user.setPassword( new Sha256Hash( password, user.getSalt() ).toHex() );
        user.setCreateTime( new Date() );
        user.setUpdateTime( new Date() );
        return dao().insert( user );
    }

    public User add( User user ){

        user.setSalt( R.UU16() );
        user.setPassword( new Sha256Hash( user.getPassword(), user.getSalt() ).toHex() );
        user.setCreateTime( new Date() );
        user.setUpdateTime( new Date() );
        return dao().insert( user );
    }

    public int fetch( String userName, String password ){
        User user = fetch( userName );
        if( user == null ) {
            return -1;
        }
        String tempPass = new Sha256Hash( password, user.getSalt() ).toHex();
        if( tempPass.equalsIgnoreCase( user.getPassword() ) ) {
            return user.getId();
        }
        return -1;
    }

    /**
     * 管理员重置密码
     * @param userName
     * @param newPassword
     * @return
     */
    public ErrorCode resetPassword(String  userName ,String newPassword ){
        User user = fetch( userName );
        if( user == null ) {
            return ErrorCode.NOT_LOGIN;
        }


        user.setSalt( R.UU16() );
        user.setPassword( new Sha256Hash( newPassword, user.getSalt() ).toHex() );
        user.setUpdateTime( new Date() );
        dao().update( user, "^(password|salt|updateTime)$" );
//        .query("^(id|title|info|picurl)$", cnd.desc("publishAt"))
        return ErrorCode.SUCCESS;
    }

    public ErrorCode changePassword( String  userName, String currentPassword,String newPassword ){
        User user = fetch( userName );
        if( user == null ) {
            return ErrorCode.NOT_LOGIN;
        }

        String tempPass = new Sha256Hash( currentPassword, user.getSalt() ).toHex();
        if( !tempPass.equalsIgnoreCase( user.getPassword() ) ) {
            return ErrorCode.PASSWORD_ERROR;
        }
        user.setSalt( R.UU16() );
        user.setPassword( new Sha256Hash( newPassword, user.getSalt() ).toHex() );
        user.setUpdateTime( new Date() );
        dao().update( user, "^(password|salt|updateTime)$" );
//        .query("^(id|title|info|picurl)$", cnd.desc("publishAt"))
        return ErrorCode.SUCCESS;
    }

    public void updateIgnoreNull( User user ){
    }

}