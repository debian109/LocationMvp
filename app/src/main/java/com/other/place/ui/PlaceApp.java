package com.other.place.ui;

import android.app.Application;

import com.other.place.internal.di.component.AppComponent;
import com.other.place.internal.di.component.DaggerAppComponent;
import com.other.place.internal.di.module.AppSharePreferenceModule;
import com.other.place.internal.di.module.GooglePlaceModule;

/**
 * Created by Aksiom on 6/29/2016.
 */
public class PlaceApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .googlePlaceModule(new GooglePlaceModule())
                .appSharePreferenceModule(new AppSharePreferenceModule(this))
                .build();
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
