package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.Intent;
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
            Bitmap picture; //Initializing the bitmap
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

        EditText tittel = view.findViewById(R.id.nytt_funn_tittel_et); //Finner editText som holder tittelen
        funn.setTittel(tittel.getText().toString()); //Legger tittelen fra editText'en til i funnet

        EditText beskrivelse = view.findViewById(R.id.nytt_funn_beskrivelse_et); //Finner beskrivelse editText'en
        funn.setBeskrivelse(beskrivelse.getText().toString());//Legger beskrivelsen til i funnet

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialiserer objektLagret med context og filNavnet
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Henter arrayet som alerede er lagret på filNavnet
        arrayList.add(funn); //Legger det nye funnet til i listen

        objektLagrer.saveData(arrayList); //Lagrer den nye listen og overskriver den gamle listen
    }
}


