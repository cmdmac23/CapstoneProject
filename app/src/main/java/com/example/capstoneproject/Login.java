package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginClick (View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void forgotPasswordClick (View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void createAccountClick (View view){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}