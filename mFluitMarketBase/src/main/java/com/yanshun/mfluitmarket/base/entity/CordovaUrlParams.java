package com.yanshun.mfluitmarket.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用于Cordova的URL拼接参数
 */
public class CordovaUrlParams implements Parcelable {

    String key;

    String value;

    protected CordovaUrlParams(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CordovaUrlParams> CREATOR = new Creator<CordovaUrlParams>() {
        @Override
        public CordovaUrlParams createFromParcel(Parcel in) {
            return new CordovaUrlParams(in);
        }

        @Override
        public CordovaUrlParams[] newArray(int size) {
            return new CordovaUrlParams[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
