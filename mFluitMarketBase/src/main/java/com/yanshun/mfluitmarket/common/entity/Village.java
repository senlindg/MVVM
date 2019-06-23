package com.yanshun.mfluitmarket.common.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

/**
 * Created by hasee on 2017/10/17.
 */
@Table(name="village")
public class Village {
    @Column(name="id",isId = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "villageId")
    private String villageId;

    @Column(name="townsId")
    private String  townsId;

    @Column(name="url")
    private String url;

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

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getTownsId() {
        return townsId;
    }

    public void setTownsId(String townsId) {
        this.townsId = townsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Towns getTowns(DbManager db) throws DbException {
        return db.findById(Towns.class, townsId);
    }

    @Override
    public String toString() {
        return "Village{"+
                "id=" + id +
                ", name='" + name + '\''+
                ", townsId='" + townsId + '\''+
                ", villageId='" + villageId + '\''+
                "}";
    }
}
