package com.other.place.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thinh.vo on 04/11/2016.
 */

public class PlaceItem {
    @SerializedName("geometry")
    public Geometry geometry;
    @SerializedName("name")
    public String name;
    @SerializedName("place_id")
    public String place_id;
    @SerializedName("vicinity")
    public String vicinity;
}
