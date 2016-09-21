package com.beansontoast.pictag.di;

import android.content.Context;

import com.beansontoast.pictag.MainApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renier on 9/21/2016.
 */
@Module
public class ApplicationModule {
    private static MainApplication sApplication;

    public ApplicationModule(MainApplication application) {
        sApplication = application;
    }

    @Provides
    Context provideApplicationContext() {
        return sApplication.getApplicationContext();
    }
}
