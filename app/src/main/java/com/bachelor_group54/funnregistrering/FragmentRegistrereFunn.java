package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRegistrereFunn extends Fragment {
    private View view; //This view wil be used to access elements contained inside the fragment page (like getting text from an editText)
    private Bitmap picture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registrere_lose_funn, container, false); //Loads the page from the XML file
        //Add setup code here later
        return view;
    }

    public void gpsBtn() {
        //TODO legge til bilde funksjon
        Toast.makeText(getContext(), "Har ikke laget gpsfunksjon enda", Toast.LENGTH_LONG).show();
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

            ImageView imageView = view.findViewById(R.id.preview_bilde_nytt_funn); //Finds the ImageView
            imageView.setImageBitmap(picture); //Sets the ImageView to the picture taken from the camera app
        }

        super.onActivityResult(requestCode, resultCode, data); //Calls the super's onActivityResult (Required by Android)
    }

    public void registrerFunnBtn() {
        Funn funn = new Funn();

        EditText title = view.findViewById(R.id.nytt_funn_tittel_et); //Finds the editText that contains the title
        funn.setTittel(title.getText().toString()); //Puts the title in the finds object

        EditText description = view.findViewById(R.id.nytt_funn_beskrivelse_et); //Finds the editText that contains the description
        funn.setBeskrivelse(description.getText().toString());//Adds the description to the find

        //If the a picture has been added save it
        if(picture != null) {
            savePicture(funn);
        }

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialises the class that saves the finds
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Gets the already saved ArrayList with all the previous finds
        //Toast.makeText(getContext(), ((Funn)arrayList.get(0)).getTittel(), Toast.LENGTH_SHORT).show();
        arrayList.add(funn); //Adds the new find to the list

        objektLagrer.saveData(arrayList); //Saves the new list, overwriting the old list
    }

    public void savePicture(Funn funn){
        //Gets the current picture ID for shared preferences (locally saved)
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("pictures", getContext().MODE_PRIVATE);
        int pictureID = sharedpreferences.getInt("pictureID", 0) + 1;

        //Saves the image and saves the ID of the picture to the find
        ImageSaver.saveImage(picture, getContext(), pictureID);
        funn.setBilde(pictureID);

        //Updates the picture ID in shared preferences
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("pictureID", pictureID);
        editor.apply();
    }
}


