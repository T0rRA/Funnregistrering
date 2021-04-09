package com.bachelor_group54.funnregistrering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentRegistrereFunn fragmentRegistrereFunn;
    private FragmentRegistrereBruker fragmentRegistrereBruker;
    private FragmentMineFunn fragmentMineFunn;
    private FragmentEnkeltFunn fragmentEnkeltFunn;
    private FragmentMain fragmentMain;

    private FragmentManager fm;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private FragmentLogin fragmentLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        fm = getSupportFragmentManager();

        //Initializing the fragments needed inn the app
        fragmentRegistrereFunn = new FragmentRegistrereFunn();
        fragmentMineFunn = new FragmentMineFunn();
        fragmentMain = new FragmentMain();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        //Only if you want to start on element 1 in the list, no need if starting at 0
        mPager.setCurrentItem(0);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>" + getString(R.string.app_name) + "</font>")); //Changes the color of the actionbar text
    }

    //TODO lage onResume som logger bruker inn igjen
    @Override
    protected void onStop() {
        SharedPreferences sharedpreferences = getSharedPreferences("user", MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");

        if(!username.equals("")) {
            SetJSON setJSON = new SetJSON();
            setJSON.execute("Bruker/LogOut", "brukernavn=" + username);
            Toast.makeText(this, "Logger ut", Toast.LENGTH_LONG).show();
        }

        super.onStop();
    }

    //The ScreenSlidePagerAdapter holds the fragment from the navigation bar and makes sliding between them possible.
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> fragmentListe = new ArrayList<>();

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            //Adds the fragments to the slider
            fragmentListe.add(fragmentRegistrereFunn);
            fragmentListe.add(fragmentMineFunn);
            fragmentListe.add(fragmentMain);
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

        @Override
        public void startUpdate(@NonNull ViewGroup container) {
            super.startUpdate(container);
            pagerAdapter.getItem(mPager.getCurrentItem()).onResume(); //Makes it so that we can update a fragment with its onResume method
        }
    }

    @Override
    //Makes the back button work as expected
    public void onBackPressed() {
        if (mPager.getVisibility() == View.GONE) {
            closeFragment();
            return;
        }
        if (mPager.getCurrentItem() == 0) {
            finish(); //If the main page is the current page exit the app
        } else {
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
        fragmentMain.setJSONTestBtn();
    }

    //Buttons for FragmentRegistrereFunn
    public void bildeBtn(View view) {
        fragmentRegistrereFunn.bildeBtn();
    }

    public void gpsBtn(View view) {
        fragmentRegistrereFunn.gpsBtn();
    }

    public void registrerFunnBtn(View view) {
        Funn funn = fragmentRegistrereFunn.registrerFunnBtn(); //Registers the find, and gets the find object back
        ((FragmentRegistrereFunn) pagerAdapter.getItem(mPager.getCurrentItem())).clearFields(); //Clears the fields in the register new find fragment
        mPager.setCurrentItem(1); //Goes to the found overview
        openEnkeltFunn(funn, fragmentMineFunn.getListSize() - 1); //Opens the find in the find list
    }

    //Opens find from list
    public void openEnkeltFunn(Funn funn, int position) {
        openFragment(fragmentEnkeltFunn);
    }

    //Buttons for the single found fragment
    //Saves the changes made to the find
    public void fragmentEnkeltFunnLagreEndring(View view) {
        fragmentEnkeltFunn.saveFind();
        closeFragment();
        fragmentMineFunn.makeList();
    }

    public void fragmentEnkeltFunnUpdatePicture(View view) {
        fragmentEnkeltFunn.bildeBtn();
    }

    public void fragmentEnkeltFunnSendFunnmeldingBtn(View view) {
        fragmentEnkeltFunn.sendFunnmelding();
    }

    public void fragmentEnkeltFunnSendFunnskjemaBtn(View view) {
        fragmentEnkeltFunn.sendFunnskjema();
    }


    //Navigation bar buttons
    public void navbarRegistrereFunn(View view) {
        mPager.setCurrentItem(0);
    }

    public void navbarKart(View view) {
        Toast.makeText(this, "Har ikke kartside enda", Toast.LENGTH_LONG).show();
    }

    public void navbarMineFunn(View view) {
        mPager.setCurrentItem(1);

    }

    public void navbarProfil(View view) {
        Toast.makeText(this, "Har ikke profilside enda", Toast.LENGTH_LONG).show();
    }

    public void navbarHjelp(View view) {
        mPager.setCurrentItem(2); //Går til forsiden for nå
        Toast.makeText(this, "Har ikke hjelpside enda", Toast.LENGTH_LONG).show();
    }

    //User fragment buttons
    public void regUserBtn(View view) {
        fragmentRegistrereBruker = new FragmentRegistrereBruker();
        openFragment(fragmentRegistrereBruker);
    }

    public void saveUserBtn(View view) {
        fragmentRegistrereBruker.saveUserBtn();
    }

    public void loginBtn(View view) {
        fragmentLogin.logInBtn();

    }

    public void fragmentLoginRegBtn(View view) {
        fragmentRegistrereBruker = new FragmentRegistrereBruker();

        openFragment(fragmentRegistrereBruker);
    }

    public void toLoginPageBtn(View view){ //login page button

        fragmentLogin = new FragmentLogin();

        openFragment(fragmentLogin);
    }

    public void openFragment(Fragment fragment) {
        mPager.setVisibility(View.GONE); //Sets the main fragments visibility to gone so that the user cannot se or interact with it

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction(); //Makes a fragment transaction that can be used to change the fragment
        fragmentTransaction.replace(R.id.layout, fragment); //Changes the fragment. R.id.layout is the main layout of the Activity that holds the fragment(MainActivity)
        fragmentTransaction.addToBackStack(""); //Adds the fragment to the fragment stack so it can be poped later
        fragmentTransaction.commit();
    }

    public void showPreferencesBtn(View view){ //settings/preference page button. Creates an intent using the SetPreferenceActivity class and starts an activity.
        Intent intent = new Intent(this, SetPreferenceActivity.class);
        startActivity(intent);
    }

    public void closeFragment() {
        fm.popBackStack();//Goes back to the slide fragments
        if(fm.getBackStackEntryCount() == 1) {
            mPager.setVisibility(View.VISIBLE); //Makes the main fragments visible again
        }
    }
}

