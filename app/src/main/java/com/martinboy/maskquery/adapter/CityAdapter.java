package com.martinboy.maskquery.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.martinboy.maskquery.MainActivity;
import com.martinboy.maskquery.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] cityArray;
    private Activity activity;

    public CityAdapter(Activity activity, String[] cityArray) {
        this.activity = activity;
        this.cityArray = cityArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).itemCityName.setText(cityArray[holder.getAdapterPosition()]);
        ((ViewHolder) holder).itemCityBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof MainActivity) {
                    Log.d("tag1", "cityArray[holder.getAdapterPosition()]): " + cityArray[holder.getAdapterPosition()]);
                    Bundle bundle = new Bundle();
                    bundle.putString("city", cityArray[holder.getAdapterPosition()]);
                    ((MainActivity) activity).switchFragment("drug", bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityArray != null ? cityArray.length : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemCityBg;
        TextView itemCityName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCityBg = itemView.findViewById(R.id.item_city_bg);
            itemCityName = itemView.findViewById(R.id.item_city_name);
        }
    }
}
