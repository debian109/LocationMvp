package com.other.place.internal.di.module;

import com.other.place.internal.di.scope.PerPlaceModel;
import com.other.place.internal.mvp.model.PlaceModel;
import com.other.place.network.PlaceApi;
import com.other.place.preference.AppSharePreference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Module
public class PlaceModelModule {

    @PerPlaceModel
    @Provides
    PlaceModel providePlaceModel(PlaceApi mPlaceApi, AppSharePreference mAppSharePreference) {
        return new PlaceModel(mPlaceApi, mAppSharePreference);
    }
}
