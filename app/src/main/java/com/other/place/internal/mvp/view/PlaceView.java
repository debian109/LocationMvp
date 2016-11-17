package com.other.place.internal.mvp.view;

import com.other.place.internal.mvp.MVP;
import com.other.place.model.PlaceItem;

import java.util.List;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public interface PlaceView extends MVP.View{
    void populate(List<PlaceItem> data);
}
