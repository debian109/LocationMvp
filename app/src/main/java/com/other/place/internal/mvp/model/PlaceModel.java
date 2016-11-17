package com.other.place.internal.mvp.model;

import com.other.place.internal.mvp.MVP;
import com.other.place.model.PlaceItem;
import com.other.place.model.PlaceList;
import com.other.place.network.PlaceApi;
import com.other.place.preference.AppSharePreference;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by thinh.vo on 16/11/2016.
 */


public class PlaceModel {

    PlaceApi mPlaceApi;
    AppSharePreference mAppSharePreference;

    public PlaceModel(PlaceApi mPlaceApi, AppSharePreference mAppSharePreference) {
        this.mPlaceApi = mPlaceApi;
        this.mAppSharePreference = mAppSharePreference;
    }

    public void RequestLocation(final MVP.Model.OnLoad<List<PlaceItem>> onLoad) {
        String locationString = mAppSharePreference.getString("location");
        String key=mAppSharePreference.getString("key");
        Observable<PlaceList> placeListObservable = mPlaceApi.NearBySearch(locationString, 3000, key, key, PlaceApi.API_KEY);
        placeListObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<PlaceList, Observable<List<PlaceItem>>>() {
                    @Override
                    public Observable<List<PlaceItem>> call(PlaceList placeList) {
                        return Observable.just(placeList.listPlaceItem);
                    }
                }).subscribe(new Subscriber<List<PlaceItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onLoad.onFail(e.toString());
            }

            @Override
            public void onNext(List<PlaceItem> placeItems) {
                onLoad.onSuccess(placeItems);
            }
        });

    }
}
