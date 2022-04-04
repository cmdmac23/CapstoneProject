package com.example.capstoneproject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
public class ApiFunctions {
    public static ApiResponse login(Context context, UserLogin user){
        String jsonString = new Gson().toJson(user);
        Log.e("API JSON STRING", jsonString);

        try {
            JSONObject json = new JSONObject(jsonString);
            Log.e("API JSON OBJECT", json.toString());
            JSONObject result = ApiManagement.requestJson(context, "login", Request.Method.POST, json);
            Log.e("API RESPONSE JSON", result.toString());
            Gson gson = new Gson();
            ApiResponse response = gson.fromJson(String.valueOf(result),ApiResponse.class);

            return response;
        } catch (Exception e){
            Log.d("Login Exception", e.toString());
        }

        return null;
    }
}

 */

/*
class AccountLogin extends AsyncTask<String, Void, Void> {
    @Override
    protected void onPreExecute(){
        // loading bar
    }

    @Override
    protected Void doInBackground(String... urls){
        String json = "{\"username\":\"admin\",\"password\":\"password123\"}";

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
            ApiResponse response = gson.fromJson(result, ApiResponse.class);

            Log.e("TESTING", result);

            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Boolean result){

    }
}

 */
