package com.bachelor_group54.funnregistrering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RegistrereFunnFragment registrereFunnFragment;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        mainFragment = new MainFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, mainFragment);
        fragmentTransaction.commit();

    }

    public void nyeFunnBtn(View view) {
        registrereFunnFragment = new RegistrereFunnFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
        fragmentTransaction.replace(R.id.layout, registrereFunnFragment); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
        fragmentTransaction.addToBackStack(""); //Legger Fragmentet på stack så back knappen fungerer
        fragmentTransaction.commit();
    }

    public void mineFunnBtn(View view) {
    }

    public void infoBtn(View view) {
    }


    //RegistrereFunn knapper under
    public void bildeBtn(View view) {
       registrereFunnFragment.bildeBtn(this);
    }

    public void gpsBtn(View view) {
        registrereFunnFragment.gpsBtn(this);
    }

    public void registrerFunnBtn(View view) {
        registrereFunnFragment.registrerFunnBtn(this);
    }
}