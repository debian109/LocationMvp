package com.other.place.network;

import com.other.place.model.PlaceList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by thinh.vo on 16/11/2016.
 */

public interface PlaceApi {
    public  String API_KEY="AIzaSyCgFmIUHfP8eRtPWGhj06wdet7COVMhT3o";
    public  String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    @GET("json")
    Observable<PlaceList> NearBySearch(@Query("location") String location, @Query("radius") int radius, @Query("type") String type, @Query("keyword") String keyword, @Query("key") String key);
}
