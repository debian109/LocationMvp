package com.other.place.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.android.gms.location.LocationSettingsStates;
import com.other.place.R;
import com.other.place.internal.di.component.DaggerActivityComponent;
import com.other.place.internal.di.module.LocationServiceModule;
import com.other.place.location.LocationService;
import com.other.place.preference.AppSharePreference;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    LocationService mLocationService;
    @Inject
    AppSharePreference mAppSharePreference;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private boolean mShowList = true;
    private int mCurrentNavigation;
    private String[] keys;
    private String[] titles;
    public Drawable mIconNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerActivityComponent.builder()
                .appComponent(PlaceApp.getAppComponent())
                .locationServiceModule(new LocationServiceModule(this)).build()
                .inject(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        keys = getResources().getStringArray(R.array.nav_item_activity_key);
        titles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mIconNavigation = mToolbar.getNavigationIcon();
        mCurrentNavigation= mAppSharePreference.getInt("current");
        mAppSharePreference.setString("key", keys[mCurrentNavigation]);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(mIconNavigation);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        if(savedInstanceState==null)
        {
            setTitle();
            RxPermissions
                    .getInstance(this)
                    .request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                onGrantedPermission();
                            } else {
                                finish();
                            }
                        }
                    });
            replaceContentFragment();
        }



    }


    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mToolbar.setNavigationIcon(mIconNavigation);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
            getSupportActionBar().setTitle(titles[mCurrentNavigation]);
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);//intent);
        switch (requestCode) {
            case LocationService.REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        if (states.isLocationPresent()) {
                            setTitle();
                            replaceContentFragment();
                        }
                        break;
                    case RESULT_CANCELED:
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map) {
            mShowList = !mShowList;
            if (mShowList)
                item.setIcon(R.drawable.ic_satellite);
            else
                item.setIcon(R.drawable.ic_description);
            changeFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_atm) {
            mCurrentNavigation = 0;
        } else if (id == R.id.nav_bar) {
            mCurrentNavigation = 1;
        } else if (id == R.id.nav_coffee) {
            mCurrentNavigation = 2;
        } else if (id == R.id.nav_gas) {
            mCurrentNavigation = 3;
        } else if (id == R.id.nav_hospital) {
            mCurrentNavigation = 4;
        } else if (id == R.id.nav_hotel) {
            mCurrentNavigation = 5;
        } else if (id == R.id.nav_movie_theater) {
            mCurrentNavigation = 6;
        } else if (id == R.id.nav_movie) {
            mCurrentNavigation = 7;
        } else if (id == R.id.nav_parking) {
            mCurrentNavigation = 8;
        } else if (id == R.id.nav_pharmacy) {
            mCurrentNavigation = 9;
        } else if (id == R.id.nav_pub) {
            mCurrentNavigation = 10;
        } else if (id == R.id.nav_res) {
            mCurrentNavigation = 11;
        } else if (id == R.id.nav_supermarket) {
            mCurrentNavigation = 12;
        } else if (id == R.id.nav_taxi) {
            mCurrentNavigation = 13;
        } else if (id == R.id.nav_theater) {
            mCurrentNavigation = 14;
        }
        mAppSharePreference.setInt("current",mCurrentNavigation);
        mAppSharePreference.setString("key", keys[mCurrentNavigation]);

        setTitle();
        replaceContentFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void replaceContentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentById(R.id.frame);
        if (fragment == null) {
            if (mShowList) {
                fragment = ContentFragment.newInstance();
            } else {
                fragment = MapFragment.newInstance();
            }
            fragmentManager.beginTransaction().
                    replace(R.id.frame, fragment)
                    .commitAllowingStateLoss();
        } else {
            fragment.onRequest();
        }
    }

    private void changeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment fragment = null;
        if (fragment == null) {
            if (mShowList) {
                fragment = ContentFragment.newInstance();
            } else {
                fragment = MapFragment.newInstance();
            }
            fragmentManager.beginTransaction().
                    replace(R.id.frame, fragment)
                    .commitAllowingStateLoss();
        }
    }

    private void setTitle() {
        getSupportActionBar().setTitle(titles[mCurrentNavigation]);
    }


    private void onGrantedPermission() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {
                        if(isConnectedToInternet)
                        {
                            replaceContentFragment();
                        }else
                        {
                            MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                                    .title("Network!")
                                    .content("No Network!")
                                    .positiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            //finish();
                                        }
                                    });

                            MaterialDialog dialog = builder.build();
                            if(!isDestroyed() && !isFinishing())
                                dialog.show();
                        }
                    }
                });
        mLocationService.locationUpdatesObservable.subscribe(new Action1<Location>() {
            @Override
            public void call(Location location) {
                mAppSharePreference.setString("location", location.getLatitude() + "," + location.getLongitude());
                mAppSharePreference.setString("lat", String.valueOf(location.getLatitude()));
                mAppSharePreference.setString("lng", String.valueOf(location.getLongitude()));
                replaceContentFragment();
            }
        });
    }
}
