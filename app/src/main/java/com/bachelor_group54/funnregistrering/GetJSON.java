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
    private FragmentMineFunn fragmentMineFunn;

    public GetJSON(TextView textView) {
        this.textView = textView;
    }

    public GetJSON(FragmentMineFunn fragmentMineFunn) {
        this.fragmentMineFunn = fragmentMineFunn;
    }

    @Override
    //Takes the url as input, run it by using .execute("url", "field1", "field2") (Only need the filename on the server (GeneratePdf))
    protected String doInBackground(String... urls) {
        StringBuilder outPutString = new StringBuilder();
        String lineFromServer = "";
        StringBuilder stringFromServer = new StringBuilder();

        try {
            URL urlen = new URL("https://funnapi.azurewebsites.net/" + urls[0]);
            HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                System.err.println("Failed: HTTP error Code:" + conn.getResponseCode());
                return "Failed: HTTP error Code:" + conn.getResponseCode();
            }
            //Gets the string from the server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((lineFromServer = bufferedReader.readLine()) != null) {
                stringFromServer.append(lineFromServer);
            }
            //FIXME legg til ny test for tom database
            if (stringFromServer.toString().equals("Databasen er tom")) {
                return "";
            }

            try {
                //Runs trough the JSON and formats it
                JSONArray mat = new JSONArray(stringFromServer.toString());
                for (int i = 0; i < mat.length(); i++) { //Loops trough the JSON objects.
                    JSONObject jsonobject = mat.getJSONObject(i);
                    for (int j = 1; j < urls.length; j++) {
                        outPutString.append(jsonobject.getString(urls[j])); //Gets the fields from the JSONobject, the fields are defined in the in parameter for the method
                        if (j < urls.length - 1) { //Adds comma if the data is not the last inn the object
                            outPutString.append(",");
                        }
                    }
                    outPutString.append("\n");
                }
                return outPutString.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return stringFromServer.toString();
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
        if(textView != null) {
            textView.setText(jsonString);
        }

        if(fragmentMineFunn != null){
            ArrayList<Funn> findsList = new ArrayList<>();
            for(String line : jsonString.split("\n")){
                Funn find = new Funn();
                String[] fields = line.split(",");

                try {
                    find.setFunnID(Integer.parseInt(fields[0]));
                    //TODO legge til bilde
                    find.setDato(fields[2]);
                    find.setKommune(fields[3]);
                    find.setFylke(fields[4]);
                    find.setFunndybde(Double.parseDouble(fields[5]));
                    find.setGjenstandMerking(fields[6]);
                    find.setLatitude(Double.parseDouble(fields[7].split(" ")[0].replace("N", "")));
                    find.setLongitude(Double.parseDouble(fields[7].split(" ")[1].replace("W", "")));
                    find.setDatum(fields[8]);
                    find.setArealType(fields[9]);
                }catch (NumberFormatException e){
                    System.out.println("-----------------\nNumber format exception i GetJSON");
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("-----------------\nArray out of bounds i GetJSON");
                    e.printStackTrace();
                }

                findsList.add(find);
            }
            fragmentMineFunn.makeList(findsList);
        }
    }
}
