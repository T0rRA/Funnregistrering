package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        setInputValidation();
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

    //Method for setting input validation on the fields in the register user view
    public void setInputValidation(){
        EditText nameET = view.findViewById(R.id.fragment_bruker_fornavn); //finds the editText containing the name
        //Sets input validation on the nameET field
        nameET.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1 , 30, nameET));

        EditText lastNameET = view.findViewById(R.id.fragment_bruker_etternavn); //finds the editText containing the last name
        lastNameET.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1 , 30, lastNameET));

        EditText addressET = view.findViewById(R.id.fragment_bruker_adresse); //finds the editText containing the address
        addressET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1 , 50, addressET));

        EditText postalcodeET = view.findViewById(R.id.fragment_bruker_postnr); //finds the editText containing the postal code
        postalcodeET.addTextChangedListener(new InputValidater(getContext(), false, true, false, 4 , 4, postalcodeET));

        EditText phoneNumET = view.findViewById(R.id.fragment_bruker_tlf); //finds the editText containing the phone number
        phoneNumET.addTextChangedListener(new PhoneInputValidator(phoneNumET));

        EditText emailET = view.findViewById(R.id.fragment_bruker_epost); //finds the editText containing the email
        emailET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1 , 50, emailET));
    }
}
