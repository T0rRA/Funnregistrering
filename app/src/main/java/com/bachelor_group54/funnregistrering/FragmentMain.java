package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMain extends Fragment {
    private View view; //View'et til siden trengs om man vil kalle på underelementer i view'et (eks hente tekst fra en editText)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false); //Laster inn skjermutsenet fra XML filen
        //Legg til settup kode her
        LinearLayout navbarSettings = view.findViewById(R.id.navbar_innstillinger); //Gets the navbar layout for this view
        navbarSettings.setBackground(getContext().getDrawable(R.drawable.navbar_btn_selected_background)); //Setts color on the navbar indicating what page you are on

        return view;
    }
}
