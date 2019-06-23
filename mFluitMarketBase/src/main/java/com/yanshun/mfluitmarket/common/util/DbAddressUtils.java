package com.yanshun.mfluitmarket.common.util;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by hasee on 2017/10/17.
 */

public class DbAddressUtils {
    DbManager.DaoConfig daoConfig;
    public DbManager db;
    private static class LazyHolder{
        private static final DbAddressUtils INSTANCE = new DbAddressUtils();
    }

    private DbAddressUtils(){
        daoConfig = new DbManager.DaoConfig()
                .setDbName("address.db")
                .setDbVersion(1);
        db = x.getDb(daoConfig);
    }
    public static final DbAddressUtils getInstance(){
        return LazyHolder.INSTANCE;
    }
}
