package com.yanshun.mfluitmarket.common.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by hasee on 2017/10/17.
 * 省
 */
@Table(name = "province")
public class Province {

    @Column(name = "id",isId = true)
    private long id;

    @Column(name = "provinceId")
    private String proviceId;

    @Column(name ="name")
    private String name;

    @Column(name = "url")
    private String url;
    public List<City> getCityList(DbManager db) throws DbException {
        return db.selector(City.class).where("provinceId","=",this.proviceId).findAll();
    }

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

    public String getProviceId() {
        return proviceId;
    }

    public void setProviceId(String proviceId) {
        this.proviceId = proviceId;
    }

    @Override
    public String toString() {
        return "Province{"+
                "id=" + id +
                ", proviceId='" + proviceId + '\''+
                ", name='" + name + '\''+
                ", url='" + url + '\''+
                "}";
    }
}
