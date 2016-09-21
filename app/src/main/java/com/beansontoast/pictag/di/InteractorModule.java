package com.beansontoast.pictag.di;

import android.content.Context;

import com.beansontoast.pictag.interactor.MediaStoreInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renier on 9/21/2016.
 */
@Module
public class InteractorModule {
    @Singleton
    @Provides
    MediaStoreInteractor providesMediaStoreInteractor(Context context) {
        return new MediaStoreInteractor(context);
    }
}
