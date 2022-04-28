package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ToDoMain extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public static LinearLayout mainListLayout;
    public static ToDoList selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("To-Do");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mainListLayout = (LinearLayout) findViewById(R.id.listMainLinearLayout);
        mainListLayout.removeAllViews();

        //System.out.println(Menu.toDoListArray.listArray.length);
        populateScreen(mainListLayout);

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.to_do_nav);
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
        Intent intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateScreen(View view) {
        int length = Menu.toDoListArray.listArray.length;
        for (int i = 0; i < length; i++) {
            //getListMainLayout(this, view, Menu.toDoListArray.listArray[i], i);
            if (Menu.toDoListArray.listArray != null) {
                getListMainLayout(this, view, Menu.toDoListArray.listArray[i], i);
            }
        }
    }

    public static void getListMainLayout(Context ctx, View view, ToDoList list, int index) {
        LinearLayout subLayout = getListSubLayout(ctx, list, index);
        subLayout.setVisibility(View.GONE);

        LinearLayout row = new LinearLayout(ctx);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.isClickable();
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subLayout.getVisibility() == View.GONE) {
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
        titleText.setText(list.title);
        titleText.setTextSize(18);
        titleText.setPadding(15,0,0,0);

        row.addView(titleText);

        mainListLayout.addView(row);
        mainListLayout.addView(subLayout);
    }

    public static LinearLayout getListSubLayout(Context ctx, ToDoList list, int index) {
        LinearLayout menu = new LinearLayout(ctx);
        menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        menu.setOrientation(LinearLayout.VERTICAL);
        //menu.setId(2000000 + entry.eventId);
        menu.setBackgroundColor(ContextCompat.getColor(ctx, R.color.submenu_green));

        LinearLayout groupDifficult = new LinearLayout(ctx);
        groupDifficult.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        groupDifficult.setOrientation(LinearLayout.HORIZONTAL);

        TextView groupText = new TextView(ctx);
        groupText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
        groupText.setText("Group: " + list.group);
        groupText.setPadding(50, 0, 0, 0);

        groupDifficult.addView(groupText);

        int length = list.listItemArray.length;
        for (int i = 0; i < length; i++) {
            TextView listItemText = new TextView(ctx);
            listItemText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            listItemText.setText(list.listItemArray[i].itemName);
            listItemText.setTextSize(18);
            listItemText.setPadding(50, 0, 0, 0);
            menu.addView(listItemText);

            TextView difficultyText = new TextView(ctx);
            difficultyText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            difficultyText.setText("Difficulty: " + list.listItemArray[i].difficulty);
            menu.addView(difficultyText);

            CheckBox checbox = new CheckBox(ctx);
            checbox.setText("");
            checbox.setHeight(50);
            checbox.setWidth(70);
            checbox.setId(9000000 + index);
            if (list.listItemArray[i].completed == 1) {
                checbox.setChecked(true);
            }
            checbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    onCheckBoxChecked(buttonView.getId(), isChecked);
                }
            });
            menu.addView(checbox);
        }

        if(!list.toUser.isEmpty() && !list.toUser.equals(Login.username)){
            TextView sharedText = new TextView(ctx);
            sharedText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            sharedText.setText("Shared With: " + list.toUser);
            sharedText.setPadding(50,0,0,0);

            menu.addView(sharedText);
        }
        if(!list.fromUser.isEmpty() && list.toUser.equals(Login.username)){
            TextView sharedText = new TextView(ctx);
            sharedText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            sharedText.setText("Shared From: " + list.toUser);
            sharedText.setPadding(50,0,0,0);

            menu.addView(sharedText);
        }

        if (!list.toUser.equals(Login.username)){
            TextView editText = new TextView(ctx);
            editText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            editText.setTextSize(18);
            editText.setText("EDIT");
            editText.setPadding(0,0,50,0);
            editText.setGravity(Gravity.RIGHT);
            editText.isClickable();
            editText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    selectedList = list;
                    Intent i = new Intent(ctx, UpdateList.class);
                    ctx.startActivity(i);
                }
            });

            menu.addView(editText);
        }
        return (menu);
    }

    public static void onCheckBoxChecked(int index, boolean isChecked) {
        index = index - 9000000;
        ToDoListItem updateStatus = new ToDoListItem();
        updateStatus.listItemId = Menu.toDoListArray.listArray[index].listItemArray[index].listItemId;
        updateStatus.userId = Login.userid;

        int difficulty = updateStatus.difficulty;

        if (isChecked) {
            updateStatus.completed = 1;
            Menu.toDoListArray.listArray[index].listItemArray[index].completed = 1;
            Login.points = Login.points + difficulty;
        }
        else {
            updateStatus.completed = 0;
            Menu.toDoListArray.listArray[index].listItemArray[index].completed = 0;
            Login.points = Login.points + difficulty;

        }

        updateStatus.difficulty = Login.points;

        new UpdateListCompletionStatus(updateStatus).execute();

    }
}