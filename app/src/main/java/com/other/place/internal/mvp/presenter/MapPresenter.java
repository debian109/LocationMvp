package com.other.place.internal.mvp.presenter;

import com.other.place.internal.mvp.MVP;
import com.other.place.internal.mvp.model.PlaceModel;
import com.other.place.internal.mvp.view.MapView;
import com.other.place.model.PlaceItem;

import java.util.List;

import javax.inject.Inject;

import easymvp.AbstractPresenter;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public class MapPresenter extends AbstractPresenter<MapView> implements MVP.Model.OnLoad<List<PlaceItem>> {

    PlaceModel placeModel;

    @Inject
    public MapPresenter(PlaceModel placeModel) {
        this.placeModel=placeModel;
    }


    public void requestApi()
    {
        placeModel.RequestLocation(this);
    }

    @Override
    public void onViewAttached(MapView view) {
        super.onViewAttached(view);
        requestApi();
    }


    @Override
    public void onSuccess(List<PlaceItem> data) {
        getView().populate(data);
    }

    @Override
    public void onFail(String error) {

    }
}
