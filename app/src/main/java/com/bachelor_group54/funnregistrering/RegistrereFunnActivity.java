package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrereFunnActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrere_lose_funn);
    }

    public void bildeBtn(View view) {
        //TODO legge til bilde funksjon
        Toast.makeText(this, "Har ikke laget bildefunksjon enda", Toast.LENGTH_LONG).show();
    }

    public void gpsBtn(View view) {
        //TODO legge til gps funksjon
        Toast.makeText(this, "Har ikke laget gps funksjon enda", Toast.LENGTH_LONG).show();
    }

    public void registrerFunnBtn(View view) {
        Funn funn = new Funn();

        EditText tittel = findViewById(R.id.nytt_funn_tittel_et);
        funn.setTittel(tittel.getText().toString());

        EditText beskrivelse = findViewById(R.id.nytt_funn_beskrivelse_et);
        funn.setBeskrivelse(beskrivelse.getText().toString());

        ObjektLagrer objektLagrer = new ObjektLagrer(this, "funn");
        ArrayList<Object> arrayList = objektLagrer.loadData();
        arrayList.add(funn);

        objektLagrer.saveData(arrayList);
    }
}


