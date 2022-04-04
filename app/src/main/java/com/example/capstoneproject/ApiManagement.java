package com.example.capstoneproject;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiManagement {
    public static RequestQueue requestQueue = null;
    public static String baseUrl = "http://cmac23-001-site1.etempurl.com/api/database/";
    //public static String baseUrl = "http://162.242.2.65:55357/api/database/";

    public static void setupQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static JSONObject requestJson(Context context, String endpoint, int method, JSONObject json){
        if (requestQueue == null)
            setupQueue(context);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();

        Log.e("API ENDPOINT", baseUrl + endpoint);

        /*
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                method,
                baseUrl + endpoint,
                json,
                future,
                future

        );

         */

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                method,
                baseUrl + endpoint,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("NEW TEST", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("NEW TEST", error.toString());
                    }
                }

        );

        /*{@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type", "application/json");
            params.put("Accept", "application/json");
            return params;
        }};
        */

        Log.e("objectRequest", objectRequest.getBodyContentType());
        Log.e("objectRequest", objectRequest.getUrl());


        objectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(objectRequest);

        try{
            Log.e("IN TRY STATEMENT", "Future.get");
            //return future.get(30, TimeUnit.SECONDS);
        } catch (Exception e){
            Log.e("API EXCEPTION", e.toString());
            Log.e("API STACKTRACE", e.getMessage());
            Log.e("API STACKTRACE", e.getLocalizedMessage());
        }

        return null;
    }

    public static Object PostWithReturn(String json, Object object, Class cls){
        try {
            URL url = new URL(ApiManagement.baseUrl + "login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");

            Gson gson = new Gson();
            object = gson.fromJson(result, cls);

            in.close();
            conn.disconnect();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
