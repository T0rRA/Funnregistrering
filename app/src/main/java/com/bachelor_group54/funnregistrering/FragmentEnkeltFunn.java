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

//This fragment displays one selected find at the time. The find can also be edited here.
public class FragmentEnkeltFunn extends Fragment {
    private View view;
    private Funn funn;

    //Simple constructor for getting the find that the fragment should display
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
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(ImageSaver.loadImage(getContext(), funn.getBilde())); //Sets the image view to the finds image

        //FIXME hva om funndybden er 0 eller at man befinner seg på koordinatene 0.0, 0.0

        TextView coordinates = view.findViewById(R.id.fragment_enkelt_funn_tv_koordinater); //Finds the coordinates textView
        String coords = "Koordinater: " + funn.getLongitude() + " " + funn.getLatitude();
        if(funn.getLatitude() == 0.0 && funn.getLongitude() == 0.0){ //If both latitude and longitude == 0.0 then the coordinates are probably not set
            coords = "Koordinater: ikke fylt ut enda";
        }
        coordinates.setText(coords);

        TextView depth = view.findViewById(R.id.fragment_enkelt_funn_tv_funndybde);
        depth.setText("Funndybde: " + (funn.getFunndybde() == 0 ? "ikke fylt ut enda" : funn.getFunndybde())); //If funndybde == 0 then its probably not set

        TextView title = view.findViewById(R.id.fragment_enkelt_funn_tv_tittel); //Finds the textView of the title
        title.setText("Tittel: " + checkString(funn.getTittel())); //Checks and sets the title

        //The same for all the other textViews, finding, checking and setting the text.
        TextView date = view.findViewById(R.id.fragment_enkelt_funn_tv_dato);
        date.setText("Dato: " + checkString(funn.getDato()));

        TextView location = view.findViewById(R.id.fragment_enkelt_funn_tv_sted);
        location.setText("Sted: " + checkString(funn.getFunnsted()));

        TextView owner = view.findViewById(R.id.fragment_enkelt_funn_tv_grunneier);
        owner.setText("Grunneier: " + checkString(funn.getGrunneierNavn()));

        TextView status = view.findViewById(R.id.fragment_enkelt_funn_tv_status);
        status.setText("Status: vi har ikke noe status");

        TextView description = view.findViewById(R.id.fragment_enkelt_funn_tv_beskrivelse);
        description.setText("Beskrivelse: " + checkString(funn.getBeskrivelse()));

        TextView item = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand);
        item.setText("Gjenstand: " + checkString(funn.getGjenstand()));

        TextView itemMarking = view.findViewById(R.id.fragment_enkelt_funn_tv_gjenstand_merket);
        itemMarking.setText("Gjenstand merket med: " + checkString(funn.getGjenstandMerking()));

        TextView age = view.findViewById(R.id.fragment_enkelt_funn_tv_datum);
        age.setText("Datum: "+ checkString(funn.getDatum()));

        TextView areaType = view.findViewById(R.id.fragment_enkelt_funn_tv_arealtype);
        areaType.setText("Arealtype: " + checkString(funn.getArealType()));

        TextView moreInfo = view.findViewById(R.id.fragment_enkelt_funn_tv_annet);
        moreInfo.setText("Andre opplysninger: " + checkString(funn.getOpplysninger()));
    }

    //Checks if strings are filled put or not
    public String checkString(String string){
        if(string == null || string.equals("")){ //If null or empty string return not filled message
            return "ikke fylt ut";
        }
        return string; //Returns the input string by default
    }
}
