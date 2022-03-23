package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginClick (View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    DatabaseManagement.establishConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
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