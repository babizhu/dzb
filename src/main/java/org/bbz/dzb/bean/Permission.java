package org.bbz.dzb.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by liulaoye on 16-10-25.
 * Permission
 */


    @Table("t_permission")
    public class Permission extends BaseBean{

    public long getId(){
        return id;
    }

    public void setId( long id ){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getAlias(){
        return alias;
    }

    public void setAlias( String alias ){
        this.alias = alias;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription( String description ){
        this.description = description;
    }

    @Id
        protected long id;
        @Name
        protected String name;
        @Column("al")
        protected String alias;
        @Column("dt")
        @ColDefine(type = ColType.VARCHAR, width = 500)
        private String description;
    }

