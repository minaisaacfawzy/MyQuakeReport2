package com.example.android.myquakereport2;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by M on 30/06/2018.
 */

public class EarthquakeViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout parentLayout;
    TextView txtvMag,txtvLoc,txtvDate,txtvTime,txtvOffset;

    public EarthquakeViewHolder(final View itemView) {
        super(itemView);
        txtvMag = itemView.findViewById(R.id.txtvMag2);
        txtvLoc = itemView.findViewById(R.id.txtvLocation2);
        txtvDate  = itemView.findViewById(R.id.txtvDate2);
        txtvTime = itemView.findViewById(R.id.txtvTime2);
        txtvOffset = itemView.findViewById(R.id.txtvOffset2);
        parentLayout = itemView.findViewById(R.id.constraint_layout);

    }
}
