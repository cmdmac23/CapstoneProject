package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class CreatePlanner extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;

    public Calendar defaultCalendar = Calendar.getInstance();
    public Calendar eventCalendar = Calendar.getInstance();
    public Calendar reminderCalendar = Calendar.getInstance();
    public EditText eventDate;
    public EditText reminderDate;
    public EditText eventTime;
    public EditText reminderTime;
    public EditText lastClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_planner);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Entry");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Switch locationSwitch = (Switch) findViewById(R.id.locationSwitch);
        LinearLayout locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
        Switch reminderSwitch = (Switch) findViewById(R.id.reminderSwitch);
        LinearLayout reminderLayout = (LinearLayout) findViewById(R.id.reminderLayout);
        Switch shareSwitch = (Switch) findViewById(R.id.shareSwitch);
        LinearLayout shareLayout = (LinearLayout) findViewById(R.id.shareLayout);

        eventDate = (EditText) findViewById(R.id.eventDateText);
        reminderDate = (EditText) findViewById(R.id.reminderDateText);
        eventTime = (EditText) findViewById(R.id.eventTimeText);
        reminderTime = (EditText) findViewById(R.id.reminderTimeText);

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.create_planner_nav);
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

        // Date functions
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                defaultCalendar.set(Calendar.YEAR, year);
                defaultCalendar.set(Calendar.MONTH, month);
                defaultCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateLabel(lastClick);
            }
        };
        eventDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lastClick = eventDate;
                new DatePickerDialog(CreatePlanner.this, date, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        reminderDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lastClick = reminderDate;
                new DatePickerDialog(CreatePlanner.this, date, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setDefaultValues();

        // Time functions
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                defaultCalendar.set(Calendar.HOUR_OF_DAY, hour);
                defaultCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel(lastClick);
            }
        };
        eventTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lastClick = eventTime;
                new TimePickerDialog(CreatePlanner.this, time, defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE), false).show();
            }
        });
        reminderTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lastClick = reminderTime;
                new TimePickerDialog(CreatePlanner.this, time, defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE), false).show();
            }
        });

        // Toggle switch functions
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    locationLayout.setVisibility(View.VISIBLE);
                else
                    locationLayout.setVisibility(View.GONE);
            }
        });
        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    reminderLayout.setVisibility(View.VISIBLE);
                else
                    reminderLayout.setVisibility(View.GONE);
            }
        });
        shareSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    shareLayout.setVisibility(View.VISIBLE);
                else
                    shareLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setDefaultValues(){
        updateDateLabel(eventDate);
        updateDateLabel(reminderDate);
        updateTimeLabel(eventTime);
        updateTimeLabel(reminderTime);
    }

    private void updateDateLabel(EditText text){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        eventCalendar = defaultCalendar;
        defaultCalendar = Calendar.getInstance();
    }

    private void updateTimeLabel(EditText text){
        String myFormat="hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        reminderCalendar = defaultCalendar;
        defaultCalendar = Calendar.getInstance();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void createOnClick (View view){
        String myFormat="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

        PlannerEvent newEvent = new PlannerEvent();
        newEvent.userId = Login.userid;
        newEvent.title = ((EditText) findViewById(R.id.eventTitle)).getText().toString();
        newEvent.description = ((EditText) findViewById(R.id.eventDescription)).getText().toString();
        newEvent.group = ((Spinner) findViewById(R.id.groupSpinner)).getSelectedItem().toString();
        newEvent.dateTime = dateFormat.format(eventCalendar.getTime());
        newEvent.location = ((EditText) findViewById(R.id.locationText)).getText().toString();
        newEvent.reminder = dateFormat.format(reminderCalendar.getTime());
        newEvent.difficulty = ((SeekBar) findViewById(R.id.difficultyBar)).getProgress() + 1;
        newEvent.fromUser = Login.username;
        newEvent.toUser = ((EditText) findViewById(R.id.shareText)).getText().toString();
        newEvent.completed = 0;

        if (newEvent.title.isEmpty()){
            popupMessage("Please enter a title", this);
        }
        else{
            new CreatePlannerEntry(this, this, view, newEvent).execute();
        }
    }
}

class CreatePlannerEntry extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    PlannerEvent event;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    CreatePlannerEntry(Context ctx, Activity act, View vw, PlannerEvent event) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.event = event;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Creating Entry...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(event);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("planner/entries/add", json, new ApiResponse(), ApiResponse.class);
        Menu.plannerEntryArray = (PlannerEventArray)ApiManagement.PostWithReturn("planner/entries", json, new PlannerEventArray(), PlannerEventArray.class);

        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        Intent i = new Intent(context, PlannerMain.class);
        context.startActivity(i);
        activity.finish();
    }
}