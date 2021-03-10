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

public class FragmentRegistrereBruker extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registrer_bruker, container, false); //Loads the page from the XML file
        return view;
    }
    public void saveUserBtn() {

        User user = new User(); //new user object

        EditText name = view.findViewById(R.id.name_new); //finds the editText containing the name
        user.setName(name.getText().toString()); //adds the content (the name) to the user object

        EditText lastName = view.findViewById(R.id.last_name_new); //finds the editText containing the last name
        user.setLast_name(lastName.getText().toString());

        EditText address = view.findViewById(R.id.address_new); //finds the editText containing the address
        user.setAddress(address.getText().toString()); //adds the content (the address) to the user object

        EditText postalcode = view.findViewById(R.id.postal_code_new); //finds the editText containing the postal code
        user.setPostal_code(postalcode.getText().toString()); //adds the content (the postal code) to the user object

        EditText phone_num = view.findViewById(R.id.phone_num_new); //finds the editText containing the phone number
        user.setPhone_num(phone_num.getText().toString()); //adds the content (the phone number) to the user object

        EditText email = view.findViewById(R.id.email_new); //finds the editText containing the email
        user.setEmail(email.getText().toString()); //adds the content (the email) to the user object

        EditText password = view.findViewById(R.id.password_new); //finde the editText containing the password
        user.setPassword(email.getText().toString()); // adds the content (the password) to the user object


        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "user"); // initializes the object saving class
        ArrayList<Object> alist = objektLagrer.loadData(); // Fills arraylist with previous user info
        alist.add(user); //Adds new the new user object to the list

        objektLagrer.saveData(alist); // Saves the list

    }
}
