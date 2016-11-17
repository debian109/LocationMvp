package com.other.place.internal.di.component;

import com.other.place.internal.di.module.AppModule;
import com.other.place.internal.di.module.AppSharePreferenceModule;
import com.other.place.internal.di.module.GooglePlaceModule;
import com.other.place.network.PlaceApi;
import com.other.place.preference.AppSharePreference;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Singleton
@Component(modules = {AppModule.class, GooglePlaceModule.class, AppSharePreferenceModule.class})
public interface AppComponent {
    PlaceApi placeApi();

    AppSharePreference appSharePreference();
}
