package com.bachelor_group54.funnregistrering;

import android.content.Context;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

//Class for holding info about each found (Contains variables with getters and setters )
public class Funn implements Serializable {
    private String tittel, grunneierFornavn, grunneierEtternavn, grunneierAdresse, grunneierPostNr, grunneierPostSted,
            grunneierTlf, grunneierEpost, funnsted, kommune, fylke, gjenstand, gjenstandMerking,
            datum, arealType, beskrivelse, dato, opplysninger, gårdNr, gbnr;

    //Sets the depth to -1, then we can check if its not set later, as negative values should not be a valid input
    //The maximum latitude value is 90 and the maximum longitude value is 180. Setting both to 200 means we can later check if the value has been set or not
    private double longitude = 200, latitude = 200, funndybde = -1;

    //The picture int is the number that needs to be given to the ImageSaver class to load the correct image
    private int bildeID, funnID;

    private Bitmap bilde;

    private boolean funnmeldingSendt = false, funnskjemaSendt = false, tillatelseGitt = true;

    //Returns a string that we can send as a find message
    public String getFunnmelding(){
        return tittel + "\n" + "Lengdegrad: " + longitude + "\nBreddegrad: " + latitude + "\n" + beskrivelse;
    }

    //Returns a formatted string of the fields needed for the find form
    public String getFunnskjema(){
        return "Tittel: " + tittel + "\n\n" +

                "Grunneier \n" +
                "GrunneierNavn: " + grunneierFornavn + " " + grunneierEtternavn +"\n" +
                "GrunneierAdresse: " + grunneierAdresse + '\n' +
                "GrunneierPostNr: " + grunneierPostNr + '\n' +
                "GrunneierPostSted: " + grunneierPostSted + '\n' +
                "GrunneierTlf: " + grunneierTlf + '\n' +
                "GrunneierEpost: " + grunneierEpost + "\n\n" +

                "Dato: " + dato + '\n' +
                "Funnsted: " + funnsted + '\n' +
                "GårdNr: " + gårdNr + '\n' +
                "Gbnr: " + gbnr + '\n' +
                "Kommune: " + kommune + '\n' +
                "Fylke: " + fylke + "\n\n" +

                "Gjenstand: " + gjenstand + '\n' +
                "Funndybde: " + funndybde + "\n" +
                "Gjenstand merket med: " + gjenstandMerking + "\n\n" +

                "Breddegrad: " + latitude + "\n" +
                "Lengdegrad: " + longitude + "\n" +
                "Datum: " + datum + '\n' +
                "ArealType: " + arealType + "\n\n" +

                "Beskrivelse: " + beskrivelse + "\n\n" +
                "Opplysninger: " + opplysninger;
    }

    //Checks if all the fields needed for find message is filled
    public boolean isFunnmeldingKlar(){
        if(tittel == null || tittel.equals("") || tittel.equals("null")){
            return false;
        }
        if(bilde == null){
            return false;
        }
        if(latitude == 200) {
            return false;
        }
        return longitude != 200;
    }

    //Checks if all the fields needed for the find form is filled
    public boolean isFunnskjemaKlart(){
        String[] allTheStrings = new String[]{tittel, dato, funnsted, grunneierFornavn, grunneierEtternavn, grunneierAdresse, grunneierPostNr, grunneierPostSted,
            grunneierTlf, grunneierEpost, beskrivelse, gjenstand, gjenstandMerking,
                datum, arealType, gårdNr, gbnr, kommune, fylke};

        for (String s : allTheStrings){
            if(s == null || s.equals("") || s.equals("null")){ //Checks if the strings are valid
                return false;
            }
        }
        return longitude != 200 && latitude != 200 && funndybde != -1 && bilde != null; //Checks if the ints and doubles are valid
    }

    //Gets the index of the current areaType, returns -1 on error
    public int getArealTypeIndex(Context context) {
        if(arealType == null || arealType.equals("") || arealType.equals("null")){return 0;}
        String[] arealTypeArray = context.getResources().getStringArray(R.array.area_type_array);
        int i = 0;
        try {
            while (!arealTypeArray[i].equals(arealType)) {
                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return 0;
        }
        return i;
    }

    //Finds the area type in the list and updates the area type variable
    public void setArealTypeWithIndex(int arealTypeIndex, Context context) {
        this.arealType = context.getResources().getStringArray(R.array.area_type_array)[arealTypeIndex];
    }

    public String getArealType() {
        return arealType;
    }

    public void setArealType(String arealType) {
        this.arealType = arealType;
    }

    public String getGrunneierFornavn() {
        return grunneierFornavn;
    }

    public void setGrunneierFornavn(String grunneierFornavn) {
        this.grunneierFornavn = grunneierFornavn;
    }

    public String getGrunneierEtternavn() {
        return grunneierEtternavn;
    }

    public void setGrunneierEtternavn(String grunneierEtternavn) {
        this.grunneierEtternavn = grunneierEtternavn;
    }

    public Bitmap getBilde() {
        return bilde;
    }

    public void setBilde(Bitmap bilde) {
        this.bilde = bilde;
    }

    public String getOpplysninger() {
        return opplysninger;
    }

    public void setOpplysninger(String opplysninger) {
        this.opplysninger = opplysninger;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public int getBildeID() {
        return bildeID;
    }

    public void setBildeID(int bildeID) {
        this.bildeID = bildeID;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public String getGrunneierAdresse() {
        return grunneierAdresse;
    }

    public void setGrunneierAdresse(String grunneierAdresse) {
        this.grunneierAdresse = grunneierAdresse;
    }

    public String getGrunneierPostNr() {
        return grunneierPostNr;
    }

    public void setGrunneierPostNr(String grunneierPostNr) {
        this.grunneierPostNr = grunneierPostNr;
    }

    public String getGrunneierPostSted() {
        return grunneierPostSted;
    }

    public void setGrunneierPostSted(String grunneierPostSted) {
        this.grunneierPostSted = grunneierPostSted;
    }

    public String getGrunneierTlf() {
        return grunneierTlf;
    }

    public void setGrunneierTlf(String grunneierTlf) {
        this.grunneierTlf = grunneierTlf;
    }

    public String getGrunneierEpost() {
        return grunneierEpost;
    }

    public void setGrunneierEpost(String grunneierEpost) {
        this.grunneierEpost = grunneierEpost;
    }

    public String getFunnsted() {
        return funnsted;
    }

    public void setFunnsted(String funnsted) {
        this.funnsted = funnsted;
    }

    public String getKommune() {
        return kommune;
    }

    public void setKommune(String kommune) {
        this.kommune = kommune;
    }

    public String getFylke() {
        return fylke;
    }

    public void setFylke(String fylke) {
        this.fylke = fylke;
    }

    public String getGjenstand() {
        return gjenstand;
    }

    public void setGjenstand(String gjenstand) {
        this.gjenstand = gjenstand;
    }

    public String getGjenstandMerking() {
        return gjenstandMerking;
    }

    public void setGjenstandMerking(String gjenstandMerking) {
        this.gjenstandMerking = gjenstandMerking;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getFunndybde() {
        return funndybde;
    }

    public void setFunndybde(double funndybde) {
        this.funndybde = funndybde;
    }

    public String getGårdNr() {
        return gårdNr;
    }

    public void setGårdNr(String gårdNr) {
        this.gårdNr = gårdNr;
    }

    public String getGbnr() {
        return gbnr;
    }

    public void setGbnr(String gbnr) {
        this.gbnr = gbnr;
    }

    public boolean isFunnmeldingSendt() {
        return funnmeldingSendt;
    }

    public void setFunnmeldingSendt(boolean funnmeldingSendt) {
        this.funnmeldingSendt = funnmeldingSendt;
    }

    public boolean isFunnskjemaSendt() {
        return funnskjemaSendt;
    }

    public void setFunnskjemaSendt(boolean funnskjemaSendt) {
        this.funnskjemaSendt = funnskjemaSendt;
    }

    public int getFunnID() {
        return funnID;
    }

    public void setFunnID(int funnID) {
        this.funnID = funnID;
    }

    public boolean isTillatelseGitt() {
        return tillatelseGitt;
    }

    public void setTillatelseGitt(boolean tillatelseGitt) {
        this.tillatelseGitt = tillatelseGitt;
    }
}
