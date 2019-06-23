package com.yanshun.mfluitmarket.common.county;

import com.yanshun.mfluitmarket.base.presenter.IPresenter;
import com.yanshun.mfluitmarket.base.view.IView;
import com.yanshun.mfluitmarket.common.entity.ZiXunAll;

import java.util.List;

public interface BreakFastAddrContract {
    interface BreakFastAddrView extends IView<BreakFastAddrPresenter> {
        void updateView(List<ZiXunAll> entities);
    }

    interface BreakFastAddrPresenter extends IPresenter<BreakFastAddrView> {
        void requestData();
    }
}
