package com.example.isaksayyed.foodmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.isaksayyed.foodmenu.Adapter.pokemonEvolutionAdapter;
import com.example.isaksayyed.foodmenu.Adapter.pokemonTypeAdapter;
import com.example.isaksayyed.foodmenu.Common.Common;
import com.example.isaksayyed.foodmenu.Model.Pokemon;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonDetails extends Fragment {


    ImageView pokemon_img;
    TextView pokemon_name,pokemon_weight,pokemon_height;
    RecyclerView recyclerView_type,recyclerView_weakness,recyclerView_pre_evolution,recyclerView_next_evolution;

    static PokemonDetails instances;

    public static PokemonDetails getInstances() {
        if (instances == null)
            instances = new PokemonDetails();
        return instances;
    }

    public PokemonDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        pokemon_img = view.findViewById(R.id.image_pokemon);
        pokemon_name = view.findViewById(R.id.name);
        pokemon_height = view.findViewById(R.id.height);
        pokemon_weight = view.findViewById(R.id.weight);

        Pokemon pokemon= Common.findPokemonbynum(getArguments().getString("num"));

        recyclerView_type =view.findViewById(R.id.recycler_type);
        recyclerView_type.setHasFixedSize(true);
        recyclerView_type.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView_weakness =view.findViewById(R.id.recycler_weakness);
        recyclerView_weakness.setHasFixedSize(true);
        recyclerView_weakness.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView_next_evolution =view.findViewById(R.id.recycler_nextEvolution);
        recyclerView_next_evolution.setHasFixedSize(true);
        recyclerView_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView_pre_evolution =view.findViewById(R.id.recycler_preEvolution);
        recyclerView_pre_evolution.setHasFixedSize(true);
        recyclerView_pre_evolution.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        setDetailsPokemon(pokemon);

        return view;
    }

    private void setDetailsPokemon(Pokemon pokemon) {
        //Load image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);

        //set the texts
        pokemon_name.setText(pokemon.getName());
        pokemon_weight.setText("Weight:"+pokemon.getWeight());
        pokemon_height.setText("Height:"+pokemon.getHeight());

        //setType
        pokemonTypeAdapter pokemonTypeAdapter = new pokemonTypeAdapter(getActivity(),pokemon.getType()) ;
        recyclerView_type.setAdapter(pokemonTypeAdapter);

        //setWeakness
        pokemonTypeAdapter pokemonWeaknessAdapter = new pokemonTypeAdapter(getActivity(),pokemon.getWeaknesses()) ;
        recyclerView_weakness.setAdapter(pokemonWeaknessAdapter);

        //setEvolution
        pokemonEvolutionAdapter pokemonPrevAdapter = new pokemonEvolutionAdapter(getActivity(),pokemon.getPrev_evolution()) ;
        recyclerView_pre_evolution.setAdapter(pokemonPrevAdapter);

        pokemonEvolutionAdapter pokemonNextAdapter = new pokemonEvolutionAdapter(getActivity(),pokemon.getNext_evolution()) ;
        recyclerView_next_evolution.setAdapter(pokemonNextAdapter);


    }

}
