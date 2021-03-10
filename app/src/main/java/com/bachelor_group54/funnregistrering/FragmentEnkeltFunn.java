package com.bachelor_group54.funnregistrering;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentEnkeltFunn extends Fragment {
    private View view;
    private Funn funn;

    public FragmentEnkeltFunn(Funn funn) {
        this.funn = funn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enkelt_funn, container, false); //Loads the page from the XML file
        //Add setup code here later
        loadFunn();
        return view;
    }

    public void loadFunn(){
        ImageSaver imageSaver = new ImageSaver();

        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde);
        imageView.setImageBitmap(imageSaver.loadImage(getContext(), funn.getBilde()));

        TextView title = view.findViewById(R.id.fragment_enkelt_funn_tv_tittel);
        title.setText("Tittel: " + funn.getTittel());

        TextView date = view.findViewById(R.id.fragment_enkelt_funn_tv_dato);
        date.setText("Dato: " + (funn.getDato() == null ? "ikke fylt ut enda" : funn.getDato()));

        TextView location = view.findViewById(R.id.fragment_enkelt_funn_tv_sted);
        location.setText("Sted: " + funn.getFunnsted());

        TextView coordinates = view.findViewById(R.id.fragment_enkelt_funn_tv_koordinater);
        coordinates.setText("Koordinater: " + funn.getLongitude() + " " +funn.getLatitude());

        TextView owner = view.findViewById(R.id.fragment_enkelt_funn_tv_grunneier);
        owner.setText("Grunneier: " + funn.getGrunneierNavn());

        TextView status = view.findViewById(R.id.fragment_enkelt_funn_tv_status);
        status.setText("Status: vi har ikke noe status");

        TextView description = view.findViewById(R.id.fragment_enkelt_funn_tv_beskrivelse);
        description.setText("Beskrivelse: " + funn.getBeskrivelse());

        TextView item = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand);
        item.setText("Gjenstand: " + funn.getGjenstand());

        TextView depth = view.findViewById(R.id.fragment_enkelt_funn_tv_funndybde);
        depth.setText("Funndybde" + funn.getFunndybde());

        TextView itemMarking = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand_merket);
        itemMarking.setText("Gjenstand merket med: " + funn.getGjenstandMerking());

        TextView age = view.findViewById(R.id.fragment_enkelt_funn_tv_datum);
        age.setText("Datum: "+ funn.getDatum());

        TextView areaType = view.findViewById(R.id.fragment_enkelt_funn_tv_arealtype);
        areaType.setText("Arealtype: " + funn.getArealType());

        TextView moreInfo = view.findViewById(R.id.fragment_enkelt_funn_tv_annet);
        moreInfo.setText("Andre opplysninger: " + funn.getOpplysninger());
    }
}
