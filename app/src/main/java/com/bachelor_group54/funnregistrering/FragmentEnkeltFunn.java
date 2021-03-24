package com.bachelor_group54.funnregistrering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//This fragment displays one selected find at the time. The find can also be edited here.
public class FragmentEnkeltFunn extends Fragment {
    private View view;
    private Funn funn; //The find the view is displaying
    private int position; //The finds position in the saved list
    private Bitmap picture;

    //Simple constructor for getting the find that the fragment should display
    public FragmentEnkeltFunn(Funn funn, int position) {
        this.funn = funn;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enkelt_funn, container, false); //Loads the page from the XML file
        //Add setup code here later
        loadFunn();
        updateStatusBtn();
        setTextWatchers();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatusBtn();
    }

    //Makes the status buttons update when editTexts are changed
    public void setTextWatchers(){
        EditText[] editTexts = {view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad)
                , view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad)
                , view.findViewById(R.id.fragment_enkelt_funn_et_funndybde)
                , view.findViewById(R.id.fragment_enkelt_funn_et_tittel)
                , view.findViewById(R.id.fragment_enkelt_funn_et_dato)
                , view.findViewById(R.id.fragment_enkelt_funn_et_sted)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneier)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted)
                , view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf)
                , view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke)
                , view.findViewById(R.id.fragment_enkelt_funn_et_datum)
                , view.findViewById(R.id.fragment_enkelt_funn_et_arealtype)
                , view.findViewById(R.id.fragment_enkelt_funn_et_annet)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gbnr)
                , view.findViewById(R.id.fragment_enkelt_funn_et_kommune)
                , view.findViewById(R.id.fragment_enkelt_funn_et_fylke)};

        for (int i = 0; i < editTexts.length;i++){
            EditText et = editTexts[i];
            if(i > 2){ //The first 3 elements are number fields
                //Sets input validator to only allow alphabet chars with length from 0 - 100
                et.addTextChangedListener(new InputValidater(getContext(), true, false, false, 0, 100, et));
            } //TODO legge til inputvalidering av nummer feltene
            et.addTextChangedListener(new StatusUpdater()); //Setts the textWatcher on the editText
        }
    }

    public void loadFunn() {
        String tomtFelt = "ikke fylt ut";
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(ImageSaver.loadImage(getContext(), funn.getBildeID())); //Sets the image view to the finds image

        //TODO finne ut hvilke felter brukeren skal kunne endre selv

        EditText latitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude textView
        //Latitude cannot be more than 90 or less than -90
        if (funn.getLatitude() >= -90 && funn.getLatitude() <= 90) {
            latitudeEt.setText("" + funn.getLatitude());
        }

        EditText longitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude textView
        //Longitude cannot be more than 180 or less than -180
        if (funn.getLongitude() >= -180 && funn.getLongitude() <= 180) {
            longitudeEt.setText("" + funn.getLongitude());
        }

        EditText depthEt = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde);
        if (funn.getFunndybde() == -1) {//-1 is the default value
            depthEt.setHint(tomtFelt);
        } else {
            depthEt.setText("" + funn.getFunndybde());
        }

        EditText titleEt = view.findViewById(R.id.fragment_enkelt_funn_et_tittel); //Finds the textView of the title
        setText(funn.getTittel(), titleEt); //Checks and sets the title

        //The same for all the other textViews, finding, checking and setting the text.
        EditText dateEt = view.findViewById(R.id.fragment_enkelt_funn_et_dato);
        setText(funn.getDato(), dateEt);

        EditText locationEt = view.findViewById(R.id.fragment_enkelt_funn_et_sted);
        setText(funn.getFunnsted(), locationEt);

        EditText ownerEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneier);
        setText(funn.getGrunneierNavn(), ownerEt);

        EditText ownerAddressEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse);
        setText(funn.getGrunneierAdresse(), ownerAddressEt);

        EditText ownerPostalCodeEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr);
        setText(funn.getGrunneierPostNr(), ownerPostalCodeEt);

        EditText ownerPostalPlaceEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted);
        setText(funn.getGrunneierPostSted(), ownerPostalPlaceEt);

        EditText ownerTlfEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf);
        setText(funn.getGrunneierTlf(), ownerTlfEt);

        EditText ownerEmailEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost);
        setText(funn.getGrunneierEpost(), ownerEmailEt);

        EditText description = view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse);
        setText(funn.getBeskrivelse(), description);

        EditText itemEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand);
        setText(funn.getGjenstand(), itemEt);

        EditText itemMarkingEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke);
        setText(funn.getGjenstandMerking(), itemMarkingEt);

        EditText ageEt = view.findViewById(R.id.fragment_enkelt_funn_et_datum);
        setText(funn.getDatum(), ageEt);

        EditText areaTypeEt = view.findViewById(R.id.fragment_enkelt_funn_et_arealtype);
        setText(funn.getArealType(), areaTypeEt);

        EditText moreInfoEt = view.findViewById(R.id.fragment_enkelt_funn_et_annet);
        setText(funn.getOpplysninger(), moreInfoEt);

        EditText farmNrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr);
        setText(funn.getGårdNr(), farmNrEt);

        EditText gbnrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        setText(funn.getGbnr(), gbnrEt);

        EditText municipalityEt = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        setText(funn.getKommune(), municipalityEt);

        EditText countyEt = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        setText(funn.getFylke(), countyEt);
    }

    //Checks if strings are filled put or not
    public String checkString(String string) {
        if (string == null || string.equals("")) { //If null or empty string return not filled message
            return "ikke fylt ut";
        }
        return string; //Returns the input string by default
    }

    public void setText(String text, EditText editText) {
        text = checkString(text);
        if (!text.equals("ikke fylt ut")) {
            editText.setText(text);
        }
    }

    public void saveFind() {
        //Updates the find with the changed information
        updateFind();

        //If the a picture has been added save it
        if (picture != null) {
            savePicture();
        }

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialises the class that saves the finds
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Gets the already saved ArrayList with all the previous finds
        arrayList.set(position, funn); //Overwrites the previous finds

        objektLagrer.saveData(arrayList); //Saves the new list, overwriting the old list
    }

    //This method is used for updating the find before saving it
    public void updateFind() {
        //FIXME legge til sjekk for om latitude er over 90 eller under -90
        EditText latitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude editText
        if (!latitudeEt.getText().toString().equals("")) {
            try {
                funn.setLatitude(Double.parseDouble(inputChecker(latitudeEt)));//Updates the latitude in the find
            }catch (NumberFormatException e){/*Do noting*/}
        }

        //FIXME legge til sjekk for om longitude er over 180 eller under -180
        EditText longitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude editText
        if (!longitudeEt.getText().toString().equals("")) {
            try {
            funn.setLongitude(Double.parseDouble(inputChecker(longitudeEt))); //Updates the longitude in the find
            }catch (NumberFormatException e){/*Do noting*/}
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde); //Finds the text field
        if (!depth.getText().toString().equals("")) {
            funn.setFunndybde(Double.parseDouble(depth.getText().toString())); //Changes the info inn the find
        }

        //Just the same all the way, find the text fields and updates the find
        EditText titleEt = view.findViewById(R.id.fragment_enkelt_funn_et_tittel);
        funn.setTittel(inputChecker(titleEt));

        EditText dateEt = view.findViewById(R.id.fragment_enkelt_funn_et_dato);
        funn.setDato(inputChecker(dateEt));

        EditText locationEt = view.findViewById(R.id.fragment_enkelt_funn_et_sted);
        funn.setFunnsted(inputChecker(locationEt));

        EditText ownerEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneier);
        funn.setGrunneierNavn(inputChecker(ownerEt));

        EditText ownerAddressEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse);
        funn.setGrunneierAdresse(inputChecker(ownerAddressEt));

        EditText ownerPostalCodeEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr);
        funn.setGrunneierPostNr(inputChecker(ownerPostalCodeEt));

        EditText ownerPostalPlaceEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted);
        funn.setGrunneierPostSted(inputChecker(ownerPostalPlaceEt));

        EditText ownerTlfEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf);
        funn.setGrunneierTlf(inputChecker(ownerTlfEt));

        EditText ownerEmailEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost);
        funn.setGrunneierEpost(inputChecker(ownerEmailEt));

        EditText descriptionEt = view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse);
        funn.setBeskrivelse(inputChecker(descriptionEt));

        EditText itemEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand);
        funn.setGjenstand(inputChecker(itemEt));

        EditText itemMarkingEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke);
        funn.setGjenstandMerking(inputChecker(itemMarkingEt));

        EditText ageEt = view.findViewById(R.id.fragment_enkelt_funn_et_datum);
        funn.setDatum(inputChecker(ageEt));

        EditText areaTypeEt = view.findViewById(R.id.fragment_enkelt_funn_et_arealtype);
        funn.setArealType(inputChecker(areaTypeEt));

        EditText moreInfoEt = view.findViewById(R.id.fragment_enkelt_funn_et_annet);
        funn.setOpplysninger(inputChecker(moreInfoEt));

        EditText farmNrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr);
        funn.setGårdNr(inputChecker(farmNrEt));

        EditText gbnrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        funn.setGbnr(inputChecker(gbnrEt));

        EditText municipalityEt = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        funn.setKommune(inputChecker(municipalityEt));

        EditText countyEt = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        funn.setFylke(inputChecker(countyEt));
    }


    public void savePicture() {
        //Gets the image ID
        int pictureID = funn.getBildeID();

        //If no picture has been set get a new picture ID
        if (pictureID == 0) {

            pictureID = getNextPictureID();
            SharedPreferences sharedpreferences = getContext().getSharedPreferences("pictures", getContext().MODE_PRIVATE);
            //Updates the picture ID in shared preferences
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("pictureID", pictureID);
            editor.apply();

            funn.setBildeID(pictureID);
        }

        //Saves the image
        ImageSaver.saveImage(picture, getContext(), pictureID);

    }

    public int getNextPictureID(){
        //Gets the next available pictureID
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("pictures", getContext().MODE_PRIVATE);
        return sharedpreferences.getInt("pictureID", 0) + 1;
    }

    int CAMERA_PIC_REQUEST = 1337; //Setting the request code for the camera intent, this is used to identify the result when it is returned after taking the picture in onActivityResult.

    //This method opens the camera app when clicking the "Take image" button
    public void bildeBtn() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Makes an intent of the image capture type
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST); //Starts the camera app and waits for the result
    }

    @Override
    //This method receives the image from the camera app and setts the ImageView to that image.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) { //If the requestCode matches that of the startActivityForResult of the cameraIntent we know it is the camera app that is returning it's data.
            try { //May produce null pointers if the picture is not taken
                picture = (Bitmap) data.getExtras().get("data"); //Gets the picture from the camera app and saves it as a Bitmap
                if(funn.getBildeID() == 0){
                    funn.setBildeID(getNextPictureID());
                }
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Picture not taken", Toast.LENGTH_LONG).show(); //Prints a message to the user, explaining that no picture was taken
                return; //Return if there is no picture
            }

            ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the ImageView
            imageView.setImageBitmap(picture); //Sets the ImageView to the picture taken from the camera app
        }

        super.onActivityResult(requestCode, resultCode, data); //Calls the super's onActivityResult (Required by Android)
    }

    //Fixme denne metoden må kjøres når felter endres
    //This method color codes the status buttons (red = missing info, yellow = ready to send, green = sent)
    public void updateStatusBtn() {
        Button findMessageBtn = view.findViewById(R.id.fragment_enkelt_funn_funnmelding_btn);
        if (!funn.isFunnmeldingSendt()) { //Checks if the find message is sent or not
            if (funn.isFunnmeldingKlar()) {
                findMessageBtn.setBackgroundColor(getResources().getColor(R.color.colorYellow)); //If the right info is entered the the button is yellow
            } else {
                findMessageBtn.setBackgroundColor(getResources().getColor(R.color.colorRed)); //If the right info is not entered then the buttons is red
            }
        } else {
            findMessageBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen)); //If the message is sent then the button is green
        }

        Button findFormBtn = view.findViewById(R.id.fragment_enkelt_funnskjema_btn);
        if (!funn.isFunnskjemaSendt()) {
            if (funn.isFunnskjemaKlart()) {
                findFormBtn.setBackgroundColor(getResources().getColor(R.color.colorYellow)); //If the right info is entered the the button is yellow
            } else {
                findFormBtn.setBackgroundColor(getResources().getColor(R.color.colorRed)); //If the right info is not entered then the buttons is red
            }
        } else {
            findFormBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen)); //If the for is sent then the button is green
        }
    }

    public void sendFunnmelding() {
        EmailIntent.sendEmail(""/*FIXME sett inn email adresse her*/, "Funn funnet", funn.getFunnmelding(), funn.getBildeID(), getContext());
        funn.setFunnmeldingSendt(true); //FIXME hvordan vet vi at mailen faktisk ble sendt.
        saveFind();
    }

    public void sendFunnskjema() {
        //TODO finne ut hvordan man lager PDF
        EmailIntent.sendEmail(""/*FIXME sett inn email adresse her*/, "Funn funnet", funn.getFunnskjema() /*FIXME legge til info om bruker */, funn.getBildeID(), getContext());
        funn.setFunnskjemaSendt(true); //FIXME hvordan vet vi at mailen faktisk ble sendt.
        saveFind();
    }
    
    //Returns empty string if string is invalid or the string if it is valid
    public String inputChecker(EditText editText){
        String editTextString = editText.getText().toString();
        //If the editText has an error or the text field is empty the input is invalid
        if(editText.getError() != null || editTextString.equals("")){
            return "";
        }
        return editTextString;
    }

    //Updates the status buttons when editText are changed
    public class StatusUpdater implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateFind();
            updateStatusBtn();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
