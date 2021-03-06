package com.example.capstoneproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreatePlanner extends AppCompatActivity {
    public static Context context;
    public Calendar defaultCalendar = Calendar.getInstance();
    public Calendar eventCalendar = Calendar.getInstance();
    public Calendar reminderCalendar = Calendar.getInstance();
    public EditText eventDate;
    public EditText reminderDate;
    public EditText eventTime;
    public EditText reminderTime;
    public EditText lastClick;
    public boolean reminderSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_planner);

        context = this;

        // Set top bar information
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Entry");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Log.e("Default calendar date:", String.valueOf(defaultCalendar.get(Calendar.DAY_OF_MONTH)));
        Log.e("Event calendar date:", String.valueOf(eventCalendar.get(Calendar.DAY_OF_MONTH)));

        // Get all of the buttons set for later use
        Switch locationSwitch = (Switch) findViewById(R.id.locationSwitch);
        LinearLayout locationLayout = (LinearLayout) findViewById(R.id.locationLayout);
        Switch reminderSwitch = (Switch) findViewById(R.id.reminderSwitch);
        LinearLayout reminderLayout = (LinearLayout) findViewById(R.id.reminderLayout);
        Switch shareSwitch = (Switch) findViewById(R.id.shareSwitch);
        LinearLayout shareLayout = (LinearLayout) findViewById(R.id.shareLayout);

        // Get all of the date/time text boxes for later use
        eventDate = (EditText) findViewById(R.id.eventDateText);
        reminderDate = (EditText) findViewById(R.id.reminderDateText);
        eventTime = (EditText) findViewById(R.id.eventTimeText);
        reminderTime = (EditText) findViewById(R.id.reminderTimeText);

        // Date functions
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                defaultCalendar.set(Calendar.YEAR, year);
                defaultCalendar.set(Calendar.MONTH, month);
                defaultCalendar.set(Calendar.DAY_OF_MONTH, day);
                if (lastClick == eventDate)
                    updateDateLabel(lastClick);
                else
                    updateDateReminderLabel(lastClick);
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

        // Set a default date/time for all date/time blanks
        setDefaultValues();

        // Time functions
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                defaultCalendar.set(Calendar.HOUR_OF_DAY, hour);
                defaultCalendar.set(Calendar.MINUTE, minute);
                if (lastClick == eventTime)
                    updateTimeLabel(lastClick);
                else
                    updateTimeReminderLabel(lastClick);
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
                if (b){
                    reminderLayout.setVisibility(View.VISIBLE);
                    reminderSelected = true;
                }
                else{
                    reminderLayout.setVisibility(View.GONE);
                    reminderSelected = false;
                }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
        eventCalendar.set(defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH));
        defaultCalendar = Calendar.getInstance();
    }

    private void updateDateReminderLabel(EditText text){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        reminderCalendar.set(defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DAY_OF_MONTH));
        defaultCalendar = Calendar.getInstance();
    }

    private void updateTimeLabel(EditText text){
        String myFormat="hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        eventCalendar.set(eventCalendar.get(Calendar.YEAR), eventCalendar.get(Calendar.MONTH), eventCalendar.get(Calendar.DAY_OF_MONTH), defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE));
        defaultCalendar = Calendar.getInstance();
    }

    private void updateTimeReminderLabel(EditText text){
        String myFormat="hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        reminderCalendar.set(reminderCalendar.get(Calendar.YEAR), reminderCalendar.get(Calendar.MONTH), reminderCalendar.get(Calendar.DAY_OF_MONTH), defaultCalendar.get(Calendar.HOUR_OF_DAY), defaultCalendar.get(Calendar.MINUTE));
        defaultCalendar = Calendar.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        if (reminderSelected){
            newEvent.reminder = dateFormat.format(reminderCalendar.getTime());
            defaultCalendar = Calendar.getInstance();
            reminderCalendar.set(Calendar.MILLISECOND, 0);
            // Schedule the notification
            long delay = Duration.between(defaultCalendar.toInstant(), reminderCalendar.toInstant()).toMillis();
            WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(
                            new Data.Builder()
                                    .putString("BODY", newEvent.title + " is due on " + newEvent.dateTime)
                                    .build()
                    )
                    .build();
            WorkManager.getInstance(this).enqueue(uploadWorkRequest);
        }
        else
            newEvent.reminder = null;

        newEvent.difficulty = ((SeekBar) findViewById(R.id.difficultyBar)).getProgress() + 1;
        newEvent.fromUser = Login.username;
        newEvent.toUser = ((EditText) findViewById(R.id.shareText)).getText().toString();
        newEvent.completed = 0;

        if (newEvent.title.isEmpty()){
            Login.popupMessage("Please enter a title", this);
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