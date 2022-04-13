package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.List;

public class Menu extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public static LinearLayout linearLayout;
    public static PlannerEventArray plannerEntryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);

        linearLayout = (LinearLayout) findViewById(R.id.menuMainLinearLayout);
        new PreloadPlannerEntries(this, this).execute();

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.menu_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        i = new Intent(context, Menu.class);
                        startActivity(i);
                        break;
                    case R.id.nav_planner:
                        i = new Intent(context, PlannerMain.class);
                        startActivity(i);
                        break;
                    case R.id.nav_todo:
                        i = new Intent(context, ToDoMain.class);
                        startActivity(i);
                        break;
                    case R.id.nav_rewards:
                        i = new Intent(context, RewardsMain.class);
                        startActivity(i);
                        break;
                    case R.id.nav_settings:
                        i = new Intent(context, Settings.class);
                        startActivity(i);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void populateScreen(View view, Context ctx){
        int length = plannerEntryArray.entryArray.length;


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView[] entries = new TextView[length];
        for(int i = 0; i < length; i++)
        {
            entries[i] = new TextView(ctx);
            entries[i].setTextSize(15);
            entries[i].setLayoutParams(lp);
            entries[i].setId(i);
            entries[i].setText("    " + plannerEntryArray.entryArray[i].title);
            linearLayout.addView(entries[i]);
        }
    }
}


class PreloadPlannerEntries extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    //View view;
    PlannerEventArray apiResponse = null;
    ProgressDialog progress;

    PreloadPlannerEntries(Context ctx, Activity act) {
        this.context = ctx;
        this.activity = act;
        //this.view = vw;
    }

    @Override
    protected void onPreExecute(){
        //progress = new ProgressDialog(context);
        //progress.setMessage("Loading all planner entries (remove this later)...");
        //progress.setIndeterminate(true);
        //progress.show();
    }

    protected Void doInBackground(String... urls){
        PlannerEvent input = new PlannerEvent();
        input.userId = Login.userid;

        Gson gson = new Gson();
        String json = gson.toJson(input);

        apiResponse = (PlannerEventArray)ApiManagement.PostWithReturn("planner/entries", json, new PlannerEventArray(), PlannerEventArray.class);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        //progress.hide();
        if (apiResponse == null){
            Log.e("TESTING ENTRY", "Response was null");
        }
        if (apiResponse.entryArray == null){
            Log.e("ENTRY ARRAY", ".eventArray was null");
        }
        else{
            Menu.plannerEntryArray = apiResponse;
            Menu.populateScreen(null, context);
        }
    }
}