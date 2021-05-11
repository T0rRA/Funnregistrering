package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBruker extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bruker, container, false); //Loads the page from the XML file
        updateEditTexts();
        return view;
    }

    public void updateEditTexts(){
        User user = User.getInstance();

        EditText userFirstNameEt = view.findViewById(R.id.fragment_bruker_fornavn);
        userFirstNameEt.setText(user.getName());

        EditText userLastNameEt = view.findViewById(R.id.fragment_bruker_etternavn);
        userLastNameEt.setText(user.getLastName());

        EditText userAddressEt = view.findViewById(R.id.fragment_bruker_adresse);
        userAddressEt.setText(user.getAddress());

        EditText userTlfEt = view.findViewById(R.id.fragment_bruker_tlf);
        userTlfEt.setText(user.getPhoneNum());

        EditText userEmailEt = view.findViewById(R.id.fragment_bruker_epost);
        userEmailEt.setText(user.getEmail());
    }
}
