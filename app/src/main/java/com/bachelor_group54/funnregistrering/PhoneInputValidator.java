package com.bachelor_group54.funnregistrering;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PhoneInputValidator implements TextWatcher {
    private EditText editText;

    public PhoneInputValidator(EditText editText) {
        this.editText = editText;
    }

    @Override
    //This method checks if the phone number inputted by the user is valid otherwise it makes an error
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().matches("\\d{9,10}")){ //If the phone number is longer than 8 digits then it's not a norwegian number and needs country code.
            editText.setError("Du kan ikke ha mer en 8 tall, om du har utenlandsk telefonnummer legg til + og landskode først. Eks +47");
            //Checks is phone number is 8-10 numbers, it can also have +1-3 numbers in the beginning and - or space after that
            //Valid phone numbers, +47 12345678, +47-1234567890, +47123456789, +1 12345678, +999 12345678, 12345678
        } else if(!charSequence.toString().matches("^(\\+\\d{1,3}[\\-\\s]?)?\\d{8,10}")) {
            editText.setError("Dette er ikke et gyldig telefonnummer.");
        }
    }

    //Empty methods that has to be implemented because of the TextWatcher interface.
    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
