package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//Class for the my finds fragment
public class FragmentMineFunn extends Fragment {
    private View view;
    private int listSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_funn, container, false); //Inflates the view
        makeList();
        LinearLayout navbarMineFunn = view.findViewById(R.id.navbar_mine_funn); //Gets the navbar layout for this view
        navbarMineFunn.setBackground(getContext().getDrawable(R.drawable.navbar_btn_selected_background)); //Setts color on the navbar indicating what page you are on
        return view; //Returns the this fragment's view
    }

    @Override
    //Updates the page
    public void onResume() {
        makeList();
        super.onResume();
    }

    //This method creates and sets a list containing all the finds.
    public void makeList(){
        ListView listView = view.findViewById(R.id.list_view_mine_funn); //Finds the listView
        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn");
        ArrayList funnListe = objektLagrer.loadData(); //Gets the ArrayList containing all the finds
        ListAdapter listAdapter = new ListAdapter(getContext(), funnListe, (MainActivity)getActivity()); //Creates the listAdapter, the listAdapter is used to handle the elements within the listView
        listView.setAdapter(listAdapter); //Sets the ListAdapter
        listSize = funnListe.size();
    }

    public int getListSize() {
        return listSize == 0 ? 1 : listSize;
    }
}
