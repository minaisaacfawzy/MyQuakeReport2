package com.example.android.myquakereport2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 30/06/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {
    Context context;
    List<Earthquake> earthquakes = new ArrayList<>();
    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;


    public RecyclerViewAdapter(Context context,List<Earthquake> earthquakes) {
        this.context = context;
        this.earthquakes = earthquakes;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);

        return new EarthquakeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, final int position) {
        Earthquake e = earthquakes.get(position);

        holder.txtvDate.setText(QueryUtils.formatDate(e.getmTimeInMilliSeconds()));

        holder.txtvTime.setText(QueryUtils.formatTime(e.getmTimeInMilliSeconds()));
        holder.txtvMag.setText(QueryUtils.formatMag(e.getmMag()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.txtvMag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = QueryUtils.getMagnitudeColor(e.getmMag(),context);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String wholeLocation = e.getmLoc();
        if(wholeLocation.contains(",")){
            String[] s = wholeLocation.split(",");
            holder.txtvOffset.setText(s[0]+",");
            holder.txtvLoc.setText(s[1]);
        }else{
            holder.txtvOffset.setText("Near by, ");
            holder.txtvLoc.setText(wholeLocation);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Earthquake e = earthquakes.get(position);
                Uri currUrl = Uri.parse(e.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,currUrl);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return earthquakes.size();
    }
}
