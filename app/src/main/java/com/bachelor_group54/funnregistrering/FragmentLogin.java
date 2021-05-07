package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentLogin extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false); //Loads the page from the XML file
        EditText usernameEt = view.findViewById(R.id.user_name);
        EditText passwordEt = view.findViewById(R.id.password);
        passwordEt.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1 , 100, passwordEt));
        usernameEt.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1 , 30, usernameEt));
        return view;
    }

    public void logInBtn() {
        //Gets username and password for the input fields, if login is successful edit the user object
        EditText usernameEt = view.findViewById(R.id.user_name);
        EditText passwordEt = view.findViewById(R.id.password);
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        //Saves the password to the user object
        User user = User.getInstance();
        user.setPassword(password);

        logIn(username, password);
    }

    public void logIn(String username, String password){
        //Attempts to log in
        SetJSON setJSON = new SetJSON(FragmentList.getInstance().getContext(), username);
        setJSON.execute("Bruker/LogIn", "brukernavn=" + username, "passord=" + password);
    }

    public void forgottenPassword(){
        EditText usernameEt = view.findViewById(R.id.user_name);
        String username = usernameEt.getText().toString();

        if(username.equals("")){
            Toast.makeText(getContext(), "Skriv inn brukernavn først", Toast.LENGTH_LONG).show();
            return;
        }

        SetJSON setJSON = new SetJSON(getContext());
        setJSON.execute("Bruker/SendPwResetLink", "brukernavn=" + username);
    }
}
