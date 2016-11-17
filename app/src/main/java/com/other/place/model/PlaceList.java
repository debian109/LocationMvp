package com.other.place.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thinh.vo on 04/11/2016.
 */

public class PlaceList {
    @SerializedName("next_page_token")
    public String next_page_token;
    @SerializedName("results")
    public List<PlaceItem> listPlaceItem;
}
