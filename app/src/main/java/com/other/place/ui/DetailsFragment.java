package com.other.place.ui;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.other.place.R;
import com.other.place.internal.di.component.DaggerPlaceModelDetailComponent;
import com.other.place.internal.mvp.presenter.PlaceDetailPresenter;
import com.other.place.internal.mvp.view.PlaceDetailView;
import com.other.place.location.LocationService;
import com.other.place.preference.AppSharePreference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import easymvp.annotation.FragmentView;
import easymvp.annotation.Presenter;

@FragmentView(presenter = PlaceDetailPresenter.class)
public class DetailsFragment extends Fragment implements PlaceDetailView, OnMapReadyCallback {

    private static final String ARG_PLACE_ID = "placeId";
    @Inject
    @Presenter
    PlaceDetailPresenter placeDetailPresenter;
    @Inject
    AppSharePreference mAppSharePreference;
    @BindView(R.id.address_textview)
    TextView address_textview;
    @BindView(R.id.phone_textview)
    TextView phone_textview;
    @BindView(R.id.website_textview)
    TextView website_textview;
    @BindView(R.id.rating_textview)
    TextView rating_textview;
    @BindView(R.id.rating)
    RatingBar ratingbar;
    private String placeId;

    private GoogleMap googleMap;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(String placeid) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_ID, placeid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeId = getArguments().getString(ARG_PLACE_ID);
        }
        setHasOptionsMenu(true);
        DaggerPlaceModelDetailComponent.builder().locationService(new LocationService(getActivity())).appComponent(PlaceApp.getAppComponent()).build().inject(this);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_map);
        item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String lat = mAppSharePreference.getString("lat");
        String lng = mAppSharePreference.getString("lng");
        Location location = new Location("");
        if (lat.length() > 0 && lng.length() > 0) {
            location.setLatitude(Double.parseDouble(lat));
            location.setLongitude(Double.parseDouble(lng));
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(17)
                .build();
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .camera(cameraPosition));
        fragmentTransaction.replace(R.id.map_detail, mapFragment).commit();
        mapFragment.getMapAsync(this);

        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        placeDetailPresenter.requestMap(placeId);
    }

    @Override
    public void populate(Place place) {

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18));
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(place.getLatLng())).showInfoWindow();

        if (place.getPhoneNumber().length() > 0)
            phone_textview.setText(place.getPhoneNumber());
        else
            phone_textview.setText("No Phone Number!");
        address_textview.setText(place.getAddress());
        if (place.getWebsiteUri() != null)
            website_textview.setText(place.getWebsiteUri().toString());
        else
            website_textview.setText("No Website!");
        ratingbar.setNumStars(5);
        ratingbar.setRating(place.getRating());
        float rate = place.getRating();
        if (rate < 0)
            rate = 0;
        rating_textview.setText(rating_textview.getText() + ": " + rate);
    }
}
