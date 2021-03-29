package com.bachelor_group54.funnregistrering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

//This fragment displays one selected find at the time. The find can also be edited here.
public class FragmentEnkeltFunn extends Fragment {
    private View view;
    private Funn funn; //The find the view is displaying
    private int position; //The finds position in the saved list
    private Bitmap picture;
    private int pdfHeight = 1120; // declaring pdf height
    private int pdfWidth = 792; // declaring pdf width
    private Bitmap bmp, scalebmp; // creating variable for image storing
    private static final int PERMISSION_REQUEST_CODE = 200; //for runtime permissions

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

        //Initializing and scarling of the logo /TODO: the same for the picture
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.raloeve);
        scalebmp =Bitmap.createScaledBitmap(bmp,140,140,false);

        //TODO: add checking and requesting premissions code
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

        for (EditText et : editTexts){
            et.addTextChangedListener(new StatusUpdater()); //Setts the textWatcher on the editText
        }
    }

    public void loadFunn() {
        String tomtFelt = "ikke fylt ut";
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(ImageSaver.loadImage(getContext(), funn.getBildeID())); //Sets the image view to the finds image

        //TODO finne ut hvilke felter brukeren skal kunne endre selv

        EditText latitude = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude textView
        //Latitude cannot be more than 90 or less than -90
        if (funn.getLatitude() >= -90 && funn.getLatitude() <= 90) {
            latitude.setText("" + funn.getLatitude());
        }

        EditText longitude = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude textView
        //Longitude cannot be more than 180 or less than -180
        if (funn.getLongitude() >= -180 && funn.getLongitude() <= 180) {
            longitude.setText("" + funn.getLongitude());
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde);
        if (funn.getFunndybde() == -1) {//-1 is the default value
            depth.setHint(tomtFelt);
        } else {
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

        EditText ownerAddress = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse);
        setText(funn.getGrunneierAdresse(), ownerAddress);

        EditText ownerPostalCode = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr);
        setText(funn.getGrunneierPostNr(), ownerPostalCode);

        EditText ownerPostalPlace = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted);
        setText(funn.getGrunneierPostSted(), ownerPostalPlace);

        EditText ownerTlf = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf);
        setText(funn.getGrunneierTlf(), ownerTlf);

        EditText ownerEmail = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost);
        setText(funn.getGrunneierEpost(), ownerEmail);

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

        EditText gårdNr = view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr);
        setText(funn.getGårdNr(), gårdNr);

        EditText gbnr = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        setText(funn.getGbnr(), gbnr);

        EditText kommune = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        setText(funn.getKommune(), kommune);

        EditText fylke = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        setText(funn.getFylke(), fylke);
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
        EditText latitude = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude editText
        if (!latitude.getText().toString().equals("")) {
            try {
                funn.setLatitude(Double.parseDouble(latitude.getText().toString()));//Updates the latitude in the find
            }catch (NumberFormatException e){/*Do noting*/}
        }

        //FIXME legge til sjekk for om longitude er over 180 eller under -180
        EditText longitude = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude editText
        if (!latitude.getText().toString().equals("")) {
            try {
            funn.setLongitude(Double.parseDouble(longitude.getText().toString())); //Updates the longitude in the find
            }catch (NumberFormatException e){/*Do noting*/}
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde); //Finds the text field
        if (!depth.getText().toString().equals("")) {
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

        EditText ownerAddress = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse);
        funn.setGrunneierAdresse(ownerAddress.getText().toString());

        EditText ownerPostalCode = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr);
        funn.setGrunneierPostNr(ownerPostalCode.getText().toString());

        EditText ownerPostalPlace = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted);
        funn.setGrunneierPostSted(ownerPostalPlace.getText().toString());

        EditText ownerTlf = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf);
        funn.setGrunneierTlf(ownerTlf.getText().toString());

        EditText ownerEmail = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost);
        funn.setGrunneierEpost(ownerEmail.getText().toString());

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

        EditText gårdNr = view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr);
        funn.setGårdNr(gårdNr.getText().toString());

        EditText gbnr = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        funn.setGbnr(gbnr.getText().toString());

        EditText kommune = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        funn.setKommune(kommune.getText().toString());

        EditText fylke = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        funn.setFylke(fylke.getText().toString());
    }

    public void pdfGenerator(){
        // Creation of an object variable for the PDF document
        PdfDocument pdfDocument = new PdfDocument();

        //Paint is used to draw shapes and add text
        Paint logo = new Paint();
        Paint text = new Paint();

        /*Adding pageInfo to the the PDF
        * Passing the width, height and number of pages
        * Creates the PDF */
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pdfWidth,pdfHeight,1).create();

        //sets the PDFs startpage.
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        //creates canvas variable from the startpage.
        Canvas canvas = page.getCanvas();

        /*Draws image on pdf file:
        * bitmap is the 1st parameter,
        * position from left is the 2nd parameter,
        * position from top is the 3rd parameter,
        * the paint variable is the 4th parameter. */
        canvas.drawBitmap(scalebmp,56,40,logo);

        // adding typeface for the text
        text.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        //Setting text size (in the pdf)
        text.setTextSize(16);

        /* Draws the text on the pdf
        * the text is the 1st parameter,
        * the position from start is the 2nd parameter,
        * the position from top is the 3rd parameter,
        * the paint variable  (text) is the 4th parameter.
        * */
        canvas.drawText("Dette er en test for å se om alt fungerer som det skal", 209,100, text);
        canvas.drawText("Her er enda en test", 209,80, text);
        canvas.drawText("Og dette er enda litt mer testing", 209,60, text);

        //finishing the page
        pdfDocument.finishPage(page);

        //sets storage path
        File file = new File(Environment.getExternalStorageDirectory(),"pdfTest.pdf");

        //writes the pdf to the path
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(), "PDF'en er lagd!", Toast.LENGTH_SHORT).show();
        } catch (IOException e){ //error handling
            e.printStackTrace();
        }
        //closing pdf
        pdfDocument.close();
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
        pdfGenerator();
    }

    public void sendFunnskjema() {
        //TODO finne ut hvordan man lager PDF -> lagdt til metode for generering av pdf

        EmailIntent.sendEmail(""/*FIXME sett inn email adresse her*/, "Funn funnet", funn.getFunnskjema() /*FIXME legge til info om bruker */, funn.getBildeID(), getContext());
        funn.setFunnskjemaSendt(true); //FIXME hvordan vet vi at mailen faktisk ble sendt.
        saveFind();
        pdfGenerator();
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
