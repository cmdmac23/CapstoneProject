package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannerMain extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public static LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Planner");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mainLayout = (LinearLayout) findViewById(R.id.plannerMainLinearLayout);
        populateScreen(mainLayout);

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.planner_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        i = new Intent(context, Menu.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_planner:
                        i = new Intent(context, PlannerMain.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_todo:
                        i = new Intent(context, ToDoMain.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_rewards:
                        i = new Intent(context, RewardsMain.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_settings:
                        i = new Intent(context, Settings.class);
                        startActivity(i);
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void CreateClick (View view){
        Intent intent = new Intent(this, CreatePlanner.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateScreen(View view){
        int length = Menu.plannerEntryArray.entryArray.length;

        for(int i = 0; i < length; i++)
        {
            getEntryMainLayout(this, view, Menu.plannerEntryArray.entryArray[i]);
        }
    }

    public static void getEntryMainLayout(Context ctx, View view, PlannerEvent entry){
        Date newDate = null;
        SimpleDateFormat readingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss aa");

        try {
            newDate = readingFormat.parse(entry.dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout subLayout = getEntrySubLayout(ctx, entry);
        subLayout.setVisibility(View.GONE);

        LinearLayout row = new LinearLayout(ctx);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setId(4000000 + entry.eventId);
        row.isClickable();
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(subLayout.getVisibility() == View.GONE){
                    subLayout.setVisibility(View.VISIBLE);
                }
                else {
                    subLayout.setVisibility(View.GONE);
                }
            }
        });
        row.setBackgroundColor(ContextCompat.getColor(ctx, R.color.lightest_green));

        TextView titleText = new TextView(ctx);
        titleText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        titleText.setWidth(665);
        titleText.setText(entry.title);
        titleText.setTextSize(18);
        titleText.setPadding(15,0,0,0);

        TextView dateText = new TextView(ctx);
        dateText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        dateText.setWidth(320);
        dateText.setText(outputFormat.format(newDate));
        dateText.setTextSize(18);

        CheckBox checkbox = new CheckBox(ctx);
        checkbox.setText("");
        checkbox.setHeight(50);
        checkbox.setWidth(70);
        checkbox.setId(3000000 + entry.eventId);
        if (entry.completed == 1) {
            checkbox.setChecked(true);
        }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                onCheckboxChecked(buttonView.getId(), isChecked);
            }
        });

        row.addView(titleText);
        row.addView(dateText);
        row.addView(checkbox);

        mainLayout.addView(row);
        mainLayout.addView(subLayout);
    }

    public static LinearLayout getEntrySubLayout(Context ctx, PlannerEvent entry){
        LinearLayout menu = new LinearLayout(ctx);
        menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        menu.setOrientation(LinearLayout.VERTICAL);
        menu.setId(2000000 + entry.eventId);
        menu.setBackgroundColor(ContextCompat.getColor(ctx, R.color.submenu_green));

        TextView descriptionText = new TextView(ctx);
        descriptionText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        descriptionText.setText(entry.description);
        descriptionText.setTextSize(18);
        descriptionText.setPadding(50,0,0,0);

        menu.addView(descriptionText);

        LinearLayout groupDifficult = new LinearLayout(ctx);
        groupDifficult.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        groupDifficult.setOrientation(LinearLayout.HORIZONTAL);

        TextView groupText = new TextView(ctx);
        groupText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
        groupText.setText("Group: " + entry.group);
        groupText.setPadding(50,0,0,0);

        TextView difficultyText = new TextView(ctx);
        difficultyText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
        difficultyText.setText("Difficulty: " + entry.difficulty);

        groupDifficult.addView(groupText);
        groupDifficult.addView(difficultyText);
        menu.addView(groupDifficult);

        if (!entry.location.isEmpty()){
            TextView locationText = new TextView(ctx);
            locationText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            locationText.setText("Location: " + entry.location);
            locationText.setPadding(50,0,0,0);

            menu.addView(locationText);
        }

        if (!entry.reminder.isEmpty()){
            TextView reminderText = new TextView(ctx);
            reminderText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            reminderText.setText("Reminder Set For: " + entry.reminder);
            reminderText.setPadding(50,0,0,0);

            menu.addView(reminderText);
        }

        if(!entry.toUser.isEmpty()){
            TextView sharedText = new TextView(ctx);
            sharedText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            sharedText.setText("Shared With: " + entry.toUser);
            sharedText.setPadding(50,0,0,0);

            menu.addView(sharedText);
        }

        return menu;
    }

    public static void onCheckboxChecked(int eventid, boolean isChecked){
        PlannerEvent updateStatus = new PlannerEvent();
        updateStatus.eventId = eventid;

        if (isChecked){
            updateStatus.completed = 1;
        }
        else{
            updateStatus.completed = 0;
        }

        new UpdateCompletionStatus(updateStatus).execute();
    }

}