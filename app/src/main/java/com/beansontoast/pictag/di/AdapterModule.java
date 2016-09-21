package com.beansontoast.pictag.di;

import com.beansontoast.pictag.main.adapter.GalleryAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renier on 9/21/2016.
 */
@Module
public class AdapterModule {
    @Provides
    @Singleton
    GalleryAdapter providesGalleryAdapter() {
        return new GalleryAdapter();
    }
}
