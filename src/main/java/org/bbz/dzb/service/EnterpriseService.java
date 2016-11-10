package org.bbz.dzb.service;

import org.bbz.dzb.bean.Enterprise;
import org.bbz.dzb.bean.User;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdNameEntityService;

import java.util.Date;
import java.util.List;

/**
 * Created by liulaoye on 16-10-25.
 * EnterpriseService
 */
@IocBean(fields = "dao")
public class EnterpriseService extends IdNameEntityService<User>{

    public Enterprise add( String name, String describe, String address ){
        Enterprise enterprise = new Enterprise();
        enterprise.setAddress( address );
        enterprise.setDescription( describe );
        enterprise.setName( name );
        enterprise.setCreateTime( new Date(  ) );
        enterprise.setUpdateTime( new Date(  ) );
        return dao().insert( enterprise );
    }

    public List<Enterprise> getAll(){
//       QueryResult qr = new QueryResult();
//        return  qr.setList(dao().query( Enterprise.class, null, null );
        return dao().query( Enterprise.class, null, null );
    }

    public int delete( Enterprise enterprise ){

        return dao().delete( Enterprise.class, enterprise.getId() );
    }

    public void updateIgnoreNull( Enterprise enterprise ){
        enterprise.setUpdateTime( new Date(  ) );

        dao().updateIgnoreNull( enterprise );
    }

    public Object add( Enterprise enterprise ){

        final Date now = new Date();
        enterprise.setCreateTime( now );
        enterprise.setUpdateTime( now );
        return dao().insert( enterprise );
    }

}