package com.bachelor_group54.funnregistrering;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        String tomtFelt = "ikke fylt ut";
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(ImageSaver.loadImage(getContext(), funn.getBilde())); //Sets the image view to the finds image

        //FIXME hva om funndybden er 0 eller at man befinner seg på koordinatene 0.0, 0.0

        EditText coordinates = view.findViewById(R.id.fragment_enkelt_funn_et_koordinater); //Finds the coordinates textView
        String coords = "" + funn.getLongitude() + " " + funn.getLatitude();
        if(funn.getLatitude() == 0.0 && funn.getLongitude() == 0.0){ //If both latitude and longitude == 0.0 then the coordinates are probably not set
            coords = tomtFelt;
            coordinates.setHint(coords); //Hint is preview text, makes it easier to edit.
        }else {
            coordinates.setText(coords);
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde);
        if(funn.getFunndybde() == 0){//If funndybde == 0 then its probably not set
            depth.setHint(tomtFelt);
        }else{
            depth.setText("" + funn.getFunndybde());
        }

        EditText title = view.findViewById(R.id.fragment_enkelt_funn_et_tittel); //Finds the textView of the title
        setText(funn.getTittel(), title); //Checks and sets the title

        //The same for all the other textViews, finding, checking and setting the text.
        EditText date = view.findViewById(R.id.fragment_enkelt_funn_et_dato);
        setText(funn.getDato(), date);

        EditText location = view.findViewById(R.id.fragment_enkelt_funn_et_sted);
        setText(funn.getFunnsted(), location);

        EditText owner = view.findViewById(R.id.fragment_enkelt_funn_et_grunneier);
        setText(funn.getGrunneierNavn(), owner);

        //TODO legge til status
        TextView status = view.findViewById(R.id.fragment_enkelt_funn_tv_status);
        status.setText("Status: vi har ikke noe status");

        EditText description = view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse);
        setText(funn.getBeskrivelse(), description);

        EditText item = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand);
        setText(funn.getGjenstand(), item);

        EditText itemMarking = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke);
        setText(funn.getGjenstandMerking(), itemMarking);

        EditText age = view.findViewById(R.id.fragment_enkelt_funn_et_datum);
        setText(funn.getDatum(), age);

        EditText areaType = view.findViewById(R.id.fragment_enkelt_funn_et_arealtype);
        setText(funn.getArealType(), areaType);

        EditText moreInfo = view.findViewById(R.id.fragment_enkelt_funn_et_annet);
        setText(funn.getOpplysninger(), moreInfo);

        EditText postnr = view.findViewById(R.id.fragment_enkelt_funn_et_postnr);
        setText(funn.getPostnr(), postnr);

        EditText find_location = view.findViewById(R.id.fragment_enkelt_funn_et_funnsted);
        setText(funn.getFunnsted(), find_location);

        EditText farm = view.findViewById(R.id.fragment_enkelt_funn_et_gaard);
        setText(funn.getGaard(), farm);

        EditText farmnumber = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        setText(funn.getGbnr(), farmnumber);

        EditText kommune = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        setText(funn.getKommune(), kommune);

        EditText fylke = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        setText(funn.getFylke(), fylke);
    }

    //Checks if strings are filled put or not
    public String checkString(String string){
        if(string == null || string.equals("")){ //If null or empty string return not filled message
            return "ikke fylt ut";
        }
        return string; //Returns the input string by default
    }

    public void setText(String text, EditText editText){
        text = checkString(text);
        if (!text.equals("ikke fylt ut")) {
            editText.setText(text);
        }
    }
}
