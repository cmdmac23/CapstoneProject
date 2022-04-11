package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.navigation.NavigationView;

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
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);

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
    }

    private void updateDateLabel(EditText text){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        text.setText(dateFormat.format(defaultCalendar.getTime()));
        eventCalendar = defaultCalendar;
        defaultCalendar = Calendar.getInstance();
    }

    private void updateTimeLabel(EditText text){
        String myFormat="hh:mm a";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
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
}