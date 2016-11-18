package com.other.place.internal.di.component;

import com.other.place.internal.di.module.PlaceModelDetailModule;
import com.other.place.internal.di.scope.PerPlaceDetailModel;
import com.other.place.internal.mvp.presenter.PlaceDetailPresenter;
import com.other.place.location.LocationService;
import com.other.place.ui.fragment.DetailsFragment;

import dagger.Component;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@PerPlaceDetailModel
@Component(dependencies = {AppComponent.class, LocationService.class},modules={PlaceModelDetailModule.class})
public interface PlaceModelDetailComponent {
    void inject(DetailsFragment contentFragment);
    PlaceDetailPresenter placeDetailPresenter();
}
