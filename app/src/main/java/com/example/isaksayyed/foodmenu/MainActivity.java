package com.example.isaksayyed.foodmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.isaksayyed.foodmenu.Common.Common;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver showPokemonType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().toString().equals(Common.Key_Pokemon_Type)){

                //Replace Fragment
                android.support.v4.app.Fragment pokemonType = PokemonTypeFragment.getInstance();
                String type = intent.getStringExtra("type");
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                pokemonType.setArguments(bundle);

                getSupportFragmentManager().popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon,pokemonType);
                fragmentTransaction.addToBackStack("type");
                fragmentTransaction.commit();
            }
        }
    };

    BroadcastReceiver showDetails = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //Replace Fragment
                android.support.v4.app.Fragment detailsFragment = PokemonDetails.getInstances();
                String num = intent.getStringExtra("num");
                Bundle bundle = new Bundle();
                bundle.putString("num",num);
                detailsFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon,detailsFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Pokemon");

        //Register Broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetails,new IntentFilter(Common.KEY_ENABLE_HOME));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution,new IntentFilter(Common.Key_num_Evolution));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showPokemonType,new IntentFilter(Common.Key_Pokemon_Type));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){

        case android.R.id.home:

            //Replace fragment
            android.support.v4.app.Fragment pokemonList = PokemonList.getInstance();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(pokemonList);
            fragmentTransaction.replace(R.id.list_pokemon,pokemonList);
            fragmentTransaction.commit();

            break;
            default:
                break;
    }
    return true;
    }

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().toString().equals(Common.Key_num_Evolution)){


                getSupportFragmentManager().popBackStack("details",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStack("type",FragmentManager.POP_BACK_STACK_INCLUSIVE);


                //Replace Fragment
                android.support.v4.app.Fragment detailsFragment = PokemonDetails.getInstances();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num",num);
                detailsFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailsFragment);
                fragmentTransaction.replace(R.id.list_pokemon,detailsFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();
            }
        }
    };


}
