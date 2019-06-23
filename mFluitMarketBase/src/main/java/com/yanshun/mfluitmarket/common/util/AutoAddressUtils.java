package com.yanshun.mfluitmarket.common.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by hasee on 2017/10/27.
 */

public class AutoAddressUtils {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public String autoAddress = "";
    public static Context mContext;

    public String addressCode = "";

    public static AutoAddressInterface autoAddressInterface;
    private static class LazyHolder{
        private static final AutoAddressUtils INSTANCE = new AutoAddressUtils(mContext);
    }

    public static final AutoAddressUtils getInstance(Context context,AutoAddressInterface autoAddress){
        autoAddressInterface = autoAddress;
        mContext = context;
        return LazyHolder.INSTANCE;
    }

    private AutoAddressUtils(final Context context){
        //初始化定位
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        autoAddress = autoAddress + aMapLocation.getProvince() + "--";
                        autoAddress = autoAddress + aMapLocation.getCity() + "--";
                        autoAddress = autoAddress + aMapLocation.getDistrict();
                        String cityAd = aMapLocation.getAdCode();
                        if (6 == cityAd.length()){
                            addressCode = cityAd.substring(0,2);
                            addressCode = addressCode + "--" + cityAd.substring(0,4);
                            addressCode = addressCode + "--" + cityAd.substring(0,6);
                        }
                        autoAddressInterface.AutoAddressLinstener(addressCode,autoAddress);
//                        if (addressCode.contains("43--4310")){
//                            autoAddressInterface.AutoAddressLinstener(addressCode,autoAddress);
//                        }else{
//                            Toast.makeText(context,"目前只支持湖南郴州范围!",Toast.LENGTH_LONG).show();
//                        }
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        if (12 == aMapLocation.getErrorCode()){
                            Toast.makeText(context,"请赋予应用定位权限!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"定位失败!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        };

        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    public interface AutoAddressInterface{
        void AutoAddressLinstener(String addressCode, String address);
    }
}
