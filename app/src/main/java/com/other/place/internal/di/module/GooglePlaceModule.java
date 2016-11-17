package com.other.place.internal.di.module;

import com.other.place.network.PlaceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Module
public class GooglePlaceModule {
    @Provides
    @Singleton
    public PlaceApi providePlaceAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlaceApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PlaceApi.class);
    }
}
