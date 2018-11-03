package com.example.isaksayyed.foodmenu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.isaksayyed.foodmenu.Common.Common;
import com.example.isaksayyed.foodmenu.Interface.ItemClickListner;
import com.example.isaksayyed.foodmenu.Model.Pokemon;
import com.example.isaksayyed.foodmenu.R;

import java.util.List;

public class pokemonListAdapter extends RecyclerView.Adapter<pokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon>pokemonList;

    public pokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Glide.with(context).load(pokemonList.get(position).getImg()).into(holder.pokemon_Image);
        holder.pokemon_Name.setText(pokemonList.get(position).getName());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int postion) {
                Toast.makeText(context, "Click"+pokemonList.get(position).getName(), Toast.LENGTH_SHORT).show();

                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("num",pokemonList.get(position).getNum()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemon_Image;
        TextView pokemon_Name;

        ItemClickListner itemClickListner;

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            pokemon_Image = itemView.findViewById(R.id.pokemon_image);
            pokemon_Name = itemView.findViewById(R.id.pokemon_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListner.onClick(view,getAdapterPosition());
        }


    }
}
