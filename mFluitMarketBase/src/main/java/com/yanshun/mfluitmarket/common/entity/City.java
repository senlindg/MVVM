package com.yanshun.mfluitmarket.common.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by hasee on 2017/10/17.
 * 市
 */
@Table(name = "city")
public class City {

    @Column(name="id",isId = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name="provinceId")
    private String provinceId;

    @Column(name="cityId")
    private String cityId;

    @Column(name="url")
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List<County> getCountyList(DbManager db) throws DbException {
        return db.selector(County.class).where("cityId","=",this.cityId).findAll();
    }
    public Province getProvince(DbManager db) throws DbException {
        return db.findById(Province.class, provinceId);
    }

    @Override
    public String toString() {
        return "City{"+
                "id=" + id +
                ", name='" + name + '\''+
                ", provinceId ='" + provinceId + '\''+
                ", cityId='" + cityId + '\''+
                ", url='" + url + '\''+
                "}";
    }
}
