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
    private FragmentRegistrereBruker fragmentRegistrereBruker;
    private FragmentBruker fragmentBruker;

    public SetJSON() {
    }

    public SetJSON(Context context) {
        this.context = context;
    }

    public SetJSON(TextView textView) {
        this.textView = textView;
    }

    public SetJSON(String username) {
        this.username = username;
    }

    public SetJSON(Context context, FragmentMineFunn fragmentMineFunn) {
        this.context = context;
        this.fragmentMineFunn = fragmentMineFunn;
    }

    public SetJSON(Context context, FragmentRegistrereBruker fragmentRegistrereBruker) {
        this.fragmentRegistrereBruker = fragmentRegistrereBruker;
        this.context = context;
    }

    public SetJSON(Context context, FragmentBruker fragmentBruker) {
        this.fragmentBruker = fragmentBruker;
        this.context = context;
    }

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
            if(username != null){
                return "Feil passord brukernavn kombinasjon";
            }
            if(fragmentRegistrereBruker != null){
                return "Brukernavnet er opptatt";
            }
            ex.printStackTrace();
            return "io exception";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        s = s.replace("\"", "");
        if(textView != null) {
            textView.setText(s);
        }
        if(username != null){
            Context context = FragmentList.getInstance().getContext();
            if(s.equals("User was logged in")) { //Successful log in
                //Get user info and save it in the User object (using Singleton)
                GetJSON getJSON = new GetJSON(new FragmentLogin());
                getJSON.execute("Bruker/GetUser?brukernavn=" + username);
                s = "Logger inn";

                SharedPreferences sharedpreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Username", username);
                editor.putString("Password", User.getInstance().getPassword());
                editor.apply();

                FragmentList.getInstance().getMainActivity().closeFragment(); //Closes the login fragment
            }
            FragmentList.getInstance().getFragmentLogin().stopProgressBar();
        }

        if(fragmentRegistrereBruker != null){
            if(s.equals("Bruker er opprettet.")) { //Close create user fragment if the creation was successful
                FragmentList.getInstance().getMainActivity().closeFragment();
            }
        }

        if(fragmentBruker != null){
            //Get user info and save it in the User object (using Singleton)
            GetJSON getJSON = new GetJSON(new FragmentLogin());
            getJSON.execute("Bruker/GetUser?brukernavn=" + username);

            fragmentBruker.stopProgressBar();
        }

        if(context != null){
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }

        if(fragmentMineFunn != null){
            fragmentMineFunn.getFinds();
        }
    }
}
