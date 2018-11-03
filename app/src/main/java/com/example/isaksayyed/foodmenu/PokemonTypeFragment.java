package com.example.isaksayyed.foodmenu;


import android.os.Bundle;
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
import com.example.isaksayyed.foodmenu.Model.Pokemon;
import com.example.isaksayyed.foodmenu.Retrofit.IPokemonDex;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonTypeFragment extends Fragment {

    RecyclerView recyclerView;
    pokemonListAdapter pokemonListAdapter,searchAdapter;;
    List<String> last_suggested = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    List<Pokemon>typeList;
    static  PokemonTypeFragment instance;

    public static PokemonTypeFragment getInstance(){
        if (instance == null)
            instance = new PokemonTypeFragment();
        return instance;
    }



    public PokemonTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pokemon_type, container, false);


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


        if (getArguments() != null){
            String type = getArguments().getString("type");
            if (type !=null){
                typeList = Common.findPokemonsByType(type);
                pokemonListAdapter = new pokemonListAdapter(getActivity(),typeList);
                 recyclerView.setAdapter(pokemonListAdapter);
                loadSuggest();
            }
        }
        return view;
    }

    private void loadSuggest() {
        last_suggested.clear();
        if (typeList.size()>0){
            for (Pokemon pokemon:typeList)
                last_suggested.add(pokemon.getName());
            materialSearchBar.setLastSuggestions(last_suggested);
        }
    }


    private void startSearch(CharSequence text) {
        if (typeList.size()>0){
            List<Pokemon>result  = new ArrayList<>();
            for (Pokemon pokemon: typeList){
                if (pokemon.getName().contains(text))
                    result.add(pokemon);
                searchAdapter = new pokemonListAdapter(getActivity(),result);
                recyclerView.setAdapter(searchAdapter);
            }
        }
    }

}
