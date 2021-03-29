package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetJSON extends AsyncTask<String, Void, String> {
    private TextView textView;

    public GetJSON(TextView textView) {
        this.textView = textView;
    }

    @Override
    //Takes the url as input, run it by using .execute("url")
    protected String doInBackground(String... urls) {
        StringBuilder retur = new StringBuilder();
        String s = "";
        StringBuilder output = new StringBuilder();

        try {
            URL urlen = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error Code:" + conn.getResponseCode());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("Output from server .... \n");
            while ((s = bufferedReader.readLine()) != null) {
                output.append(s);
            }
            if (output.toString().equals("Databasen er tom")) {
                return "";
            }
            try {
                //Runs trough the JSON an makes a string of it
                JSONArray mat = new JSONArray(output.toString());
                for (int i = 0; i < mat.length(); i++) {
                    JSONObject jsonobject = mat.getJSONObject(i);
                    for (int j = 1; j < urls.length; j++) {
                        retur.append(jsonobject.getString(urls[j]));
                        if (j < urls.length - 1) {
                            retur.append(",");
                        }
                    }
                    retur.append("\n");
                }
                return retur.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return output.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "getJson.execute trenger URL";
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong!";
        }
    }

    //Setts the textView's text to the output of the doInBackground method if the .execute method was called
    @Override
    protected void onPostExecute(String jsonString) {
        textView.setText(jsonString);
        System.out.println("----------------------------------------------------\n" + jsonString);
    }
}
