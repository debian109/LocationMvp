package com.other.place.ui;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.other.place.R;
import com.other.place.internal.di.component.DaggerPlaceModelComponent;
import com.other.place.internal.mvp.presenter.MapPresenter;
import com.other.place.internal.mvp.view.MapView;
import com.other.place.model.PlaceItem;
import com.other.place.preference.AppSharePreference;

import java.util.List;

import javax.inject.Inject;

import easymvp.annotation.FragmentView;
import easymvp.annotation.Presenter;


@FragmentView(presenter = MapPresenter.class)
public class MapFragment extends BaseFragment implements MapView, OnMapReadyCallback {

    @Inject
    @Presenter
    MapPresenter presenter;

    @Inject
    AppSharePreference mAppSharePreference;

    GoogleMap mGoogleMap;

    public MapFragment() {

    }
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPlaceModelComponent.builder().appComponent(PlaceApp.getAppComponent()).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String lat=mAppSharePreference.getString("lat");
        String lng=mAppSharePreference.getString("lng");
        Location location=new Location("");
        if(lat.length()>0&& lng.length()>0)
        {
            location.setLatitude(Double.parseDouble(lat));
            location.setLongitude(Double.parseDouble(lng));
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(14)
                .build();
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .camera(cameraPosition));
        if(!getActivity().isFinishing() && !getActivity().isDestroyed())
            fragmentTransaction.replace(R.id.map_detail, mapFragment).commit();

        mapFragment.getMapAsync(this);

        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        presenter.requestApi();
    }

    @Override
    public void populate(List<PlaceItem> list) {
        mGoogleMap.clear();
        for (int i = 0; i < list.size(); i++) {
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).geometry.latlng.lat, list.get(i).geometry.latlng.lng)));
        }
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        String lat=mAppSharePreference.getString("lat");
        String lng=mAppSharePreference.getString("lng");
        Location location=new Location("");
        if(lat.length()>0&& lng.length()>0)
        {
            location.setLatitude(Double.parseDouble(lat));
            location.setLongitude(Double.parseDouble(lng));
        }
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    @Override
    protected void onRequest() {
        if(mGoogleMap!=null)
            presenter.requestApi();
    }
}
