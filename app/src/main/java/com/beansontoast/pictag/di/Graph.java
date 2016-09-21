package com.beansontoast.pictag.di;

import com.beansontoast.pictag.MainApplication;
import com.beansontoast.pictag.main.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by renier on 9/21/2016.
 */
@Component(modules = { PresenterModule.class, ApplicationModule.class, InteractorModule.class })
@Singleton
public interface Graph {
    void inject(MainActivity activity);

    final class Initializer {
        public static Graph init(MainApplication application) {
            return DaggerGraph
                    .builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
