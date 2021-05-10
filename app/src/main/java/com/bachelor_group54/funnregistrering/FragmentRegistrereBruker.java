package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        setInputValidation();
        return view;
    }

    //Method for setting input validation on the fields in the register user view
    public void setInputValidation(){
        EditText nameET = view.findViewById(R.id.name_new); //finds the editText containing the name
        //Sets input validation on the nameET field
        nameET.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1 , 30, nameET));

        EditText lastNameET = view.findViewById(R.id.last_name_new); //finds the editText containing the last name
        lastNameET.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1 , 30, lastNameET));

        EditText addressET = view.findViewById(R.id.address_new); //finds the editText containing the address
        addressET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1 , 50, addressET));

        EditText postalcodeET = view.findViewById(R.id.postal_code_new); //finds the editText containing the postal code
        postalcodeET.addTextChangedListener(new InputValidater(getContext(), false, true, false, 4 , 4, postalcodeET));

        EditText phoneNumET = view.findViewById(R.id.phone_num_new); //finds the editText containing the phone number
        phoneNumET.addTextChangedListener(new PhoneInputValidator(phoneNumET));

        EditText emailET = view.findViewById(R.id.email_new); //finds the editText containing the email
        emailET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1 , 50, emailET));

        final EditText passwordET = view.findViewById(R.id.password_new); //finds the editText containing the password
        passwordET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 8 , 100, passwordET));

        final EditText confirmationPwET = view.findViewById(R.id.confirmation_pw); //finds the editText containing the password
        confirmationPwET.addTextChangedListener(new InputValidater(getContext(), true, true, true, 8 , 100, confirmationPwET));
        confirmationPwET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(passwordET.getText().toString())){
                    confirmationPwET.setError(getString(R.string.ulike_passord));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void saveUserBtn() {
        String confirmationPw;

        EditText nameET = view.findViewById(R.id.name_new); //finds the editText containing the name
        String name = nameET.getText().toString(); //adds the content (the name) to the user object

        EditText lastNameET = view.findViewById(R.id.last_name_new); //finds the editText containing the last name
        String lastName = lastNameET.getText().toString();

        EditText addressET = view.findViewById(R.id.address_new); //finds the editText containing the address
        String address = addressET.getText().toString(); //adds the content (the address) to the user object

        EditText postalcodeET = view.findViewById(R.id.postal_code_new); //finds the editText containing the postal code
        String postalcode = postalcodeET.getText().toString(); //adds the content (the postal code) to the user object

        EditText phoneNumET = view.findViewById(R.id.phone_num_new); //finds the editText containing the phone number
        String phoneNumber = phoneNumET.getText().toString(); //adds the content (the phone number) to the user object

        EditText emailET = view.findViewById(R.id.email_new); //finds the editText containing the email
        String email = emailET.getText().toString(); //adds the content (the email) to the user object

        EditText passwordET = view.findViewById(R.id.password_new); //finds the editText containing the password
        String password = passwordET.getText().toString(); // adds the content (the password) to the user object

        EditText confirmationPwET = view.findViewById(R.id.confirmation_pw); //finds the editText containing the password
        confirmationPw = (confirmationPwET.getText().toString()); // adds the content (the password) to the user object

        if (password.equals(confirmationPw)) { //checks if the password and the rewritten password are the same
            /*ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "user"); // initializes the object saving class
            ArrayList<Object> alist = objektLagrer.loadData(); // Fills arraylist with previous user info
            alist.add(user); //Adds new the new user object to the list
            objektLagrer.saveData(alist); // Saves the list*/

            //Registers the user on the server
            SetJSON setJSON = new SetJSON(getContext(), this);
            setJSON.execute("Bruker/CreateUser", "Brukernavn=" + name, "Passord=" +
                    password, "Fornavn=" + name, "Etternavn=" + lastName,
                    "Adresse=" + address, "Postnr=" + postalcode, "Poststed=" /*FIXME har ikke poststed*/,
                    "Tlf=" + phoneNumber, "Epost=" + email);

        } else { //sends message if the passwords are different
            Toast.makeText(getContext(), getString(R.string.ulike_passord), Toast.LENGTH_LONG).show();
        }

    }
}
