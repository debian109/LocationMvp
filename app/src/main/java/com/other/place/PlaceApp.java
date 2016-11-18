package com.other.place;

import android.app.Application;

import com.other.place.internal.di.component.AppComponent;
import com.other.place.internal.di.component.DaggerAppComponent;
import com.other.place.internal.di.module.AppSharePreferenceModule;
import com.other.place.internal.di.module.GooglePlaceModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Aksiom on 6/29/2016.
 */
public class PlaceApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        appComponent = DaggerAppComponent.builder()
                .googlePlaceModule(new GooglePlaceModule())
                .appSharePreferenceModule(new AppSharePreferenceModule(this))
                .build();
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
