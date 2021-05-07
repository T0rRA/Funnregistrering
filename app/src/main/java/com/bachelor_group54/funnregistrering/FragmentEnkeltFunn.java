package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



//This fragment displays one selected find at the time. The find can also be edited here.
public class FragmentEnkeltFunn extends Fragment {
    private View view;
    private Funn funn; //The find the view is displaying
    private int position; //The finds position in the saved list
    private Bitmap picture;
    private boolean isFindSavable;
    private String errorMessage;
    private Bitmap scalebmp; // creating variable for image storing
    private Rect bounds = new Rect();

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

        //Initializing and scaling of the background /TODO: Kan brukes for scaling av bildet også om det skal med
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.funnskjema_bg);
        scalebmp =Bitmap.createScaledBitmap(bmp,2480,3508,false);


        //Initializes dropdown for area type
        Spinner areaType = view.findViewById(R.id.fragment_enkelt_funn_dropdown_arealtype);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.area_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        areaType.setAdapter(adapter);

        loadFunn();
        updateStatusBtn();
        setTextWatchers();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        super.onResume();
        updateStatusBtn();
    }

    //Parmams: str is the string we want to split. splitSize is the size of each split.
    public ArrayList<String> makeLine(String str, int splitSize){
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder outString = new StringBuilder();
        int currLength = 0;

        for(String s: str.split(" ")){
            currLength += s.length();
            if(currLength > splitSize){
                lines.add(outString.toString());
                outString = new StringBuilder();
                currLength = 0;
            }
            outString.append(s).append(" ");
        }
        lines.add(outString.toString());
        return lines;
    }

    public void drawString(Canvas canvas, Paint paint, String str, int x, int y) {
        ArrayList<String> lines = makeLine(str, 75);
        int yoff = 0; // lengde fra topp
        for (int i=0; i<lines.size();i++ ) {
            canvas.drawText(lines.get(i), x, y + yoff, paint);
            paint.getTextBounds(lines.get(i), 0, lines.get(i).length(), bounds);
            yoff += bounds.height();
        }
    }

    //Makes the status buttons update when editTexts are changed
    public void setTextWatchers() {
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
                , view.findViewById(R.id.fragment_enkelt_funn_et_annet)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr)
                , view.findViewById(R.id.fragment_enkelt_funn_et_gbnr)
                , view.findViewById(R.id.fragment_enkelt_funn_et_kommune)
                , view.findViewById(R.id.fragment_enkelt_funn_et_fylke)};

        for (int i = 0; i < editTexts.length; i++) {
            EditText et = editTexts[i];
            //Switch case that sets the appropriate input validation on the EditTexts
            switch (i){
                case 0: //Latitude
                    et.addTextChangedListener(new InputValidater(getContext(),false,true, true, 1, 20, et));
                    et.addTextChangedListener(new NumberMaxMinChecker(90, -90, et));
                    break;
                case 1: //Longitude
                    et.addTextChangedListener(new NumberMaxMinChecker(180, -180, et));
                case 2: //Depth
                    et.addTextChangedListener(new InputValidater(getContext(),false,true, true, 1, 20, et));
                    break;
                case 3: //Title
                    et.addTextChangedListener(new InputValidater(getContext(),true,true, false, 1, 30, et));
                    break;
                case 4: //Date
                    et.addTextChangedListener(new InputValidater(getContext(),false,true, false, 10, 10, et));
                    break;
                case 5: //Location (Funnsted) Fixme vet ikke hva som skal i dette feltet
                    et.addTextChangedListener(new InputValidater(getContext(), true, false, false, 0, 50, et));
                    break;
                case 6: //Owner name
                    et.addTextChangedListener(new InputValidater(getContext(), true, false, true, 1, 75, et));
                    break;
                case 7: //Owner address
                case 8: //Owner email
                    et.addTextChangedListener(new InputValidater(getContext(), true, true, true, 1, 50, et));
                    break;
                case 9:
                    et.addTextChangedListener(new InputValidater(getContext(), false, true, false, 4, 4, et));
                    break;
                case 10: //Owner post place
                case 19: //County
                case 20: //Municipality
                    et.addTextChangedListener(new InputValidater(getContext(), true, false, false, 1, 30, et));
                    break;
                case 11: //Owner tlf
                    et.addTextChangedListener(new PhoneInputValidator(et));
                    break;
                case 12: //Description //fixme skal denn være med?
                case 16: //Other info
                    et.addTextChangedListener(new InputValidater(getContext(), true, false, false, 0, 200, et));
                    break;
                case 13: //Item fixme vet ikke hva som skal stå i dette feltet så det må kanskje endres senere
                    et.addTextChangedListener(new InputValidater(getContext(), true, false, false, 0, 50, et));
                    break;
                case 14: //Item marked with
                    et.addTextChangedListener(new InputValidater(getContext(), true, true, false, 0, 30, et));
                    break;
                case 15: //Datum
                    et.addTextChangedListener(new InputValidater(getContext(), false, true, true, 0, 6, et));
                    break;
                case 17: //Farm fixme vet ikke hva som skal stå her
                case 18: //Gbnr fixme vet ikke hva som skal stå her
                    et.addTextChangedListener(new InputValidater(getContext(), false, true, false, 0, 8, et));
                    break;
            }
            et.addTextChangedListener(new StatusUpdater()); //Setts the textWatcher on the editText
        }
    }

    public void loadFunn() {
        ImageView imageView = view.findViewById(R.id.fragment_enkelt_funn_bilde); //Finds the image view
        imageView.setImageBitmap(funn.getBilde()); //Sets the image view to the finds image

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
        if (funn.getFunndybde() != -1) { //If depth == -1 then depth is not set
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
        setText(funn.getGrunneierFornavn() + " " + funn.getGrunneierEtternavn(), ownerEt); //TODO lagge egene felter for fornavn og etternavn

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

        Spinner areaType = view.findViewById(R.id.fragment_enkelt_funn_dropdown_arealtype);
        areaType.setSelection(funn.getArealTypeIndex(getContext()));

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
        if (string == null || string.equals("") || string.equals("null") || string.equals("null null")) { //If null or empty string return not filled message
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

    //Saves the find and returns true on success and false for fail
    public boolean saveFind() {
        //Updates the find with the changed information
        updateFind();

        if(!isFindSavable){
            Toast.makeText(getContext(), getString(R.string.feil_i_innputfelter) + errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

        //If the a picture has been added save it
        if (picture != null) {
            savePicture();
        }

        ObjektLagrer objektLagrer = new ObjektLagrer(getContext(), "funn"); //Initialises the class that saves the finds
        ArrayList<Object> arrayList = objektLagrer.loadData(); //Gets the already saved ArrayList with all the previous finds
        arrayList.set(position, funn); //Overwrites the previous finds

        objektLagrer.saveData(arrayList); //Saves the new list, overwriting the old list

        return true;
    }

    public void editFind(Context context){
        updateFind();

        User user = User.getInstance();

        Map<String,String> params = new HashMap<String, String>();
        params.put("tittel", makeStringNonNull(funn.getTittel()));
        params.put("beskrivelse", makeStringNonNull(funn.getBeskrivelse()));
        params.put("image" , makeStringNonNull(ImageSaver.makeBase64FromBitmap(funn.getBilde())));
        params.put("funndato" , makeStringNonNull(funn.getDato()));
        params.put("kommune", makeStringNonNull(funn.getKommune()));
        params.put("fylke" , makeStringNonNull(funn.getFylke()));
        params.put("funndybde" , funn.getFunndybde()+"");
        params.put("gjenstand_markert_med" , makeStringNonNull(funn.getGjenstandMerking()));
        params.put("koordinat" , funn.getLatitude() + "N " + funn.getLongitude() + "W");
        params.put("datum" , makeStringNonNull(funn.getDatum()));
        params.put("areal_type" , makeStringNonNull(funn.getArealType()));

        params.put("BrukerUserID" , "11"); //fixme uncomment user.getUsername
        params.put("FunnID", funn.getFunnID() + "");

        params.put("gbnr.gb_nr" , makeStringNonNull(funn.getGbnr()));
        params.put("gbnr.grunneier.Fornavn" , makeStringNonNull(funn.getGrunneierFornavn()));
        params.put( "gbnr.grunneier.Etternavn" , makeStringNonNull(funn.getGrunneierEtternavn()));
        params.put("gbnr.grunneier.Adresse" , makeStringNonNull(funn.getGrunneierAdresse()));
        params.put("gbnr.grunneier.Postnr.Postnr" , makeStringNonNull(funn.getGrunneierPostNr()));
        params.put("gbnr.grunneier.Postnr.Poststed" , makeStringNonNull(funn.getGrunneierPostSted()));
        params.put("gbnr.grunneier.Tlf" , makeStringNonNull(funn.getGrunneierTlf()));
        params.put("gbnr.grunneier.Epost" , makeStringNonNull(funn.getGrunneierEpost()));

        SendToServer.postRequest(context, params, "Funn/EditFunn", FragmentList.getFragmentMineFunn());
    }

    public String makeStringNonNull(String s){
        return s == null || s.equals("") ? "null" : s; //Saves the data as string "null" instead of null to prevent database issues
    }

    //This method is used for updating the find before saving it
    public void updateFind() {
        isFindSavable = true;
        errorMessage = "";

        EditText latitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_breddegrad); //Finds the latitude editText
        if (!latitudeEt.getText().toString().equals("")) {
            try {
                funn.setLatitude(Double.parseDouble(inputChecker(latitudeEt, "breddegrad")));//Updates the latitude in the find
            } catch (NumberFormatException e) {/*Do noting*/}
        }

        EditText longitudeEt = view.findViewById(R.id.fragment_enkelt_funn_et_lengdegrad); //Finds the longitude editText
        if (!longitudeEt.getText().toString().equals("")) {
            try {
                funn.setLongitude(Double.parseDouble(inputChecker(longitudeEt, "lengdegrad"))); //Updates the longitude in the find
            } catch (NumberFormatException e) {/*Do noting*/}
        }

        EditText depth = view.findViewById(R.id.fragment_enkelt_funn_et_funndybde); //Finds the text field
        if (!depth.getText().toString().equals("")) {
            try {
                funn.setFunndybde(Double.parseDouble(inputChecker(depth, "funndybde"))); //Changes the info inn the find
            } catch (NumberFormatException e) {/*Do noting*/}
        }

//Do not overwrite the picture if it has not been changed
        if(picture != null) {
            funn.setBilde(picture);
        }

        //Just the same all the way, find the text fields and updates the find
        EditText titleEt = view.findViewById(R.id.fragment_enkelt_funn_et_tittel);
        funn.setTittel(inputChecker(titleEt, "tittel"));

        EditText dateEt = view.findViewById(R.id.fragment_enkelt_funn_et_dato);
        funn.setDato(inputChecker(dateEt, "dato"));

        EditText locationEt = view.findViewById(R.id.fragment_enkelt_funn_et_sted);
        funn.setFunnsted(inputChecker(locationEt, "sted"));

        //TODO lage egene felter for fornavn og etternavn
        EditText ownerEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneier);
        funn.setGrunneierFornavn(inputChecker(ownerEt, "grunneier"));

        EditText ownerAddressEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierAdresse);
        funn.setGrunneierAdresse(inputChecker(ownerAddressEt, "grunneier adresse"));

        EditText ownerPostalCodeEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostNr);
        funn.setGrunneierPostNr(inputChecker(ownerPostalCodeEt, "grunneier postnr"));

        EditText ownerPostalPlaceEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierPostSted);
        funn.setGrunneierPostSted(inputChecker(ownerPostalPlaceEt, "grunneier poststed"));

        EditText ownerTlfEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierTlf);
        funn.setGrunneierTlf(inputChecker(ownerTlfEt, "grunneier tlf"));

        EditText ownerEmailEt = view.findViewById(R.id.fragment_enkelt_funn_et_grunneierEpost);
        funn.setGrunneierEpost(inputChecker(ownerEmailEt, "grunneier e-post"));

        EditText descriptionEt = view.findViewById(R.id.fragment_enkelt_funn_et_beskrivelse);
        funn.setBeskrivelse(inputChecker(descriptionEt, "beskrivelse"));

        EditText itemEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand);
        funn.setGjenstand(inputChecker(itemEt, "gjenstand"));

        EditText itemMarkingEt = view.findViewById(R.id.fragment_enkelt_funn_et_gjenstand_merke);
        funn.setGjenstandMerking(inputChecker(itemMarkingEt, "gjenstand merket med"));

        EditText ageEt = view.findViewById(R.id.fragment_enkelt_funn_et_datum);
        funn.setDatum(inputChecker(ageEt, "datum"));

        Spinner areaTypeSpinner = view.findViewById(R.id.fragment_enkelt_funn_dropdown_arealtype);
        funn.setArealTypeWithIndex((int)areaTypeSpinner.getSelectedItemId(), getContext());

        EditText moreInfoEt = view.findViewById(R.id.fragment_enkelt_funn_et_annet);
        funn.setOpplysninger(inputChecker(moreInfoEt, "andre opplysninger"));

        EditText farmNrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gårdnr);
        funn.setGårdNr(inputChecker(farmNrEt, "gård"));

        EditText gbnrEt = view.findViewById(R.id.fragment_enkelt_funn_et_gbnr);
        funn.setGbnr(inputChecker(gbnrEt, "gbnr"));

        EditText municipalityEt = view.findViewById(R.id.fragment_enkelt_funn_et_kommune);
        funn.setKommune(inputChecker(municipalityEt, "kommune"));

        EditText countyEt = view.findViewById(R.id.fragment_enkelt_funn_et_fylke);
        funn.setFylke(inputChecker(countyEt, "fylke"));
    }

    public File pdfGenerator(){
        // Creation of an object variable for the PDF document
        PdfDocument pdfDocument = new PdfDocument();

        //Paint is used to draw shapes and add text
        Paint picture = new Paint();
        Paint text = new Paint();

        /*Adding pageInfo to the the PDF
        * Passing the width, height and number of pages
        * Creates the PDF */
        // declaring pdf height
        int pdfHeight = 3508;
        // declaring pdf width
        int pdfWidth = 2480;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pdfWidth, pdfHeight,1).create();

        //sets the PDFs startpage.
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        //creates canvas variable from the startpage.
        Canvas canvas = page.getCanvas();

        /*Draws image on pdf file:
        * bitmap is the 1st parameter,
        * position from left is the 2nd parameter,
        * position from top is the 3rd parameter,
        * the paint variable is the 4th parameter. */
        canvas.drawBitmap(scalebmp,0,0,picture);

        // adding typeface for the text
        text.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));

        //Setting text size (in the pdf)
        text.setTextSize(36);

        //Setting color on the text
       /*
        int color = ContextCompat.getColor(getContext(),R.color.colorPrimaryDark);
        text.setColor(color);
        */

        /* Draws the text on the pdf
        * the text is the 1st parameter,
        * the position from start is the 2nd parameter,
        * the position from top is the 3rd parameter,
        * the paint variable  (text) is the 4th parameter.
        * */

        /*  Finner:
        *  TODO: Legg til poststed i User.java*/
        User user = User.getInstance();
        canvas.drawText(user.getName()+""+user.getLastName(), 300,450, text); // Navn
        canvas.drawText(user.getAddress(), 300,575, text); // Adresse
        canvas.drawText(user.getPostalCode(), 300,700, text); // Postnr.
        canvas.drawText("LEGG TIL POSTSTED I USER", 700,700, text); //sted
        canvas.drawText(user.getPhoneNum(), 300,825, text); // Tlf
        canvas.drawText(user.getEmail(), 300,955, text); // epost


        /*  Grunneier:  TODO: Registrering av tillatelse*/
        canvas.drawText(funn.getGrunneierFornavn() + " " + funn.getGrunneierEtternavn(), 1500,450, text); // Navn
        canvas.drawText(funn.getGrunneierAdresse(), 1500,575, text); // Adresse
        canvas.drawText(funn.getGrunneierPostNr(), 1500,700, text); // Postnr.
        canvas.drawText(funn.getGrunneierPostSted(), 1900,700, text); //sted
        canvas.drawText(funn.getGrunneierTlf(), 1500,825, text); // Tlf
        canvas.drawText(funn.getGrunneierEpost(), 1500,955, text); // epost
        canvas.drawText("X", 2335,855, text); // Tillatelse

        /*Funnet*/
        canvas.drawText(funn.getDato(),110, 1175, text); //Funndato
        canvas.drawText(funn.getFunnsted(),425, 1175, text); // Funnsted, gård, gbnr
        canvas.drawText(funn.getKommune(),1250, 1175, text); // Kommune
        canvas.drawText(funn.getFylke(),1900, 1175, text); // Fylke

        canvas.drawText(funn.getGjenstand(),110, 1360, text); //Gjenstand
        canvas.drawText(""+funn.getFunndybde(),1250, 1360, text); // Funndybde
        canvas.drawText(funn.getGjenstandMerking(),1575, 1360, text); // merket med

        canvas.drawText(""+funn.getLongitude(),110, 1550, text); // øst
        canvas.drawText(""+funn.getLatitude(),550, 1550, text); // nord
        canvas.drawText(funn.getDatum(),1000, 1550, text); //datum/projeksjon

        /*MåleMetode*/
        canvas.drawText(" ",615,1650,text); //Håndholdt GPS
        canvas.drawText("X",950,1650,text); // Mobiltelefon
        canvas.drawText(" ",1280,1650,text); // Digitalt kart

        /*Arealtype*/
        //TODO legg til når droppdown i funn er fiksa
        canvas.drawText("X",1755,1555,text); // Åker
        canvas.drawText("X",1755,1595,text); // Beite
        canvas.drawText("X",1755,1635,text); // Hage

        canvas.drawText("X",1990,1515,text); // Skog
        canvas.drawText("X",1990,1555,text); // Fjell
        canvas.drawText("X",1990,1595,text); // Strand
        canvas.drawText("X",1990,1635,text); // Vann

        //Andre opplysninger og observasjoner
        drawString(canvas,text, funn.getBeskrivelse(),110,1850);

        //finishing the page
        pdfDocument.finishPage(page);

        //sets storage path
        String path = getContext().getFilesDir().getPath(); //Gets program path
        String filename = "/funnskjema.pdf"; //Sets the pdf name TODO: add Dynamisk navn på funnskjema(?)
        File file = new File(path+filename);

        //writes the pdf to the path
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(), "PDF'en er lagd!", Toast.LENGTH_SHORT).show();
        } catch (IOException e){ //error handling
            e.printStackTrace();
        }
        //closing pdf
        pdfDocument.close();
        return file;
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

    public int getNextPictureID() {
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
                if (funn.getBildeID() == 0) {
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
        EmailIntent.sendEmail(""/*FIXME sett inn email adresse her*/, "Funn funnet", funn.getFunnmelding(), getContext(),new File(ImageSaver.getImagePath(getContext(),funn.getBildeID())));
        funn.setFunnmeldingSendt(true); //FIXME hvordan vet vi at mailen faktisk ble sendt.
        saveFind(); /*TODO endre til editFind, trenger lagring av funnmeldingSend variablen*/
    }

    public void sendFunnskjema() {
        //TODO finne ut hvordan man lager PDF -> lagdt til metode for generering av pdf

        EmailIntent.sendEmail("tor.ryan.andersen@gmail.com"/*FIXME sett inn email adresse her*/, "Funn funnet", funn.getFunnskjema() /*FIXME legge til info om bruker */, getContext(), pdfGenerator());
        funn.setFunnskjemaSendt(true); //FIXME hvordan vet vi at mailen faktisk ble sendt.
        saveFind(); /*TODO endre til editFind, trenger lagring av funnskjemaSendt variablen*/
    }

    //Returns empty string if string is invalid or the string if it is valid
    public String inputChecker(EditText editText, String fieldName) {
        String editTextString = editText.getText().toString();
        //If the editText has an error then the input is invalid
        if (editText.getError() != null) {
            isFindSavable = false;
            errorMessage += errorMessage.equals("") ? fieldName : ", " + fieldName; //If it's the first error no comma, the rest wil have comma
            return "";
        }
        return editTextString;
    }

    //Updates the status buttons when editText are changed
    public class StatusUpdater implements TextWatcher {

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

    public class NumberMaxMinChecker implements TextWatcher {
        private int max;
        private int min;
        private EditText editText;

        public NumberMaxMinChecker(int max, int min, EditText editText) {
            this.max = max;
            this.min = min;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                double number = Double.parseDouble(charSequence.toString());
                if(number > max){
                    editText.setError(getString(R.string.Tall_for_stort) + (max + 1));
                }else if(number < min){
                    editText.setError(getString(R.string.Tall_for_lite) + (min - 1));
                }
            } catch (NumberFormatException e){
                e.printStackTrace();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
