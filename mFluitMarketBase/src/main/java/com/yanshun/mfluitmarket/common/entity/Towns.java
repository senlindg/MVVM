package com.yanshun.mfluitmarket.common.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by hasee on 2017/10/17.
 */
@Table(name = "towns")
public class Towns {

    @Column(name="id",isId = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name="countyId")
    private String countyId;

    @Column(name="townsId")
    private String townsId;

    @Column(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Village> getVillageList(DbManager db) throws DbException {
        return db.selector(Village.class).where("townsId","=",this.townsId).findAll();
    }

    public County getCounty(DbManager db) throws DbException {
        return db.findById(County.class, countyId);
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getTownsId() {
        return townsId;
    }

    public void setTownsId(String townsId) {
        this.townsId = townsId;
    }

    @Override
    public String toString() {
        return "Towns{"+
                "id=" + id +
                ", name='" + name + '\''+
                ", countyId='" + countyId + '\''+
                ", townsId='" + townsId + '\''+
                ", url='" + url + '\''+
                "}";
    }
}
