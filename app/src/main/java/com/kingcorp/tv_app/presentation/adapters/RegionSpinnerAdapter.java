package com.kingcorp.tv_app.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingcorp.tv_app.R;

public class RegionSpinnerAdapter extends ArrayAdapter<String> {

    private String[] mRegionText;
    private int[] flagImageIds = {
            R.drawable.ic_russia,
            R.drawable.ic_belarus,
            R.drawable.ic_kazakhstan,
            R.drawable.ic_ukraine,
            R.drawable.ic_uzbekistan
    };

    public RegionSpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] regionText) {
        super(context, resource, regionText);

        this.mRegionText = regionText;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region_spinner_main, parent, false);

        ImageView regionFlag = view.findViewById(R.id.region_flag);
        regionFlag.setImageResource(flagImageIds[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region_spinner_dropdown, parent, false);

        TextView regionText = view.findViewById(R.id.region_text);
        regionText.setText(mRegionText[position]);

        ImageView regionFlag = view.findViewById(R.id.region_flag);
        regionFlag.setImageResource(flagImageIds[position]);

        return view;
    }
}
