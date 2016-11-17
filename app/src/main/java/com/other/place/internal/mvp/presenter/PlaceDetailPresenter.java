package com.other.place.internal.mvp.presenter;

import com.google.android.gms.location.places.Place;
import com.other.place.internal.mvp.MVP;
import com.other.place.internal.mvp.model.PlaceDetailModel;
import com.other.place.internal.mvp.view.PlaceDetailView;

import javax.inject.Inject;

import easymvp.AbstractPresenter;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public class PlaceDetailPresenter extends AbstractPresenter<PlaceDetailView> implements MVP.Model.OnLoad<Place> {

    PlaceDetailModel placeDetailModel;

    @Inject
    public PlaceDetailPresenter(PlaceDetailModel placeDetailModel) {
        this.placeDetailModel = placeDetailModel;
    }

    @Override
    public void onViewAttached(PlaceDetailView view) {
        super.onViewAttached(view);
    }

    public void requestMap(String placeId) {
        placeDetailModel.requestMapDetail(placeId, this);
    }

    @Override
    public void onSuccess(Place data) {
        getView().populate(data);
    }

    @Override
    public void onFail(String error) {

    }
}
