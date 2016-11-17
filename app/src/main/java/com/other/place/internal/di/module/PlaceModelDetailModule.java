package com.other.place.internal.di.module;

import com.other.place.internal.di.scope.PerPlaceDetailModel;
import com.other.place.internal.mvp.model.PlaceDetailModel;
import com.other.place.location.LocationService;
import com.other.place.preference.AppSharePreference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Module
public class PlaceModelDetailModule {

    @PerPlaceDetailModel
    @Provides
    PlaceDetailModel providePlaceDetailModel(LocationService mLocationService, AppSharePreference mAppSharePreference) {
        return new PlaceDetailModel(mLocationService, mAppSharePreference);
    }
}
