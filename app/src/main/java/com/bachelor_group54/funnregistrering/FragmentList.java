package com.bachelor_group54.funnregistrering;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

//This class holds fragments that wil be needed many places in the program
public class FragmentList {
    private static FragmentMineFunn fragmentMineFunn;
    private MainActivity mainActivity;

    //Singleton begins
    public static final FragmentList fragmentList = new FragmentList();

    public static FragmentList getInstance() {
        return fragmentList;
    }

    private FragmentList(){}

    //Singleton ends


    public static FragmentMineFunn getFragmentMineFunn() {
        return fragmentMineFunn;
    }

    public void setFragmentMineFunn(FragmentMineFunn fragmentMineFunn) {
        this.fragmentMineFunn = fragmentMineFunn;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
