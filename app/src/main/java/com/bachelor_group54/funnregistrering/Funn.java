package com.bachelor_group54.funnregistrering;

import android.graphics.Bitmap;

import java.io.Serializable;

//Class for holding info about each found (Contains variables with getters and setters )
public class Funn implements Serializable {
    private String tittel, grunneierNavn, grunneierAdresse, grunneierPostNr, grunneierPostSted,
            grunneierTlf, grunneierEpost, funnsted, kommune, fylke, gjenstand, gjenstandMerking,
            datum, arealType, beskrivelse, funndato, dato, opplysninger;

    private double longitude, latitude, funndybde;

    private int bilde;

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

    public int getBilde() {
        return bilde;
    }

    public void setBilde(int bilde) {
        this.bilde = bilde;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public String getGrunneierNavn() {
        return grunneierNavn;
    }

    public void setGrunneierNavn(String grunneierNavn) {
        this.grunneierNavn = grunneierNavn;
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

    public String getArealType() {
        return arealType;
    }

    public void setArealType(String arealType) {
        this.arealType = arealType;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getFunndato() {
        return funndato;
    }

    public void setFunndato(String funndato) {
        this.funndato = funndato;
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
}
