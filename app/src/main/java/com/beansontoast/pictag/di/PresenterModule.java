package com.beansontoast.pictag.di;

import com.beansontoast.pictag.interactor.MediaStoreInteractor;
import com.beansontoast.pictag.main.presenter.IMainActivityPresenter;
import com.beansontoast.pictag.main.presenter.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renier on 9/21/2016.
 */
@Module
public class PresenterModule {
    @Singleton
    @Provides
    IMainActivityPresenter providesMainActivityPresenter(MediaStoreInteractor mediaStoreInteractor) {
        return new MainActivityPresenter(mediaStoreInteractor);
    }
}
