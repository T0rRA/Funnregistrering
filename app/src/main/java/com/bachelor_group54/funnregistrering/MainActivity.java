package com.bachelor_group54.funnregistrering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private FragmentRegistrereFunn fragmentRegistrereFunn;
    private FragmentMain fragmentMain;
    private FragmentRegistrereBruker fragmentRegistrereBruker;
    private FragmentMineFunn fragmentMineFunn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        fragmentMain = new FragmentMain();

        //Sets the homepage fragment to the view
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, fragmentMain);
        fragmentTransaction.commit();

    }

    //Buttons for nyeFunnFragment
    public void nyeFunnBtn(View view) {
        fragmentRegistrereFunn = new FragmentRegistrereFunn();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
        fragmentTransaction.replace(R.id.layout, fragmentRegistrereFunn); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
        fragmentTransaction.addToBackStack(""); //Puts the fragment on the stack, so back button will work
        fragmentTransaction.commit();
    }

    public void mineFunnBtn(View view) {
        fragmentMineFunn = new FragmentMineFunn();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
        fragmentTransaction.replace(R.id.layout, fragmentMineFunn); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
        fragmentTransaction.addToBackStack(""); //Puts the fragment on the stack, so back button will work
        fragmentTransaction.commit();
    }

    public void infoBtn(View view) {
    }


    //Buttons for FragmentRegistrereFunn
    public void bildeBtn(View view) {
       fragmentRegistrereFunn.bildeBtn();
    }

    public void gpsBtn(View view) {
        fragmentRegistrereFunn.gpsBtn();
    }

    public void registrerFunnBtn(View view) {
        fragmentRegistrereFunn.registrerFunnBtn();
    }

    public void regUserBtn(View view){
        fragmentRegistrereBruker = new FragmentRegistrereBruker();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
        fragmentTransaction.replace(R.id.layout, fragmentRegistrereBruker); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
        fragmentTransaction.addToBackStack(""); //Legger Fragmentet på stack så back knappen fungerer
        fragmentTransaction.commit();

    }
    public void saveUserBtn(View view){
        fragmentRegistrereBruker.saveUserBtn();
    }
}