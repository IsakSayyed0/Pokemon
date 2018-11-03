package com.example.isaksayyed.foodmenu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaksayyed.foodmenu.Common.Common;
import com.example.isaksayyed.foodmenu.Interface.ItemClickListner;
import com.example.isaksayyed.foodmenu.R;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.List;

public class pokemonTypeAdapter extends RecyclerView.Adapter<pokemonTypeAdapter.MyViewHolder> {

    Context context;
    List<String> typeList;
    public pokemonTypeAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.chip_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.chip.setChipText(typeList.get(position));
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList.get(position)));

    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        Chip chip;
    ItemClickListner itemClickListner;
        public MyViewHolder(View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.Key_Pokemon_Type).putExtra("type",typeList.get(getAdapterPosition())));
                }
            });
        }
    }
}
