package com.martinboy.maskquery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.martinboy.database.MaskEntity;
import com.martinboy.maskquery.R;
import com.martinboy.maskquery.fragment.DrugFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class DrugAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DrugFragment fragment;
    private List<MaskEntity> drugList;

    public DrugAdapter(DrugFragment fragment, List<MaskEntity> drugList) {
        this.fragment = fragment;
        this.drugList = drugList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).itemDrugName.setText(drugList.get(holder.getAdapterPosition()).getName());
        ((ViewHolder) holder).itemDrugAddress.setText(drugList.get(holder.getAdapterPosition()).getAddress());
        ((ViewHolder) holder).itemDrugAdultMask.setText("成人口罩: " + drugList.get(holder.getAdapterPosition()).getAdult_mask());
        ((ViewHolder) holder).itemDrugChildMask.setText("兒童口罩: " + drugList.get(holder.getAdapterPosition()).getChild_mask());
        ((ViewHolder) holder).itemDrugDate.setText("更新時間: " + drugList.get(holder.getAdapterPosition()).getDate());
        if(!drugList.get(holder.getAdapterPosition()).getCustom_note().equals("")) {
            ((ViewHolder) holder).itemDrugCustomNote.setText(drugList.get(holder.getAdapterPosition()).getCustom_note());
        } else if (!drugList.get(holder.getAdapterPosition()).getNote().equals("")) {
            ((ViewHolder) holder).itemDrugCustomNote.setText(drugList.get(holder.getAdapterPosition()).getNote());
        }
        ((ViewHolder) holder).btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.queryMap(drugList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugList != null ? drugList.size() : 0;
    }

    public void setDrugList(List<MaskEntity> drugList) {
        this.drugList = drugList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout item_drug_bg;
        TextView itemDrugName, itemDrugAddress, itemDrugAdultMask, itemDrugChildMask, itemDrugDate, itemDrugCustomNote;
        Button btnMap;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnMap = itemView.findViewById(R.id.btn_map);
            item_drug_bg = itemView.findViewById(R.id.item_drug_bg);
            itemDrugName = itemView.findViewById(R.id.item_drug_name);
            itemDrugAddress = itemView.findViewById(R.id.item_drug_address);
            itemDrugAdultMask = itemView.findViewById(R.id.item_drug_adult_mask);
            itemDrugChildMask = itemView.findViewById(R.id.item_drug_child_mask);
            itemDrugCustomNote = itemView.findViewById(R.id.item_drug_custom_note);
            itemDrugDate = itemView.findViewById(R.id.item_drug_date);
        }
    }
}
