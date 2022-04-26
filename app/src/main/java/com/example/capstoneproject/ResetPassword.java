package com.example.capstoneproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ResetPassword extends AppCompatActivity {
    public static EditText password1;
    public static EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password1 = findViewById(R.id.resetPasswordOne);
        password2 = findViewById(R.id.resetPasswordTwo);
    }

    public void submitOnClick(View view){
        if (password1.getText().toString().isEmpty() || password2.getText().toString().isEmpty()){
            Login.popupMessage("Please fill in all required fields", this);
        }
        else if (!password1.getText().toString().equals(password2.getText().toString())){
            Login.popupMessage("The passwords you entered do not match", this);
            password1.setText("");
            password2.setText("");
        }
        else {
            if (ForgotPassword.email == null){
                Login.popupMessage("There was an issue resetting your password", this);
            }
            else{
                UserLogin userInfo = new UserLogin();
                userInfo.password = password1.getText().toString();
                userInfo.email = ForgotPassword.email;

                new ResetPasswordRequest(this, this, view, userInfo).execute();
            }
        }
    }
}

class ResetPasswordRequest extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    UserLogin user;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    ResetPasswordRequest(Context ctx, Activity act, View vw, UserLogin userLogin) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.user = userLogin;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Resetting password...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.e("JSON", json);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("user/resetpassword", json, new ApiResponse(), ApiResponse.class);
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

            if (!apiResponse.text.equals("Password updated")){       // issue with update
                Login.popupMessage(apiResponse.text, context);
            }
            else{
                CreateAccount.popupMessageNewScene("Your password has been reset. Please log in with your new credentials.", context);
            }
        }
    }
}