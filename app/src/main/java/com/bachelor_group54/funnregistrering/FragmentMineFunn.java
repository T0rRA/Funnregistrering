package com.bachelor_group54.funnregistrering;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//Class for the my finds fragment
public class FragmentMineFunn extends Fragment {
    private View view;
    private int listSize;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_funn, container, false); //Inflates the view
        getFinds();
        LinearLayout navbarMineFunn = view.findViewById(R.id.navbar_mine_funn); //Gets the navbar layout for this view
        navbarMineFunn.setBackground(getContext().getDrawable(R.drawable.navbar_btn_selected_background)); //Setts color on the navbar indicating what page you are on
        return view; //Returns the this fragment's view
    }

    @Override
    //Updates the page
    public void onResume() {
        //getFinds();
        super.onResume();
    }

    //This method creates and sets a list containing all the finds.
    public void makeList(ArrayList<Funn> findsList){
        ListView listView = view.findViewById(R.id.list_view_mine_funn); //Finds the listView
        ListAdapter listAdapter = new ListAdapter(getContext(), findsList, (MainActivity)getActivity()); //Creates the listAdapter, the listAdapter is used to handle the elements within the listView
        listView.setAdapter(listAdapter); //Sets the ListAdapter
        listSize = findsList.size();
    }

    public void getFinds(){
        startProgressBar();
        GetJSON getJSON = new GetJSON(this);
        getJSON.execute("Funn/GetAllUserFunn?brukernavn=helge2&passord=helge123"/*FIXME endre til å bruke riktig brukernavn og passord eller token?*/, "funnID", "image", "funndato", "kommune", "fylke", "funndybde", "gjenstand_markert_med", "koordinat", "datum", "areal_type");
    }

    public void stopProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_mine_funn);
        progressBar.setWillNotDraw(true);
    }

    public void startProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_mine_funn);
        progressBar.setWillNotDraw(false);
    }

    public int getListSize() {
        return listSize;
    }
}
