package com.bachelor_group54.funnregistrering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return view;
    }

    public void loadFunn(){
        String tomtFelt = "ikke fylt ut";
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(ImageSaver.loadImage(getContext(), funn.getBildeID())); //Sets the image view to the finds image

        //TODO finne ut hvilke felter brukeren skal kunne endre selv

        EditText latitude = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude textView
        //Latitude cannot be more than 90 or less than -90
        if(funn.getLatitude() >= -90 && funn.getLatitude() <= 90){
            latitude.setText("" + funn.getLatitude());
        }

        EditText longitude = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude textView
        //Longitude cannot be more than 180 or less than -180
        if(funn.getLongitude() >= -180 && funn.getLongitude() <= 180){
            longitude.setText("" + funn.getLongitude());
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde);
        if(funn.getFunndybde() == -1){//-1 is the default value
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

        //FIXME legge til gårdnr, bruksnr, kommune og fylke
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

    public void saveFind(){
        //Updates the find with the changed information
        updateFind();

        //If the a picture has been added save it
        if(picture != null) {
            savePicture();
        }

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialises the class that saves the finds
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Gets the already saved ArrayList with all the previous finds
        arrayList.set(position, funn); //Overwrites the previous finds

        objektLagrer.saveData(arrayList); //Saves the new list, overwriting the old list
    }

    //This method is used for updating the find before saving it
    public void updateFind(){
        //FIXME legge til sjekk for om latitude er over 90 eller under -90
        EditText latitude = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude editText
        if(!latitude.getText().toString().equals("")) {
            funn.setLatitude(Double.parseDouble(latitude.getText().toString()));//Updates the latitude in the find
        }

        //FIXME legge til sjekk for om longitude er over 180 eller under -180
        EditText longitude = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude editText
        if(!latitude.getText().toString().equals("")) {
            funn.setLongitude(Double.parseDouble(longitude.getText().toString())); //Updates the longitude in the find
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde); //Finds the text field
        if(!depth.getText().toString().equals("")) {
            funn.setFunndybde(Double.parseDouble(depth.getText().toString())); //Changes the info inn the find
        }

        //Just the same all the way, find the text fields and updates the find
        EditText title = view.findViewById(R.id.fragment_enkelt_funn_et_tittel);
        funn.setTittel(title.getText().toString());

        EditText date = view.findViewById(R.id.fragment_enkelt_funn_et_dato);
        funn.setDato(date.getText().toString());

        EditText location = view.findViewById(R.id.fragment_enkelt_funn_et_sted);
        funn.setFunnsted(location.getText().toString());

        EditText owner = view.findViewById(R.id.fragment_enkelt_funn_et_grunneier);
        funn.setGrunneierNavn(owner.getText().toString());

        //TODO legge til status
        TextView status = view.findViewById(R.id.fragment_enkelt_funn_tv_status);
        status.setText("Status: vi har ikke noe status");

        EditText description = view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse);
        funn.setBeskrivelse(description.getText().toString());

        EditText item = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand);
        funn.setGjenstand(item.getText().toString());

        EditText itemMarking = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke);
        funn.setGjenstandMerking(itemMarking.getText().toString());

        EditText age = view.findViewById(R.id.fragment_enkelt_funn_et_datum);
        funn.setDatum(age.getText().toString());

        EditText areaType = view.findViewById(R.id.fragment_enkelt_funn_et_arealtype);
        funn.setArealType(areaType.getText().toString());

        EditText moreInfo = view.findViewById(R.id.fragment_enkelt_funn_et_annet);
        funn.setOpplysninger(moreInfo.getText().toString());

        //TODO legg til resten av feltene
    }


    public void savePicture(){
        //Gets the image ID
        int pictureID = funn.getBildeID();

        //If no picture has been set get a new picture ID
        if(pictureID == 0){
            //Gets the next available pictureID
            SharedPreferences sharedpreferences = getContext().getSharedPreferences("pictures", getContext().MODE_PRIVATE);
            pictureID = sharedpreferences.getInt("pictureID", 0) + 1;

            //Updates the picture ID in shared preferences
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("pictureID", pictureID);
            editor.apply();

            funn.setBildeID(pictureID);
        }

        //Saves the image
        ImageSaver.saveImage(picture, getContext(), pictureID);

    }

    int CAMERA_PIC_REQUEST = 1337; //Setting the request code for the camera intent, this is used to identify the result when it is returned after taking the picture in onActivityResult.

    //This method opens the camera app when clicking the "Take image" button
    public void bildeBtn(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Makes an intent of the image capture type
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST); //Starts the camera app and waits for the result
    }

    @Override
    //This method receives the image from the camera app and setts the ImageView to that image.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_PIC_REQUEST) { //If the requestCode matches that of the startActivityForResult of the cameraIntent we know it is the camera app that is returning it's data.
            try { //May produce null pointers if the picture is not taken
                picture = (Bitmap) data.getExtras().get("data"); //Gets the picture from the camera app and saves it as a Bitmap
            }catch (NullPointerException e){
                Toast.makeText(getContext(), "Picture not taken", Toast.LENGTH_LONG).show(); //Prints a message to the user, explaining that no picture was taken
                return; //Return if there is no picture
            }

            ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the ImageView
            imageView.setImageBitmap(picture); //Sets the ImageView to the picture taken from the camera app
        }

        super.onActivityResult(requestCode, resultCode, data); //Calls the super's onActivityResult (Required by Android)
    }

}
