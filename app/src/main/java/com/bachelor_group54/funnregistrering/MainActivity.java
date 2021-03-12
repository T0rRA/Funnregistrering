package com.bachelor_group54.funnregistrering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentRegistrereFunn fragmentRegistrereFunn;
    private FragmentRegistrereBruker fragmentRegistrereBruker;
    private FragmentMineFunn fragmentMineFunn;
    private FragmentEnkeltFunn fragmentEnkeltFunn;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    private boolean isEkeltFunnOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        fm = getSupportFragmentManager();

        //Initializing the fragments needed inn the app
        fragmentRegistrereFunn = new FragmentRegistrereFunn();
        fragmentMineFunn = new FragmentMineFunn();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        //Only if you want to start on element 1 in the list, no need if starting at 0
        mPager.setCurrentItem(0);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>" + getString(R.string.app_name) + "</font>")); //Changes the color of the actionbar text

    }

    //The ScreenSlidePagerAdapter holds the fragment from the navigation bar and makes sliding between them possible.
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> fragmentListe = new ArrayList<>();

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            //Adds the fragments to the slider
            fragmentListe.add(fragmentRegistrereFunn);
            fragmentListe.add(fragmentMineFunn);
            fragmentListe.add(new FragmentMain());
            //TODO legge til resten av fragmentene
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentListe.get(position);
        }

        @Override
        public int getCount() {
            return fragmentListe.size();
        }
    }

    @Override
    //Makes the back button work as expected
    public void onBackPressed() {
        if(isEkeltFunnOpen){
            fm.popBackStack();
            isEkeltFunnOpen = false;
            return;
        }
        if(mPager.getCurrentItem() == 0){
            finish(); //If the main page is the current page exit the app
        }else {
            mPager.setCurrentItem(0); //Goes back to the main page
        }
    }


    //Buttons for nyeFunnFragment
    public void nyeFunnBtn(View view) {
        mPager.setCurrentItem(1);
    }

    public void mineFunnBtn(View view) {
        mPager.setCurrentItem(0);
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
        mPager.setCurrentItem(0); //Returns to the mainPage
    }

    //Opens find from list
    public void openEnkeltFunn(Funn funn, int position){
        if(!isEkeltFunnOpen) {
            isEkeltFunnOpen = true;
            fragmentEnkeltFunn = new FragmentEnkeltFunn(funn, position);
            FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
            fragmentTransaction.replace(R.id.layout, fragmentEnkeltFunn); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
            fragmentTransaction.addToBackStack(""); //Puts the fragment on the stack, so back button will work
            fragmentTransaction.commit();
        }
    }

    //Saves the changes made to the find
    public void fragmentEnkeltFunnLagreEndring(View view) {
        fragmentEnkeltFunn.saveFind();
        fm.popBackStack();//Goes back to the slide fragments
    }

    public void fragmentEnkeltFunnUpdatePicture(View view){
        fragmentEnkeltFunn.bildeBtn();
    }

    public void navbarRegistrereFunn(View view) {
        if(isEkeltFunnOpen){return;}
        mPager.setCurrentItem(0);
    }

    public void navbarKart(View view) {
        if(isEkeltFunnOpen){return;}
        Toast.makeText(this, "Har ikke kartside enda", Toast.LENGTH_LONG).show();
    }

    public void navbarMineFunn(View view) {
        if(isEkeltFunnOpen){return;}
        mPager.setCurrentItem(1);

    }

    public void navbarProfil(View view) {
        if(isEkeltFunnOpen){return;}
        Toast.makeText(this, "Har ikke profilside enda", Toast.LENGTH_LONG).show();
    }

    public void navbarHjelp(View view) {
        if(isEkeltFunnOpen){return;}
        mPager.setCurrentItem(2); //Går til forsiden for nå
        Toast.makeText(this, "Har ikke hjelpside enda", Toast.LENGTH_LONG).show();
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