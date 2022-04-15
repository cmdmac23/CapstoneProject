package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateInformation extends AppCompatActivity {
    public TextView title;
    public EditText box1;
    public EditText box2;
    public EditText box3;
    public Button submit;


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

    private void submitOnClick(){
        UpdateUserLogin newUser = new UpdateUserLogin();
        String endpoint = null;

        if(box1.getText().toString().isEmpty() || box2.getText().toString().isEmpty()){
            popupMessage("Please fill in all information", this);
        }

        if (Settings.selectedOption.equals("email")){
            newUser.newEmail = box1.getText().toString();
            newUser.password = box2.getText().toString();
            newUser.userId = Login.userid;
            endpoint = "email";
        }
        else if (Settings.selectedOption.equals("username")){
            newUser.newUsername = box1.getText().toString();
            newUser.password = box2.getText().toString();
            newUser.userId = Login.userid;
            endpoint = "username";
        }
        else{
            if(!box2.getText().toString().equals(box3.getText().toString())){
                popupMessage("The passwords you entered do not match", this);
                box2.setText("");
                box3.setText("");
            }
            else{
                newUser.password = box1.getText().toString();
                newUser.password = box2.getText().toString();
                newUser.userId = Login.userid;
                endpoint = "password";
            }
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
}