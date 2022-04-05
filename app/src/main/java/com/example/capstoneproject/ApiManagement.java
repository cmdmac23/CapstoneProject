package com.example.capstoneproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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
    public static String username = "inFocus";
    public static String password = "g=Q4c$A!*-Emf7rC";
    public static String token = null;
    public static String baseUrl = "http://cmac23-001-site1.etempurl.com/api/database/";
    //public static String baseUrl = "http://162.242.2.65:55357/api/database/";

    public static String GetToken(){
        if (token == null){
            try {
                URL url = new URL("http://cmac23-001-site1.etempurl.com/api/authenticate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("username", username);
                conn.setRequestProperty("password", password);

                InputStream in = new BufferedInputStream(conn.getInputStream());
                String result = IOUtils.toString(in, "UTF-8");

                Gson gson = new Gson();
                Authortization auth = gson.fromJson(result, Authortization.class);

                token = auth.token;

                in.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    public static Object PostWithReturn(String endpoint, String json, Object object, Class cls){
        try {
            URL url = new URL(ApiManagement.baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "Bearer " + GetToken());

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


