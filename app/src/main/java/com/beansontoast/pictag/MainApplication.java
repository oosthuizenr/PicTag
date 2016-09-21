package com.beansontoast.pictag;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.beansontoast.pictag.di.Graph;

/**
 * Created by Renier on 2016/09/20.
 */

public class MainApplication extends Application {

    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mGraph = Graph.Initializer.init(this);
    }

    public Graph getGraph() {
        return mGraph;
    }

    public void setGraph(Graph graph) { mGraph = graph; }

    public static MainApplication from(@NonNull Context context) {
        return (MainApplication) context.getApplicationContext();
    }
}
