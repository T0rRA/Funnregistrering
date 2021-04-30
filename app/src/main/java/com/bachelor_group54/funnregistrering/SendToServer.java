package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SendToServer {

    public static void getServerResponse(final String json, Context context){
        RequestQueue requestQueue;

        String URL="https://funnapi.azurewebsites.net/funn/RegistrerFunn";

        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json == null ? null : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Log.v("VOLLEY","Unsupported Encoding while trying to get the bytes");
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }

    public static void postRequest(Context context, final Map<String, String> params) {
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        String url="https://funnapi.azurewebsites.net/funn/RegistrerFunn";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    /*post_response_text.setText("Data 1 : " + jsonObject.getString("data_1_post")+"\n");
                    post_response_text.append("Data 2 : " + jsonObject.getString("data_2_post")+"\n");
                    post_response_text.append("Data 3 : " + jsonObject.getString("data_3_post")+"\n");
                    post_response_text.append("Data 4 : " + jsonObject.getString("data_4_post")+"\n");*/
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.v("VOLLEY","POST DATA : unable to Parse Json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("VOLLEY","Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}
