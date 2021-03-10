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
        date.setText("Dato: " + funn.getDato());

        TextView location = view.findViewById(R.id.fragment_enkelt_funn_tv_sted);
        location.setText("Sted: " + funn.getFunnsted());

        TextView coordinates = view.findViewById(R.id.fragment_enkelt_funn_tv_koordinater);
        coordinates.setText(funn.getLongitude() + " " +funn.getLatitude());

        TextView owner = view.findViewById(R.id.fragment_enkelt_funn_tv_grunneier);
        owner.setText("Grunneier: " + funn.getGrunneierNavn());

        TextView status = view.findViewById(R.id.fragment_enkelt_funn_tv_status);
        TextView description = view.findViewById(R.id.fragment_enkelt_funn_tv_beskrivelse);
        TextView item = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand);
        TextView depth = view.findViewById(R.id.fragment_enkelt_funn_tv_funndybde);
        TextView itemMarking = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand_merket);
        TextView age = view.findViewById(R.id.fragment_enkelt_funn_tv_datum);
        TextView areaType = view.findViewById(R.id.fragment_enkelt_funn_tv_arealtype);
        TextView moreInfo = view.findViewById(R.id.fragment_enkelt_funn_tv_annet);
    }
}
