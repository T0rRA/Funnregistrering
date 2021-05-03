package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private FragmentLogin fragmentLogin;

    public GetJSON(TextView textView) {
        this.textView = textView;
    }

    public GetJSON(FragmentMineFunn fragmentMineFunn) {
        this.fragmentMineFunn = fragmentMineFunn;
    }

    public GetJSON(FragmentLogin fragmentLogin) {
        this.fragmentLogin = fragmentLogin;
    }

    @Override
    //Takes the url as input, run it by using .execute("url", "field1", "field2") (Only need the filename on the server (GeneratePdf))
    protected String doInBackground(String... urls) {
        StringBuilder outPutString = new StringBuilder();
        String lineFromServer = "";
        StringBuilder stringFromServer = new StringBuilder();

        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();

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
                    jsonObjectArrayList.add(jsonobject);
                }
                makeList(jsonObjectArrayList);
                return outPutString.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return stringFromServer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Method for making the list in myFinds
    //Separate method as the image contains \n that messes up my split("\n") implementation in onPostExecute
    private void makeList(ArrayList<JSONObject> jsonObjectArrayList){
        if(fragmentMineFunn != null){
            final ArrayList<Funn> findsList = new ArrayList<>();
            try {
                for (JSONObject jsonObject: jsonObjectArrayList) {
                    Funn find = new Funn();

                    try {
                        find.setFunnID(Integer.parseInt(jsonObject.get("funnID").toString()));
                        find.setBilde(ImageSaver.makeBitmapFormBase64(jsonObject.get("image").toString()));
                        find.setDato(jsonObject.get("funndato").toString());
                        find.setKommune(jsonObject.get("kommune").toString());
                        find.setFylke(jsonObject.get("fylke").toString());
                        find.setFunndybde(Double.parseDouble(jsonObject.get("funndybde").toString()));
                        find.setGjenstandMerking(jsonObject.get("gjenstand_markert_med").toString());
                        find.setLatitude(Double.parseDouble((jsonObject.get("koordinat").toString()).split(" ")[0].replace("N", "")));
                        find.setLongitude(Double.parseDouble((jsonObject.get("koordinat").toString()).split(" ")[1].replace("W", "")));
                        find.setDatum(jsonObject.get("datum").toString());
                        find.setArealType(jsonObject.get("areal_type").toString());
                    } catch (NumberFormatException e) {
                        System.out.println("-----------------\nNumber format exception i GetJSON");
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("-----------------\nArray out of bounds i GetJSON");
                        e.printStackTrace();
                    }
                    findsList.add(find);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { //Must run the makeList method in the UI thread
                @Override
                public void run() {
                    fragmentMineFunn.makeList(findsList);
                }
            });

        }
    }

    //Setts the textView's text to the output of the doInBackground method if the .execute method was called
    @Override
    protected void onPostExecute(String jsonString) {
        if(jsonString == null || jsonString.equals("")){return;}
        if(textView != null) {
            textView.setText(jsonString);
        }

        if(fragmentLogin != null){
            User user = User.getInstance();
            try {
                //jsonString = jsonString.substring(0 , jsonString.indexOf("[")) + jsonString.substring(jsonString.indexOf("]")); //Removes the list of finds

                String[] fields = jsonString.split(",");

                for(int i = 0; i < fields.length; i++){
                    fields[i] = fields[i].split(":")[1].replace("\"", "");
                }

                user.setPostalCode(fields[0]);
                try {
                    user.setUserID(Integer.parseInt(fields[2]));
                }catch (NumberFormatException e){
                    user.setUserID(0);
                }
                user.setUsername(fields[3]);
                user.setName(fields[7]);
                user.setLastName(fields[8]);
                user.setAddress(fields[9]);
                user.setPhoneNum(fields[10]);
                user.setEmail(fields[11]);

            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("-----------------\nArray out of bounds i GetJSON");
                e.printStackTrace();
            }
        }
    }
}
