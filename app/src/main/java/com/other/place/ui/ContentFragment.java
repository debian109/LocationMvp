package com.other.place.ui;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.other.place.R;
import com.other.place.internal.di.component.DaggerPlaceModelComponent;
import com.other.place.internal.mvp.presenter.PlacePresenter;
import com.other.place.internal.mvp.view.PlaceView;
import com.other.place.model.PlaceItem;
import com.other.place.preference.AppSharePreference;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import easymvp.annotation.FragmentView;
import easymvp.annotation.Presenter;


/**
 * A simple {@link Fragment} subclass.
 */
@FragmentView(presenter = PlacePresenter.class)
public class ContentFragment extends BaseFragment implements PlaceView, AdapterView.OnItemClickListener {

    @Inject
    @Presenter
    PlacePresenter presenter;

    @Inject
    AppSharePreference mAppSharePreference;

    @BindView(R.id.place_recycleview)
    RecyclerView mRecycleView;

    @BindView(R.id.content_progress)
    ProgressBar pb;

    @BindView(R.id.no_item_textview)
    TextView no_item_textview;

    PlaceAdapter mAdapter;

    public ContentFragment() {
    }

    public static ContentFragment newInstance() {
        ContentFragment fragment = new ContentFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPlaceModelComponent.builder().appComponent(PlaceApp.getAppComponent()).build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void populate(final List<PlaceItem> list) {
        String lat = mAppSharePreference.getString("lat");
        String lng = mAppSharePreference.getString("lng");
        Location location = new Location("");
        if (lat.length() > 0 && lng.length() > 0) {
            location.setLatitude(Double.parseDouble(lat));
            location.setLongitude(Double.parseDouble(lng));
        }
        mAdapter = new PlaceAdapter(list, this, location);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        List<PlaceItem> placeItems = mAdapter.getList();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = DetailsFragment.newInstance(placeItems.get(i).place_id);
        fragmentTransaction.add(R.id.frame, fragment).addToBackStack("hello").commit();
    }

    @Override
    public void showLoading() {
        pb.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        no_item_textview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        //Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        no_item_textview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showContent() {
        pb.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
        no_item_textview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmpty() {
        pb.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        no_item_textview.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRequest() {
        presenter.requestApi();
    }
}
