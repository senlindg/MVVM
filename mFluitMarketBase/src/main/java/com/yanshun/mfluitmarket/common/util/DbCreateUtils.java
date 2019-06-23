package com.yanshun.mfluitmarket.common.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.yanshun.mfluitmarket.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hasee on 2017/10/22.
 */

public class DbCreateUtils {
    public static String dbName = "address.db";
    private static String DATABASE_PATH = "/data/data/com.yanshun.fmarket/databases/";

    private static class LazyHolder{
        private static final DbCreateUtils INSTANCE = new DbCreateUtils();
    }

    private DbCreateUtils(){

    }

    public static final DbCreateUtils getInstance(){
        return LazyHolder.INSTANCE;
    }

    /**
     * 校验数据库是否存在
     * @return
     */
    public boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String databaseFileName = DATABASE_PATH + dbName;
            checkDB = SQLiteDatabase.openDatabase(databaseFileName,null,
                    SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){

        }
        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null? true:false;
    }

    /**
     * 复制数据库到指定文件夹下
     */
    public void copyDataBase(Context context) throws IOException{
        String databaseFileNames = DATABASE_PATH + dbName;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists()){//判断文件夹是否存在，不存在就新建
            dir.mkdir();
        }
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(databaseFileNames);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        InputStream is = context.getResources().openRawResource(R.raw.address);
        byte[] buffer = new byte[8192];
        int count =0;
        try{
            while ((count =is.read(buffer)) > 0){
                os.write(buffer,0,count);
                os.flush();
            }
        }catch (IOException e){

        }
        try{
            is.close();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
