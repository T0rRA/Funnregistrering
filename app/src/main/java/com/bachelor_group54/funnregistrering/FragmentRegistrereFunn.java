package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRegistrereFunn extends Fragment {
    private View view; //View'et til siden trengs om man vil kalle på underelementer i view'et (eks hente tekst fra en editText)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registrere_lose_funn, container, false); //Laster inn skjermutsenet fra XML filen
        //Legg til settup kode her
        return view;
    }

    public void bildeBtn(Context context) {
        //TODO legge til bilde funksjon
        Toast.makeText(context, "Har ikke laget bildefunksjon enda", Toast.LENGTH_LONG).show();
    }

    public void gpsBtn(Context context) {
        //TODO legge til gps funksjon
        Toast.makeText(context, "Har ikke laget gps funksjon enda", Toast.LENGTH_LONG).show();
    }

    public void registrerFunnBtn(Context context) {
        Funn funn = new Funn();

        EditText tittel = view.findViewById(R.id.nytt_funn_tittel_et); //Finner editText som holder tittelen
        funn.setTittel(tittel.getText().toString()); //Legger tittelen fra editText'en til i funnet

        EditText beskrivelse = view.findViewById(R.id.nytt_funn_beskrivelse_et); //Finner beskrivelse editText'en
        funn.setBeskrivelse(beskrivelse.getText().toString());//Legger beskrivelsen til i funnet

        ObjektLagrer objektLagrer = new ObjektLagrer(context, "funn"); //Initialiserer objektLagret med context og filNavnet
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Henter arrayet som alerede er lagret på filNavnet
        arrayList.add(funn); //Legger det nye funnet til i listen

        objektLagrer.saveData(arrayList); //Lagrer den nye listen og overskriver den gamle listen
    }
}


