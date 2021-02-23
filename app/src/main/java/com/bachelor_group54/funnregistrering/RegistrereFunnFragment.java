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

public class RegistrereFunnFragment extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_registrere_lose_funn, container, false);
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

        EditText tittel = view.findViewById(R.id.nytt_funn_tittel_et);
        funn.setTittel(tittel.getText().toString());

        EditText beskrivelse = view.findViewById(R.id.nytt_funn_beskrivelse_et);
        funn.setBeskrivelse(beskrivelse.getText().toString());

        ObjektLagrer objektLagrer = new ObjektLagrer(context, "funn");
        ArrayList<Object> arrayList = objektLagrer.loadData();
        arrayList.add(funn);

        objektLagrer.saveData(arrayList);
    }
}


