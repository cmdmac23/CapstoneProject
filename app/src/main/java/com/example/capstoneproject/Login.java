package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    public static int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void menuInstance(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void loginClick (View view){
        EditText userUser = findViewById(R.id.loginUsername);
        EditText userPass = findViewById(R.id.loginPassword);

        String username = userUser.getText().toString().toLowerCase().trim();
        String password = userPass.getText().toString().trim();

        UserLogin user = new UserLogin();
        user.username = username;
        user.password = password;

        if (username.isEmpty()){
            popupMessage("Please enter your username", this);
        }
        else if (password.isEmpty()){
            popupMessage("Please enter your password", this);
        }
        else{
            new AccountLogin(this, this, view, user).execute();

            userPass.setText("");
            userUser.setText("");
        }

    }

    public void loginResult(ApiResponse response){

    }

    public void forgotPasswordClick (View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void createAccountClick (View view){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    public static void popupMessage(String message, Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

class AccountLogin extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    UserLogin user;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    AccountLogin(Context ctx, Activity act, View vw, UserLogin userLogin) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.user = userLogin;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Logging you in...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(user);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("login", json, new ApiResponse(), ApiResponse.class);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        if (apiResponse == null){

        }
        else{
            if (!apiResponse.text.equals("Successful login")){       // issue with login
                Login.popupMessage(apiResponse.text, context);
            }
            else{
                Login.userid = apiResponse.userid;
                Intent i = new Intent(context, Menu.class);
                context.startActivity(i);
                activity.finish();
            }
        }
    }
}