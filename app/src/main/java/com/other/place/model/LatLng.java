package com.other.place.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thinh.vo on 07/11/2016.
 */

public class LatLng implements Serializable {
    @SerializedName("lat")
    public float lat;
    @SerializedName("lng")
    public float lng;
}
