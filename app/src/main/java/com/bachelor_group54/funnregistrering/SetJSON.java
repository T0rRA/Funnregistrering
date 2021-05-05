package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class SetJSON extends AsyncTask<String, Void, String> {
    private TextView textView;
    private Context context;
    private String username;
    private FragmentMineFunn fragmentMineFunn;

    public SetJSON() {
    }

    public SetJSON(Context context) {
        this.context = context;
    }

    public SetJSON(TextView textView) {
        this.textView = textView;
    }

    public SetJSON(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    public SetJSON(Context context, FragmentMineFunn fragmentMineFunn) {
        this.context = context;
        this.fragmentMineFunn = fragmentMineFunn;
    }

    //FIXME endre denne til å passe med serveren
    @Override
    //Sends input to the server example use .execute(url, fieldName=fieldValue, field2Name=field2Value ...)
    protected String doInBackground(String... strings) {
        String serverOutputLine = "";
        StringBuilder serverOutput = new StringBuilder();

        //Makes the url
        StringBuilder query = new StringBuilder("https://funnapi.azurewebsites.net/" + strings[0] + "?");
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
        }else if(context != null && username != null){
            //Saves the username to shared preference if login was successful else overwrite it
            SharedPreferences sharedpreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if(s.equals("true")) {
                editor.putString("username", username);
            }else {
                editor.putString("username", "");
            }
            editor.apply();
        }

        if(context != null){
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }

        if(fragmentMineFunn != null){
            fragmentMineFunn.getFinds();
        }
    }
}

