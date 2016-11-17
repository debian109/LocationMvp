package com.other.place.internal.di.module;

import android.app.Activity;

import com.other.place.internal.di.scope.PerActivity;
import com.other.place.location.LocationService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Module
public class LocationServiceModule {

    Activity activity;
    public LocationServiceModule(Activity activity) {
        this.activity=activity;
    }

    @PerActivity
    @Provides
    LocationService provideLocationTracker()
    {
        return new LocationService(activity);
    }
}
