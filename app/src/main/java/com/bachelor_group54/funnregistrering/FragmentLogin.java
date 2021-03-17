package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

    public void logInBtn(){
        //Creates loginInfo object, reads the inputs and adds them to the object
        LoginInfo loginInfo = new LoginInfo();
        EditText userName = view.findViewById(R.id.user_name);
        EditText passord = view.findViewById(R.id.password);
        loginInfo.setUser_name(userName.getText().toString());
        loginInfo.setPassword(passord.getText().toString());

        //Saves the data to a list, TODO: use the info in backend
        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "user"); // initializes the object saving class
        ArrayList<Object> list = objektLagrer.loadData(); // Fills arraylist with previous login info
        list.add(loginInfo); //Adds new the new user object to the list
        objektLagrer.saveData(list); // Saves the list

    }
}