package com.other.place.internal.mvp.presenter;

import com.other.place.internal.mvp.MVP;
import com.other.place.internal.mvp.model.PlaceModel;
import com.other.place.internal.mvp.view.PlaceView;
import com.other.place.model.PlaceItem;

import java.util.List;

import javax.inject.Inject;

import easymvp.AbstractPresenter;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public class PlacePresenter extends AbstractPresenter<PlaceView> implements MVP.Model.OnLoad<List<PlaceItem>> {

    PlaceModel placeModel;

    @Inject
    public PlacePresenter(PlaceModel placeModel) {
        this.placeModel=placeModel;
    }


    public void requestApi()
    {
        getView().showLoading();
        placeModel.RequestLocation(this);
    }

    @Override
    public void onViewAttached(PlaceView view) {
        super.onViewAttached(view);
        requestApi();
    }


    @Override
    public void onSuccess(List<PlaceItem> data) {
        try {
            if(data.size()>0)
            {
                getView().populate(data);
                getView().showContent();
            }else
            {
                getView().showEmpty();
            }
        }catch (Exception e)
        {

        }


    }

    @Override
    public void onFail(String error) {
        getView().showError(error);
    }
}
