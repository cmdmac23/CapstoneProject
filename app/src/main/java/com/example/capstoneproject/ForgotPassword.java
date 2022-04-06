package com.example.capstoneproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void forgotSubmitClick(View view){
        EditText enteredEmail = findViewById(R.id.forgotEmail);

        if (enteredEmail.getText().toString().isEmpty()){
            popupMessage("Please enter your email address", this);
        }
        else{
            UserLogin user = new UserLogin();
            user.email = enteredEmail.getText().toString();

            new ForgotPasswordAction(this, this, view, user).execute();
        }
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

    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        return true;
    }
}

class ForgotPasswordAction extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    UserLogin user;
    ProgressDialog progress;

    ForgotPasswordAction(Context ctx, Activity act, View vw, UserLogin userLogin) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.user = userLogin;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Processing...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(user);

        ApiManagement.PostNoReturn("forgotpassword", json);

        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();

        CreateAccount.popupMessageNewScene("If the email you entered is associated with an account, an email with your password has been sent.", context);
    }
}