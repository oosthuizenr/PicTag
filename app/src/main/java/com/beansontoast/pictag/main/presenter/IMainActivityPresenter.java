package com.beansontoast.pictag.main.presenter;

import com.beansontoast.pictag.main.view.IMainActivityView;

/**
 * Created by renier on 9/21/2016.
 */

public interface IMainActivityPresenter {
    void setView(IMainActivityView view);
    void clearView();
    void onResume();
    void onPause();
}
