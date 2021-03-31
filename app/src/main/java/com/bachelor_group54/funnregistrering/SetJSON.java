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
    //Sends input to the server example use .execute(url, fieldName=fieldValue, field2Name=field2Value ...)
    protected String doInBackground(String... strings) {
        String serverOutputLine = "";
        StringBuilder serverOutput = new StringBuilder();

        //Makes the url
        StringBuilder query = new StringBuilder("https://funnregistreringsapiserver.azurewebsites.net/" + strings[0] + "?");
        for(int i = 1; i < strings.length; i++){
            if(i > 1){
                query.append("&");
            }
            query.append(strings[i]);
        }
        try {
            //Connects to the server
            URL urlen = new URL(query.toString());
            HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            //Gets the output from the server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((serverOutputLine = bufferedReader.readLine()) != null) {
                serverOutput.append(serverOutputLine);
            }
            if (conn.getResponseCode() != 200) { //Server error
                System.err.println("Failed: HTTP error Code:" + conn.getResponseCode());
                return "Failed: HTTP error Code:" + conn.getResponseCode();
            }
            conn.disconnect();
            return serverOutput.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "io exception";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(textView != null) {
            textView.setText(s);
        }
    }
}

