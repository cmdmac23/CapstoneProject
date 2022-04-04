package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

        new AccountLogin(this).execute();

        /*
        if (TextUtils.isEmpty(username)){           // didn't enter username

        }
        else if (TextUtils.isEmpty(password)){      // didn't enter password

        }
        else{                                       // entered both
            UserLogin userLogin = new UserLogin();
            userLogin.username = username;
            userLogin.password = password;
            ApiResponse response = ApiFunctions.login(this, userLogin);

            if (response == null){
                Log.e("RESPONSE IS NULL", "text");
            }
            else{
                Log.e("RESPONSE CONTENT", response.text);
                if (response.text != "Successful login"){       // issue with login

                }
                else{
                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                }
            }



        }

         */
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
}

class AccountLogin extends AsyncTask<String, Void, Void> {
    Context context;
    ApiResponse apiResponse = null;

    AccountLogin(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected void onPreExecute(){
        // loading bar
    }

    protected Void doInBackground(String... urls){
        String json = "{\"username\":\"admin\",\"password\":\"password123\"}";
        ApiResponse response = null;

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
            response = gson.fromJson(result, ApiResponse.class);

            Log.e("TESTING", result);

            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiResponse = response;

        Log.e("ENDOFBG", "made it here");
        return null;
    }

    @Override
    protected void onPostExecute(Void temp){
        Log.e("results", apiResponse.text);
        if (apiResponse == null){
            Log.e("RESPONSE IS NULL", "text");
        }
        else{
            Log.e("RESPONSE CONTENT", apiResponse.text);
            if (apiResponse.text != apiResponse.text){       // issue with login        FIX THIS!!!!!!!!!!
                Log.e("ifStatement", "text");
            }
            else{
                Log.e("elseStatement", "text");
                //this.context = context.getApplicationContext();
                Intent i = new Intent(context, Menu.class);
                context.startActivity(i);
            }
        }
    }
}