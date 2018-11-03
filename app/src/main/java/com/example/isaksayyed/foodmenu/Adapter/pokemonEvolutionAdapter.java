package com.example.isaksayyed.foodmenu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.isaksayyed.foodmenu.Common.Common;

import com.example.isaksayyed.foodmenu.Interface.ItemClickListner;
import com.example.isaksayyed.foodmenu.Model.Evolution;
import com.example.isaksayyed.foodmenu.R;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

public class pokemonEvolutionAdapter  extends RecyclerView.Adapter<pokemonEvolutionAdapter.MyViewHolder> {

    Context context;
    List<Evolution>evolutionList;

    public pokemonEvolutionAdapter(Context context, List<Evolution> evolutionList) {
        this.context = context;
          if (evolutionList != null)
        this.evolutionList = evolutionList;
        else
            this.evolutionList = new ArrayList<>();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chip_items,parent,false);
        return new pokemonEvolutionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.chip.setChipText(evolutionList.get(position).getName());
        holder.chip.changeBackgroundColor(
                Common.getColorByType(
                        Common.findPokemonbynum(
                                evolutionList.get(position).getNum()
                        ).getType()
                        .get(0)
                )
        );


    }


    @Override
    public int getItemCount() {
        return evolutionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Chip chip;


        public MyViewHolder(View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.Key_num_Evolution).putExtra("num",evolutionList.get(getAdapterPosition()).getNum()));

                }
            });
        }
    }
}