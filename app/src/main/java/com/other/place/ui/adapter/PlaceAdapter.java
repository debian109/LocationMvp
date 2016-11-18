package com.other.place.ui.adapter;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.other.place.R;
import com.other.place.model.LatLng;
import com.other.place.model.PlaceItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinh.vo on 04/11/2016.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    public List<PlaceItem> getList() {
        return list;
    }

    private List<PlaceItem> list;
    private Location mLocation;

    public PlaceAdapter(List<PlaceItem> list, AdapterView.OnItemClickListener onItemClickListener, Location location) {
        this.list = list;
        this.mOnItemClickListener=onItemClickListener;
        this.mLocation=location;
    }

    AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        PlaceItem placeItem =list.get(position);
        holder.name.setText(placeItem.name);
        holder.vicinity.setText(placeItem.vicinity);
        if(mLocation!=null && placeItem.geometry!=null)
        {
            Location location= convertLocation(placeItem.geometry.latlng);
            String distanceString= String.format("%.2f km",location.distanceTo(mLocation)/1000);
            holder.distance.setText(distanceString);
        }

    }

    private Location convertLocation(LatLng location)
    {
        if(mLocation!=null)
        {
            Location l=new Location(mLocation.getProvider());
            l.setLatitude(location.lat);
            l.setLongitude(location.lng);
            return l;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name_textview)
        public TextView name;
        @BindView(R.id.vicinity_textview)
        public TextView vicinity;
        @BindView(R.id.distance_textview)
        public TextView distance;
        @BindView(R.id.direction_imageview)
        public ImageView direction;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null)
            {
                mOnItemClickListener.onItemClick(null,v,getLayoutPosition(),v.getId());
            }
        }
    }
}
