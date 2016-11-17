package com.other.place.internal.mvp.view;

import com.google.android.gms.location.places.Place;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public interface PlaceDetailView{
    void populate(Place data);
}
