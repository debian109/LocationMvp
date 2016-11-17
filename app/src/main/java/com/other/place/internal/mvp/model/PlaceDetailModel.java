package com.other.place.internal.mvp.model;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.other.place.internal.mvp.MVP;
import com.other.place.location.LocationService;
import com.other.place.preference.AppSharePreference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by thinh.vo on 17/11/2016.
 */

public class PlaceDetailModel {

    LocationService mLocationService;
    AppSharePreference mAppSharePreference;
    private CompositeSubscription compositeSubscription;

    public PlaceDetailModel(LocationService mLocationService, AppSharePreference mAppSharePreference) {
        this.mLocationService = mLocationService;
        this.mAppSharePreference = mAppSharePreference;
    }

    public void requestMapDetail(final String placeId, final MVP.Model.OnLoad<Place> onLoad)
    {
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(mLocationService.getPlaceById(placeId).subscribe(new Subscriber<PlaceBuffer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onLoad.onFail(e.toString());
            }

            @Override
            public void onNext(PlaceBuffer places) {
                if(places.getCount()>0)
                    onLoad.onSuccess(places.get(0));
                places.release();
            }
        }));
    }
}
