package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class InputValidater implements TextWatcher {
    private Context context;
    private boolean normalCharsAllowed;
    private boolean numbersAllowed;
    private boolean specialCharsAllowed;
    private int minLength;
    private int maxLength;
    private EditText editText;
    private String editTextString;

    public InputValidater(Context context, boolean normalCharsAllowed, boolean numbersAllowed, boolean specialCharsAllowed, int minLength, int maxLength, EditText editText) {
        this.context = context;
        this.normalCharsAllowed = normalCharsAllowed;
        this.numbersAllowed = numbersAllowed;
        this.specialCharsAllowed = specialCharsAllowed;
        this.minLength = minLength;
        this.maxLength = maxLength;

        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        editTextString = charSequence.toString();

        String feilmelding = "";
        if(editTextString.length() < minLength){
            feilmelding +=  context.getString(R.string.inputvalideringMinLengde) + minLength + context.getString(R.string.tegn);
        }else if(editTextString.length() > maxLength){
            editText.setText(editTextString.substring(0, editTextString.length() - 1));
            editText.setSelection(editTextString.length());
        }else if(!normalCharsAllowed && editTextString.matches(".*[a-zæøåA-ZÆØÅ].*")){
            feilmelding += context.getString(R.string.inputvalideringIkkeBokstaver);
        }else if(!numbersAllowed && editTextString.matches(".*[0-9].*")){
            feilmelding += context.getString(R.string.inputvalideringIkkeTall);
        }else if(!specialCharsAllowed && editTextString.matches(".*[^a-zøæåA-ZÆØÅ0-9 ].*")) {
            feilmelding += context.getString(R.string.inputvalideringIkkeSpesialtegn);
        }

        if(!feilmelding.equals("")){
            editText.setError(feilmelding);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
