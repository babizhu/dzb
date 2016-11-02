package org.bbz.dzb.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by liulaoye on 16-11-1.
 * Enterprise
 */
@Table("enterprise")
public class Enterprise extends BaseBean{
    @Id
    protected int id;
    @Name
    @Column
    @ColDefine(type = ColType.VARCHAR, width = 100)
    protected String name;
    @Column
    @ColDefine(type = ColType.VARCHAR, width = 150)
    protected String address;
    @Column
    @ColDefine(type = ColType.VARCHAR, width = 250)
    protected String mapAddress;
    @Column
    @ColDefine(type = ColType.VARCHAR, width = 100)
    protected String contact;
    @Column
    @ColDefine(type = ColType.VARCHAR, width = 1000)
    protected String description;
    public String getMapAddress(){
        return mapAddress;
    }

    public void setMapAddress( String mapAddress ){
        this.mapAddress = mapAddress;
    }

    public String getContact(){
        return contact;
    }

    public void setContact( String contact ){
        this.contact = contact;
    }



    public int getId(){
        return id;
    }

    public void setId( int id ){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress( String address ){
        this.address = address;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription( String description ){
        this.description = description;
    }
}
