package com.yanshun.mfluitmarket.common.county;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanshun.mfluitmarket.R;
import com.yanshun.mfluitmarket.base.activity.BaseActivity;
import com.yanshun.mfluitmarket.base.widget.BaseListView;
import com.yanshun.mfluitmarket.common.adapter.AddrAdapter;
import com.yanshun.mfluitmarket.common.entity.City;
import com.yanshun.mfluitmarket.common.entity.County;
import com.yanshun.mfluitmarket.common.entity.Province;
import com.yanshun.mfluitmarket.common.entity.Towns;
import com.yanshun.mfluitmarket.common.entity.Village;
import com.yanshun.mfluitmarket.common.entity.ZiXunAll;
import com.yanshun.mfluitmarket.common.util.AutoAddressUtils;
import com.yanshun.mfluitmarket.common.util.DbAddressUtils;
import com.yanshun.mfluitmarket.common.util.DbCreateUtils;
import com.yanshun.mfluitmarket.databinding.BreakFastAddr;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by hasee on 2017/9/18.
 * @Description 省市县乡镇村选择页面
 */
public class BreakFastAddrAct extends BaseActivity<BreakFastAddrContract.BreakFastAddrPresenter> implements
        BreakFastAddrContract.BreakFastAddrView {
    BreakFastAddr breakFastAddr;
    ZiXunAll ziXunAll;
    @Override
    public void updateView(List<ZiXunAll> entites) {
        if (null != entites) {
            ziXunAll = entites.get(1);
            breakFastAddr.setZixunall(ziXunAll);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.addr_a_mian;
    }

    @Override
    public BreakFastAddrContract.BreakFastAddrPresenter initPresenter() {
        return new BreakFastAddrPresenter();
    }

    @Override
    public void initView(){
        TextView ok = findViewById(R.id.addr_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ziXunAll.setPublicTime("20190618 玩MVVM");
            }
        });
    }

    @Override
    public void initData() {
        getPresenter().requestData();
    }

    @Override
    public void bindView(ViewDataBinding view) {
        breakFastAddr = (BreakFastAddr)view;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
