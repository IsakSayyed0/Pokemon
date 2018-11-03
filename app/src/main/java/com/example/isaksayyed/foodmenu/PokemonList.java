package com.example.isaksayyed.foodmenu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaksayyed.foodmenu.Adapter.pokemonListAdapter;
import com.example.isaksayyed.foodmenu.Common.Common;
import com.example.isaksayyed.foodmenu.Common.ItemoffsetDecoration;
import com.example.isaksayyed.foodmenu.Model.Pokedex;
import com.example.isaksayyed.foodmenu.Model.Pokemon;
import com.example.isaksayyed.foodmenu.Retrofit.IPokemonDex;
import com.example.isaksayyed.foodmenu.Retrofit.RetrofitClient;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView recyclerView;

    pokemonListAdapter pokemonListAdapter,searchAdapter;
    List<String>last_suggested = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

   static  PokemonList instance;

   public  static PokemonList getInstance(){
       if (instance == null)
           instance = new PokemonList();
       return instance;
    }



    public PokemonList() {
        // Required empty public constructor
        Retrofit retrofit= RetrofitClient.getInstance();
        iPokemonDex =retrofit.create(IPokemonDex.class);

   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_pokemon_list,container,false);
      recyclerView = view.findViewById(R.id.list_pokemon_recycler);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ItemoffsetDecoration itemoffsetDecoration= new ItemoffsetDecoration(getActivity(), R.dimen.spacing);
        recyclerView.addItemDecoration(itemoffsetDecoration);

        materialSearchBar = (MaterialSearchBar)view.findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter Pokemon name");
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for(String search:last_suggested){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(pokemonListAdapter);//returns default adapter
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        fetchData();
      return view;
    }

    private void startSearch(CharSequence text) {
        if (Common.commonPokemonList.size()>0){
            List<Pokemon>result  = new ArrayList<>();
            for (Pokemon pokemon: Common.commonPokemonList){
                if (pokemon.getName().contains(text))
                    result.add(pokemon);
                searchAdapter = new pokemonListAdapter(getActivity(),result);
                recyclerView.setAdapter(searchAdapter);
            }
        }
    }

    private void fetchData() {
        compositeDisposable.add(iPokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Pokedex>() {
            @Override
            public void accept(Pokedex pokedex) throws Exception {

                Common.commonPokemonList = pokedex.getPokemon();
                 pokemonListAdapter = new pokemonListAdapter(getActivity(),Common.commonPokemonList);

                 last_suggested.clear();
                 for (Pokemon pokemon:Common.commonPokemonList)
                     last_suggested.add(pokemon.getName());
                     materialSearchBar.setVisibility(View.VISIBLE);
                     materialSearchBar.setLastSuggestions(last_suggested);
                recyclerView.setAdapter(pokemonListAdapter);
            }
        }));
    }
}




