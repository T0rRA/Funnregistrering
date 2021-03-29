package com.bachelor_group54.funnregistrering;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class SetJSON extends AsyncTask<String, Void, String> {
    private TextView textView;

    public SetJSON() {
    }

    public SetJSON(TextView textView) {
        this.textView = textView;
    }

    //FIXME endre denne til å passe med serveren
    @Override
    //Må legge inn filnavn(jsonhusinn.php) etterfulgt av parameterene (Beskrivelse=gult hus, gateadresse=pilestredet31, osv.)
    protected String doInBackground(String... strings) {
        String s = "";
        StringBuilder output = new StringBuilder();

        StringBuilder query = new StringBuilder("https://funnregistreringsapiserver.azurewebsites.net/" + strings[0] + "?");
        for(int i = 1; i < strings.length; i++){
            if(i > 1){
                query.append("&");
            }
            query.append(strings[i]);
        }
        System.out.println("-----------------------------\n" + query.toString());
        try {
            URL urlen = new URL(query.toString());
            HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            //Gets the output from the server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((s = bufferedReader.readLine()) != null) {
                output.append(s);
            }
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
            }
            conn.disconnect();
            return output.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "io exception";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
    }
}

