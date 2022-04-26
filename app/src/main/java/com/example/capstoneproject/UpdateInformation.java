package com.example.capstoneproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class UpdateInformation extends AppCompatActivity {
    public static TextView title;
    public static EditText box1;
    public static EditText box2;
    public static EditText box3;
    public static Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        title = (TextView) findViewById(R.id.updateInfoTitle);
        box1 = (EditText) findViewById(R.id.updateInfoFirstBox);
        box2 = (EditText) findViewById(R.id.updateInfoSecondBox);
        box3 = (EditText) findViewById(R.id.updateInfoThirdBox);
        submit = (Button) findViewById(R.id.updateInfoButton);

        if (Settings.selectedOption.equals("email"))
            emailUpdate();
        else if (Settings.selectedOption.equals("username"))
            usernameUpdate();
        else
            passwordUpdate();
    }

    private void emailUpdate(){
        title.setText("Update Your Email");
        box1.setHint("New Email");
        box2.setHint("Current Password");
    }

    private void usernameUpdate(){
        title.setText("Change Your Username");
        box1.setHint("New Username");
        box2.setHint("Current Password");
    }

    private void passwordUpdate(){
        title.setText("Change Your Password");
        box1.setHint("Current Password");
        box1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        box2.setHint("New Password");
        box3.setHint("Confirm New Password");
        box3.setVisibility(View.VISIBLE);
    }

    public void submitOnClick(View view){
        UpdateUserLogin newUser = new UpdateUserLogin();

        if(box1.getText().toString().isEmpty() || box2.getText().toString().isEmpty()){
            Login.popupMessage("Please fill in all information", this);
        }

        if (Settings.selectedOption.equals("email")){
            newUser.newEmail = box1.getText().toString();
            newUser.password = box2.getText().toString();
            newUser.userId = Login.userid;
        }
        else if (Settings.selectedOption.equals("username")){
            newUser.newUsername = box1.getText().toString();
            newUser.password = box2.getText().toString();
            newUser.userId = Login.userid;
        }
        else{
            if(!box2.getText().toString().equals(box3.getText().toString())){
                Login.popupMessage("The passwords you entered do not match", this);
                box2.setText("");
                box3.setText("");
            }
            else{
                newUser.password = box1.getText().toString();
                newUser.newPassword = box2.getText().toString();
                newUser.userId = Login.userid;

                box1.setText("");
                box2.setText("");
                box3.setText("");

                new UpdateUserProfile(this, this, newUser, Settings.selectedOption).execute();
            }
        }
    }

    public static void popupMessageNewScene(String message, Context context, Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, Settings.class);
                context.startActivity(intent);
                activity.finish();
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


class UpdateUserProfile extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    UpdateUserLogin user;
    ApiResponse apiResponse = null;
    ProgressDialog progress;
    String endpoint;

    UpdateUserProfile(Context ctx, Activity act, UpdateUserLogin userInfo, String endpoint) {
        this.context = ctx;
        this.activity = act;
        this.user = userInfo;
        this.endpoint = endpoint;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Submitting information...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(user);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("user/update/" + endpoint, json, new ApiResponse(), ApiResponse.class);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        if (apiResponse == null){

        }
        else{
            if (!apiResponse.text.equals("Success")){       // issue with login
                Login.popupMessage(apiResponse.text, context);
            }
            else{
                UpdateInformation.popupMessageNewScene("Your " + endpoint + " has been successfully updated.", context, activity);
            }
        }
    }
}