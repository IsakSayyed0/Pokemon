package com.example.isaksayyed.foodmenu.Retrofit;

import com.example.isaksayyed.foodmenu.Model.Pokedex;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();




}
