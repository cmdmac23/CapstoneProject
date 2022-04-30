package com.example.capstoneproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class Login extends AppCompatActivity {
    public static UserLogin user = new UserLogin();
    // store these values for later API requests that require this information
    public static String username;
    public static int userid;
    public static int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
    }

    // Notification channel taken from https://developer.android.com/training/notify-user/build-notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channelid", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void loginClick (View view){
        EditText userUser = findViewById(R.id.loginUsername);
        EditText userPass = findViewById(R.id.loginPassword);

        String username = userUser.getText().toString().toLowerCase().trim();
        String password = userPass.getText().toString().trim();

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
                Login.points = apiResponse.points;
                Login.username = user.username;
                Intent i = new Intent(context, Menu.class);
                context.startActivity(i);
                activity.finish();
            }
        }
    }
}