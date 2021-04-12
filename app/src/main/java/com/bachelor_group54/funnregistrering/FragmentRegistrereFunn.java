package com.bachelor_group54.funnregistrering;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class FragmentRegistrereFunn extends Fragment {
    private View view; //This view will be used to access elements contained inside the fragment page (like getting text from an editText)
    private Bitmap picture;
    private double latitude = 200; //Initializing latitude variable
    private double longitude = 200; //Initializing longitude variable
    private Funn funn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registrere_lose_funn, container, false); //Loads the page from the XML file
        LinearLayout navbarRegistrereFunn = view.findViewById(R.id.navbar_registrere_funn); //Gets the navbar layout for this view
        navbarRegistrereFunn.setBackground(getContext().getDrawable(R.drawable.navbar_btn_selected_background)); //Setts color on the navbar indicating what page you are on
        return view;
    }

    public void gpsBtn() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE); //Gets the location manager from the system

        Location gps_loc = null;
        Location network_loc = null;

        //Checks for the necessary permissions for getting GPS Location form the system
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //Gets location from the GPS
        } else {
            //No permission ask for it
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        //Checks for the necessary permissions for getting Network Location form the system
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //Gets location from the network (can be used as a backup)
        } else {
            //No permission ask for it
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        if (gps_loc != null) { //Gets location from the GPS if the gps_loc is not null
            latitude = gps_loc.getLatitude();
            longitude = gps_loc.getLongitude();
        } else if (network_loc != null) { //Gets location from the network if network_loc is not null, only if the GPS was not found
            latitude = network_loc.getLatitude();
            longitude = network_loc.getLongitude();
        } //If nether network or gps can provide the location the default values of 0 and 0 is used instead, should be handled in the real program

        TextView textView = view.findViewById(R.id.gps_tv_nytt_funn); //Finds the textView on the main app screen
        textView.setText("Lat: " + latitude + " Long: " + longitude); //Sets the text to show the latitude and longitude of the current location of the device
    }

    @Override
    //This method is called when the users accepts or denies the permission request from the gps button.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //If the requestCode is 1 then the request comes from the gpsBtn.
            //If the request comes from the requestBtn and permission is granted by the user, then we need to call the gpsBtn metode to get the GPS coordinates.
            gpsBtn();
        }
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
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Picture not taken", Toast.LENGTH_LONG).show(); //Prints a message to the user, explaining that no picture was taken
                return; //Return if there is no picture
            }

            ImageView imageView = view.findViewById(R.id.preview_bilde_nytt_funn); //Finds the ImageView
            imageView.setImageBitmap(picture); //Sets the ImageView to the picture taken from the camera app
        }

        super.onActivityResult(requestCode, resultCode, data); //Calls the super's onActivityResult (Required by Android)
    }

    public Funn registrerFunnBtn() {
        funn = new Funn();

        EditText title = view.findViewById(R.id.nytt_funn_tittel_et); //Finds the editText containing the title
        funn.setTittel(title.getText().toString()); //Puts the title in the finds object

        EditText description = view.findViewById(R.id.nytt_funn_beskrivelse_et); //Finds the editText containing the description
        funn.setBeskrivelse(description.getText().toString());//Adds the description to the find

        //Sets latitude and longitude, NOTE default values for both are 200
        funn.setLatitude(latitude);
        funn.setLongitude(longitude);
        //Sets kommune and fylke for the find
        getAddressFromLocation(latitude, longitude);

        Date currentTime = Calendar.getInstance().getTime();
        String date = currentTime.getDate() + "/" + (currentTime.getMonth() + 1) + "/" + (currentTime.getYear() + 1900);
        funn.setDato(date);

        saveFind();
        return funn;
    }

    //TODO registrere kunn med noe info kanskje API med færre felter
    public void sentFindToBackend(){
        SetJSON setJSON = new SetJSON();
        setJSON.execute("Funn/RegistrereFunn", "image=" + ImageSaver.makeBase64FromBitmap(picture),
                "kommune=" + funn.getKommune(), "fylke=" + funn.getFylke(),
                "funndybde=" + funn.getFunndybde(), "gjenstand_markert_med=" + funn.getGjenstandMerking(),
                "koordninat=" + funn.getLatitude() + "N " + funn.getLongitude() + "W", "datum=" + funn.getDatum(),
                "areal_type=" + funn.getArealType(), "brukernavn=" /*TODO legge til brukernavn her*/);
        //TODO legge til info om grunneier
    }

    //Takes the latitude and longitude of the gps coordinates and sets the kommune and fylke of the find
    public void getAddressFromLocation(double lat, double lng) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> locations;
        try{
            locations = coder.getFromLocation(lat, lng, 1); //Uses the Geocoder class to get the Address from the long and lat values.
            if(locations== null) {
                return;
            }
            Address address = locations.get(0);
            funn.setKommune(address.getSubAdminArea());
            funn.setFylke(address.getAdminArea());

            Toast.makeText(getContext(), "Kommune " + address.getSubAdminArea() + " Fylke " + address.getAdminArea(), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Brukes kun til lokal lagring
    public void saveFind() {
        //If the a picture has been added save it
        if (picture != null) {
            savePicture();
        }

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialises the class that saves the finds
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Gets the already saved ArrayList with all the previous finds
        arrayList.add(funn); //Adds the new find to the list

        objektLagrer.saveData(arrayList); //Saves the new list, overwriting the old list
    }

    public void savePicture() {
        //Gets the current picture ID for shared preferences (locally saved)
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("pictures", getContext().MODE_PRIVATE);
        int pictureID = sharedpreferences.getInt("pictureID", 0) + 1;

        //Saves the image and saves the ID of the picture to the find
        ImageSaver.saveImage(picture, getContext(), pictureID);
        funn.setBildeID(pictureID);

        //Updates the picture ID in shared preferences
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("pictureID", pictureID);
        editor.apply();
    }


    public void clearFields(){
        EditText titleEt = view.findViewById(R.id.nytt_funn_tittel_et);
        titleEt.setText("");
        EditText descriptionEt = view.findViewById(R.id.nytt_funn_beskrivelse_et);
        descriptionEt.setText("");
        TextView gpsTv = view.findViewById(R.id.gps_tv_nytt_funn);
        gpsTv.setText("");
        ImageView imageView = view.findViewById(R.id.preview_bilde_nytt_funn);
        imageView.setImageBitmap(null);
    }
}


