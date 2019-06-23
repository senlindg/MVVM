package com.yanshun.mfluitmarket.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by hasee on 2017/10/27.
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 */

public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "breakfast_share";

    private static class LazyHolder{
        private static final SharedPreferencesUtils INSTANCE = new SharedPreferencesUtils();
    }

    public static final SharedPreferencesUtils getInstance(){
        return SharedPreferencesUtils.LazyHolder.INSTANCE;
    }

    public SharedPreferencesUtils(){

    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public void putParam(Context context , String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 保存图片到SharedPreferences
     * @param mContext
     * @param imageView
     */
    public void putImage(Context mContext, String key, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        // 将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        // 利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        // 将String保存shareUtils
        putParam(mContext, key, imgString);
    }

    /**
     * 从SharedPreferences读取图片
     * @param mContext
     * @param imageView
     */
    public Bitmap getImage(Context mContext, String key, ImageView imageView) {
        String imgString = (String)getParam(mContext, key, "");
        if (!imgString.equals("")) {
            // 利用Base64将我们string转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            // 生成bitmap
            return BitmapFactory.decodeStream(byStream);
        }
        return null;
    }
    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();
        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }
        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
            editor.commit();
        }
    }

}
