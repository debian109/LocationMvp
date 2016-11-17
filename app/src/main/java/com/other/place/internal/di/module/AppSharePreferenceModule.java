package com.other.place.internal.di.module;

import android.content.Context;

import com.other.place.preference.AppSharePreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thinh.vo on 16/11/2016.
 */

@Module
public class AppSharePreferenceModule {
    private Context context;

    public AppSharePreferenceModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    AppSharePreference provideSharedPreferences() {
        return new AppSharePreference(context);
    }

}
