package com.other.place.internal.di.component;

import com.other.place.internal.di.module.PlaceModelModule;
import com.other.place.internal.di.scope.PerPlaceModel;
import com.other.place.internal.mvp.presenter.PlacePresenter;
import com.other.place.ui.fragment.ContentFragment;
import com.other.place.ui.fragment.MapFragment;

import dagger.Component;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@PerPlaceModel
@Component(dependencies = AppComponent.class,modules={PlaceModelModule.class})
public interface PlaceModelComponent {
    void inject(ContentFragment contentFragment);
    void inject(MapFragment mapFragment);
    PlacePresenter placePresenter();
}
