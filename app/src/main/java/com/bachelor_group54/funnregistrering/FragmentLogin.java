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
        return view;
    }

    public void logInBtn() {
        //Creates loginInfo object, reads the inputs and adds them to the object
        LoginInfo loginInfo = new LoginInfo();
        EditText userName = view.findViewById(R.id.user_name);
        EditText password = view.findViewById(R.id.password);
        loginInfo.setUserName(userName.getText().toString());
        loginInfo.setPassword(password.getText().toString());

        //Saves the data to a list, TODO: use the info in backend
        /*ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "user"); // initializes the object saving class
        ArrayList<Object> list = objektLagrer.loadData(); // Fills arraylist with previous login info
        list.add(loginInfo); //Adds new the new user object to the list
        objektLagrer.saveData(list); // Saves the list*/

        SetJSON setJSON = new SetJSON(getContext(), loginInfo.getUserName());
        setJSON.execute("Bruker/LogIn", "brukernavn=" + loginInfo.getUserName(), "passord=" + loginInfo.getPassword());

        //FIXME kanskje logIn burde returnere User object?
        GetJSON getJSON = new GetJSON(this);
        getJSON.execute("Bruker/GetUser?brukernavn=" + loginInfo.getUserName());
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
