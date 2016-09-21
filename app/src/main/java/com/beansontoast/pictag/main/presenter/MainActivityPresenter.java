package com.beansontoast.pictag.main.presenter;

import com.beansontoast.pictag.interactor.MediaStoreInteractor;
import com.beansontoast.pictag.main.view.IMainActivityView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by renier on 9/21/2016.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    private MediaStoreInteractor mMediaStoreInteractor;
    private IMainActivityView mView;

    public MainActivityPresenter(MediaStoreInteractor mediaStoreInteractor) {
        this.mMediaStoreInteractor = mediaStoreInteractor;
    }

    @Override
    public void setView(IMainActivityView view) {
        mView = view;
    }

    @Override
    public void clearView() {
        mView = null;
    }

    private boolean viewValid() {
        return mView != null;
    }

    @Override
    public void onResume() {
        load();
    }

    @Override
    public void onPause() {

    }

    private void load() {
        mMediaStoreInteractor.getMediaStoreData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (viewValid()) {
                        mView.setModel(result);
                    }
                }, error -> {
                    if (viewValid()) {
                        mView.showError(error.getMessage());
                    }
                });
    }
}
