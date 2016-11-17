package com.other.place.internal.di.component;

import com.other.place.internal.di.module.LocationServiceModule;
import com.other.place.internal.di.scope.PerActivity;
import com.other.place.ui.MainActivity;

import dagger.Component;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {LocationServiceModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
}
