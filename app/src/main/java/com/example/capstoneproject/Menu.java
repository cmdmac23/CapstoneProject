package com.example.capstoneproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public static Context context;
    public static LinearLayout linearLayout;
    // Arrays that store all of the user's created items
    public static PlannerEventArray plannerEntryArray;
    public static ToDoListArray toDoListArray;
    public static RewardArray rewardArray;
    public static TextView pointsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;
        linearLayout = (LinearLayout) findViewById(R.id.menuMainLinearLayout);
        // Preload all of the information about the user on login so that there is not separate loading screens later
        new PreloadPlannerEntries(this, this).execute();
        new PreloadToDoLists(this, this).execute();
        new PreloadRewards(this, this).execute();

        pointsText = (TextView) findViewById(R.id.menuPointsText);
        pointsText.setText("Total Points: " + Login.points);

        // Popup side menu
        // Made referencing https://material.io/components/navigation-drawer/android#modal-navigation-drawer
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

    // Controller for popup menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Fill in todays tasks, if any
    public static void populateScreen(View view, Context ctx){
        SimpleDateFormat readingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date entryDate = new Date();

        int length = plannerEntryArray.entryArray.length;

        for(int i = 0; i < length; i++)
        {
            try {
                entryDate = readingFormat.parse(plannerEntryArray.entryArray[i].dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DateUtils.isToday(entryDate.getTime())){
                linearLayout.addView(getEntryLayout(ctx, plannerEntryArray.entryArray[i].title, plannerEntryArray.entryArray[i].dateTime, plannerEntryArray.entryArray[i].completed, plannerEntryArray.entryArray[i].eventId, i));
            }
        }
    }

    // Create layout for each event row
    public static LinearLayout getEntryLayout(Context ctx, String title, String date, int completed, int eventid, int index){
        Date newDate = null;
        SimpleDateFormat readingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aa");

        try {
            newDate = readingFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout row = new LinearLayout(ctx);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        row.setOrientation(LinearLayout.HORIZONTAL);

        TextView titleText = new TextView(ctx);
        titleText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        titleText.setWidth(665);
        titleText.setText(title);
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
        checkbox.setId(index);
        if (completed == 1) {
            checkbox.setChecked(true);
        }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    onCheckboxChecked(buttonView.getId(), isChecked, checkbox);
                }
            }
        );


        row.addView(titleText);
        row.addView(dateText);
        row.addView(checkbox);

        return row;
    }

    // Marking event as complete with checkbox
    public static void onCheckboxChecked(int index, boolean isChecked, CheckBox checkBox){
        PlannerEvent updateStatus = new PlannerEvent();
        updateStatus.eventId = Menu.plannerEntryArray.entryArray[index].eventId;
        updateStatus.userId = Login.userid;

        int difficulty = Menu.plannerEntryArray.entryArray[index].difficulty;


        if (isChecked){
            updateStatus.completed = 1;
            Menu.plannerEntryArray.entryArray[index].completed = 1;
            Login.points = Login.points + difficulty;
        }
        else{
            if (Login.points < difficulty){
                checkBox.setChecked(true);
                String message = "You can not mark this item as incomplete as it would cause you to have negative points";
                Login.popupMessage(message, context);
                return;
            }
            else{
                updateStatus.completed = 0;
                Menu.plannerEntryArray.entryArray[index].completed = 0;
                Login.points = Login.points - difficulty;
            }
        }

        pointsText.setText("Total Points: " + Login.points);

        updateStatus.difficulty = Login.points;

        new UpdateCompletionStatus(updateStatus).execute();
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
        else if (apiResponse.entryArray == null){
            Log.e("ENTRY ARRAY", ".eventArray was null");
        }
        else{
            Menu.plannerEntryArray = apiResponse;
            Menu.populateScreen(null, context);
        }
    }
}

//uses endpoint "todolists/lists" to populate existing user lists when to-do list page is opened
class PreloadToDoLists extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    //View view;
    ToDoListArray apiResponse = null;
    ProgressDialog progress;

    PreloadToDoLists(Context ctx, Activity act) {
        this.context = ctx;
        this.activity = act;
        //this.view = vw;
    }

    protected Void doInBackground(String... urls){
        ToDoList input = new ToDoList();
        input.userId = Login.userid;

        Gson gson = new Gson();
        String json = gson.toJson(input);

        apiResponse = (ToDoListArray) ApiManagement.PostWithReturn("todolist/lists", json, new ToDoListArray(), ToDoListArray.class);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        //progress.hide();
        if (apiResponse == null){
            Log.e("TESTING ENTRY", "Response was null");
        }
        else if (apiResponse.listArray == null){
            Log.e("ENTRY ARRAY", ".eventArray was null");
        }
        else{
            Menu.toDoListArray = apiResponse;
            //Menu.populateScreen(null, context);
        }
    }
}

class PreloadRewards extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    RewardArray apiResponse = null;

    PreloadRewards(Context ctx, Activity act) {
        this.context = ctx;
        this.activity = act;
    }

    protected Void doInBackground(String... urls){
        RewardItem input = new RewardItem();
        input.userId = Login.userid;

        Gson gson = new Gson();
        String json = gson.toJson(input);

        apiResponse = (RewardArray) ApiManagement.PostWithReturn("rewards", json, new RewardArray(), RewardArray.class);
        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        //progress.hide();
        if (apiResponse == null){
            Log.e("TESTING ENTRY", "Response was null");
        }
        else if (apiResponse.rewardArray == null){
            Log.e("ENTRY ARRAY", ".eventArray was null");
        }
        else{
            Menu.rewardArray = apiResponse;
        }
    }
}

class UpdateCompletionStatus extends AsyncTask<String, Void, Void> {
    PlannerEvent updateInfo;

    UpdateCompletionStatus(PlannerEvent update) {
        this.updateInfo = update;
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(updateInfo);

        ApiManagement.PostNoReturn("planner/entries/complete", json);
        //ApiManagement.PostWithReturn("planner/entries", json, new PlannerEventArray(), PlannerEventArray.class);
        return  null;
    }
}

//indicates user has completed an item on their to-do list to database (sends item.completed = 1 to database)
class UpdateListCompletionStatus extends AsyncTask<String, Void, Void> {
    ToDoListItem updateItem;

    UpdateListCompletionStatus(ToDoListItem update) {this.updateItem = update;}

    protected Void doInBackground(String... urls) {
        Gson gson = new Gson();
        String json = gson.toJson(updateItem);

        ApiManagement.PostNoReturn("todolist/lists/complete", json);

        return null;
    }
}