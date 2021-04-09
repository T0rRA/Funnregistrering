package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Set;

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
        String confirmationPw;

        User user = new User(); //new user object

        EditText nameET = view.findViewById(R.id.name_new); //finds the editText containing the name
        user.setName(nameET.getText().toString()); //adds the content (the name) to the user object

        EditText lastNameET = view.findViewById(R.id.last_name_new); //finds the editText containing the last name
        user.setLastName(lastNameET.getText().toString());

        EditText addressET = view.findViewById(R.id.address_new); //finds the editText containing the address
        user.setAddress(addressET.getText().toString()); //adds the content (the address) to the user object

        EditText postalcodeET = view.findViewById(R.id.postal_code_new); //finds the editText containing the postal code
        user.setPostalCode(postalcodeET.getText().toString()); //adds the content (the postal code) to the user object

        EditText phoneNumET = view.findViewById(R.id.phone_num_new); //finds the editText containing the phone number
        user.setPhoneNum(phoneNumET.getText().toString()); //adds the content (the phone number) to the user object

        EditText emailET = view.findViewById(R.id.email_new); //finds the editText containing the email
        user.setEmail(emailET.getText().toString()); //adds the content (the email) to the user object

        EditText passwordET = view.findViewById(R.id.password_new); //finds the editText containing the password
        user.setPassword(passwordET.getText().toString()); // adds the content (the password) to the user object

        EditText confirmationPwET = view.findViewById(R.id.confirmation_pw); //finde the editText containing the password
        confirmationPw = (confirmationPwET.getText().toString()); // adds the content (the password) to the user object

        if (user.getPassword().equals(confirmationPw)) { //checks if the password and the rewritten password are the same
            ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "user"); // initializes the object saving class
            ArrayList<Object> alist = objektLagrer.loadData(); // Fills arraylist with previous user info
            alist.add(user); //Adds new the new user object to the list
            objektLagrer.saveData(alist); // Saves the list

            //Registers the user on the server
            SetJSON setJSON = new SetJSON();
            setJSON.execute("Bruker/CreateUser", "Brukernavn=" + user.getName(), "Passord=" +
                    user.getPassword(), "Fornavn=" + user.getName(), "Etternavn=" + user.getLastName(),
                    "Adresse=" + user.getAddress(), "Postnr=" + user.getPostalCode(), "Poststed=" /*FIXME har ikke poststed*/,
                    "Tlf=" + user.getPhoneNum(), "Epost=" + user.getEmail());

        } else { //sends message if the passwords are different
            Toast.makeText(getContext(), "Passordene matcher ikke!", Toast.LENGTH_LONG).show();
        }

    }
}
