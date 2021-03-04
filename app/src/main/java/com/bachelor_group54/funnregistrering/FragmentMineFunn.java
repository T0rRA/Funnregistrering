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

public class FragmentMineFunn extends Fragment {
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_funn, container, false);
        makeList();
        return view;
    }

    public void makeList(){
        ListView listView = view.findViewById(R.id.list_view_mine_funn);
        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn");
        ArrayList funnListe = objektLagrer.loadData();
        ListAdapter listAdapter = new ListAdapter(getContext(), funnListe);
        listView.setAdapter(listAdapter);
    }
}
