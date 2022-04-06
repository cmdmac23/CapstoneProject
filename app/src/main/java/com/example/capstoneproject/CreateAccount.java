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
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void createAccountClick(View view){
        EditText enteredEmail = findViewById(R.id.createEmail);
        EditText enteredUser = findViewById(R.id.createUsername);
        EditText enteredPass = findViewById(R.id.createPassword);
        EditText enteredPass2 = findViewById(R.id.createPasswordVerify);

        if(enteredEmail.getText().toString().isEmpty())
            popupMessage("Please enter an email address", this);
        else if(enteredUser.getText().toString().isEmpty())
            popupMessage("Please enter a username", this);
        else if (enteredPass.getText().toString().isEmpty() || enteredPass2.getText().toString().isEmpty()){
            popupMessage("Please enter a password", this);
            enteredPass.setText("");
            enteredPass2.setText("");
        }
        else if (!enteredPass.getText().toString().equals(enteredPass2.getText().toString())){
            popupMessage("The passwords you entered do not match", this);
            enteredPass.setText("");
            enteredPass2.setText("");
        }
        else{
            String email = enteredEmail.getText().toString().toLowerCase().trim();
            String username = enteredUser.getText().toString().toLowerCase().trim();
            String password = enteredPass.getText().toString().trim();

            UserLogin user = new UserLogin();
            user.email = email;
            user.username = username;
            user.password = password;

            new AccountCreation(this, this, view, user).execute();

            enteredEmail.setText("");
            enteredUser.setText("");
            enteredPass.setText("");
            enteredPass2.setText("");
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

    public static void popupMessageNewScene(String message, Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
        return true;
    }
}

class AccountCreation extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    UserLogin user;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    AccountCreation(Context ctx, Activity act, View vw, UserLogin userLogin) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.user = userLogin;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Creating Account...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.e("JSON", json);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("createuser", json, new ApiResponse(), ApiResponse.class);
        //Log.e("CREATE RESP", apiResponse.text);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();

        if (apiResponse == null){
            Log.e("RESULTS", "response is null");
        }
        else{
            Log.e("RESULTS", apiResponse.text);

            if (!apiResponse.text.equals("Account created")){       // issue with creation
                Login.popupMessage(apiResponse.text, context);
            }
            else{
                CreateAccount.popupMessageNewScene("Account created. Please log in.", context);
            }
        }
    }
}