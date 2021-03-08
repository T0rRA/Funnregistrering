package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//Class for the my finds fragment
public class FragmentMineFunn extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_funn, container, false); //Inflates the view
        makeList();
        return view; //Returns the this fragment's view
    }

    //This method creates and sets a list containing all the finds.
    public void makeList(){
        ListView listView = view.findViewById(R.id.list_view_mine_funn); //Finds the listView
        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn");
        ArrayList funnListe = objektLagrer.loadData(); //Gets the ArrayList containing all the finds
        ListAdapter listAdapter = new ListAdapter(getContext(), funnListe); //Creates the listAdapter, the listAdapter is used to handle the elements within the listView
        listView.setAdapter(listAdapter); //Sets the ListAdapter
    }
}
