package com.yanshun.mfluitmarket.common.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by hasee on 2017/10/17.
 */
@Table(name="county")
public class County {

    @Column(name="id",isId = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name="cityId")
    private String cityId;

    @Column(name="countyId")
    private String countyId;

    @Column(name ="url")
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public List<Towns> getTownsList(DbManager db) throws DbException {
        return db.selector(Towns.class).where("countyId","=",this.countyId).findAll();
    }

    public City getCity(DbManager db) throws DbException {
        return db.findById(City.class, cityId);
    }

    @Override
    public String toString() {
        return "County{"+
                "id=" + id +
                ", name='" + name + '\''+
                ", cityId='" + cityId  + '\''+
                ", countyId='" + countyId  + '\''+
                ", url='" + url  + '\''+
                "}";
    }
}
