package com.other.place.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thinh.vo on 07/11/2016.
 */

public class Geometry implements Serializable {
    @SerializedName("location")
    public LatLng latlng;
}
