package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBruker extends Fragment {
    private View view;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bruker, container, false); //Loads the page from the XML file
        updateEditTexts();
        stopProgressBar();
        return view;
    }
/**
* Sets the edit text / fills out the users information
*/
    public void updateEditTexts(){
        User user = User.getInstance();

        EditText userFirstNameEt = view.findViewById(R.id.fragment_bruker_fornavn);
        userFirstNameEt.setText(user.getName());

        EditText userLastNameEt = view.findViewById(R.id.fragment_bruker_etternavn);
        userLastNameEt.setText(user.getLastName());

        EditText userAddressEt = view.findViewById(R.id.fragment_bruker_adresse);
        userAddressEt.setText(user.getAddress());

        EditText userPostalCodeEt = view.findViewById(R.id.fragment_bruker_postnr);
        userPostalCodeEt.setText(user.getPostalCode());

        EditText userTlfEt = view.findViewById(R.id.fragment_bruker_tlf);
        userTlfEt.setText(user.getPhoneNum());

        EditText userEmailEt = view.findViewById(R.id.fragment_bruker_epost);
        userEmailEt.setText(user.getEmail());
    }

    //Updates the user, form the info filled in the editTexts
    public void updateUser(){
        User user = User.getInstance();

        EditText userFirstNameEt = view.findViewById(R.id.fragment_bruker_fornavn);
        user.setName(userFirstNameEt.getText().toString());

        EditText userLastNameEt = view.findViewById(R.id.fragment_bruker_etternavn);
        user.setLastName(userLastNameEt.getText().toString());

        EditText userAddressEt = view.findViewById(R.id.fragment_bruker_adresse);
        user.setAddress(userAddressEt.getText().toString());

        EditText userPostalCodeEt = view.findViewById(R.id.fragment_bruker_postnr);
        user.setPostalCode(userPostalCodeEt.getText().toString());

        EditText userTlfEt = view.findViewById(R.id.fragment_bruker_tlf);
        user.setPhoneNum(userTlfEt.getText().toString());

        EditText userEmailEt = view.findViewById(R.id.fragment_bruker_epost);
        user.setEmail(userEmailEt.getText().toString());
    }

    public void editUser(){
        startProgressBar();
        updateUser();
        User user = User.getInstance();
        SetJSON setJSON = new SetJSON(FragmentList.getInstance().getContext(), this);
        setJSON.execute("Bruker/EditUser", "Brukernavn=" + user.getUsername(), "Passord=" + user.getPassword(),
                "Fornavn=" + user.getName(), "Etternavn=" + user.getLastName(), "Adresse=" + user.getAddress(),
                "Postnr=" + user.getPostalCode(), "Poststed=" + "poststed", "Tlf=" + user.getPhoneNum(), "Epost=" + user.getEmail());
    }

    public void stopProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_user);
        progressBar.setWillNotDraw(true);
    }

    public void startProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_user);
        progressBar.setWillNotDraw(false);
    }
}
