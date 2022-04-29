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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Space;
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
    public static ToDoListItem selectedItem;

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
                switch (menuItem.getItemId()) {
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

    public void CreateClick(View view) {
        Intent intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
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
                } else {
                    subLayout.setVisibility(View.GONE);
                }
            }
        });
        row.setBackgroundColor(Color.rgb(122, 159, 90));

        TextView titleText = new TextView(ctx);
        titleText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        titleText.setWidth(665);
        titleText.setText(list.title);
        titleText.setTextColor(ContextCompat.getColor(ctx, R.color.white));
        titleText.setTextSize(20);
        titleText.setPadding(15, 0, 0, 0);

        TextView groupText = new TextView(ctx);
        groupText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
        groupText.setText(list.group);
        groupText.setTextColor(ContextCompat.getColor(ctx, R.color.white));
        groupText.setTextSize(14);
        groupText.setWidth(200);


        row.addView(titleText);
        row.addView(groupText);

        Space listSpace = new Space(ctx);
        listSpace.setLayoutParams(new LinearLayout.LayoutParams(1500, 2));
        ((LinearLayout.LayoutParams)listSpace.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;

        View listLine = new View(ctx);
        listLine.setLayoutParams(new LinearLayout.LayoutParams(1200, 2));
        listLine.setBackgroundColor(ContextCompat.getColor(ctx, R.color.darkest_green));
        ((LinearLayout.LayoutParams)listLine.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;


        mainListLayout.addView(row);
        mainListLayout.addView(listSpace);
        mainListLayout.addView(subLayout);
    }

    public static LinearLayout getListSubLayout(Context ctx, ToDoList list, int index) {
        LinearLayout menu = new LinearLayout(ctx);
        menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        menu.setOrientation(LinearLayout.VERTICAL);
        //menu.setId(2000000 + entry.eventId);
        menu.setBackgroundColor(ContextCompat.getColor(ctx, R.color.white));

        LinearLayout labelItems = new LinearLayout(ctx);
        labelItems.getHeight();
        labelItems.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        labelItems.setBackgroundColor(ContextCompat.getColor(ctx, R.color.submenu_green));
        labelItems.setOrientation(LinearLayout.HORIZONTAL);

        TextView itemView = new TextView(ctx);
        itemView.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 7));
        itemView.setPadding(5, 0, 0, 0);
        itemView.setText("Items");
        itemView.setTextSize(18);
        itemView.setWidth(665);
        itemView.setPadding(15, 0, 0, 0);
        labelItems.addView(itemView);

        TextView difView = new TextView(ctx);
        difView.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 4));
        difView.setText("Difficulty");
        difView.setTextSize(18);
        difView.setWidth(450);
        labelItems.addView(difView);

        CheckBox fakeCheck = new CheckBox(ctx);
        fakeCheck.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        fakeCheck.setText("");
        fakeCheck.setChecked(true);
        fakeCheck.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        fakeCheck.setWidth(70);
        labelItems.addView(fakeCheck);

        View dividerLine = new View(ctx);
        dividerLine.setLayoutParams(new LinearLayout.LayoutParams(1200, 2));
        dividerLine.setBackgroundColor(ContextCompat.getColor(ctx, R.color.light_gold));
        ((LinearLayout.LayoutParams)dividerLine.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;

        menu.addView(labelItems);
        menu.addView(dividerLine);

        int length = list.listItemArray.length;
        for (int i = 0; i < length; i++) {

            LinearLayout groupItems = new LinearLayout(ctx);
            groupItems.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            groupItems.setOrientation(LinearLayout.HORIZONTAL);

            TextView listItemText = new TextView(ctx);
            listItemText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 7));
            listItemText.setText(list.listItemArray[i].itemName);
            listItemText.setTextSize(18);
            listItemText.setWidth(665);
            listItemText.setPadding(15, 0, 0, 0);
            //menu.addView(listItemText);
            groupItems.addView(listItemText);

            TextView difficultyText = new TextView(ctx);
            difficultyText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            difficultyText.setText("" + list.listItemArray[i].difficulty);
            difficultyText.setWidth(320);
            listItemText.setPadding(15, 0, 0, 0);
            //menu.addView(difficultyText);
            groupItems.addView(difficultyText);

            CheckBox checbox = new CheckBox(ctx);
            checbox.setText("");
            checbox.setHeight(50);
            checbox.setWidth(70);
            checbox.setId(9000000 + index);
            if (list.listItemArray[i].completed == 1) {
                checbox.setChecked(true);
            }
            int finalI = i;
            checbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckBoxChecked(buttonView.getId(), isChecked, finalI);
                }
            });
            //menu.addView(checbox);
            groupItems.addView(checbox);

            menu.addView(groupItems);
            View line = new View(ctx);
            line.setLayoutParams(new LinearLayout.LayoutParams(1200, 2));
            line.setBackgroundColor(Color.rgb(85, 133, 43));
            ((LinearLayout.LayoutParams)line.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
            menu.addView(line);
        }

        if (!list.toUser.isEmpty() && !list.toUser.equals(Login.username)) {
            TextView sharedText = new TextView(ctx);
            sharedText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            sharedText.setText("Shared With: " + list.toUser);
            sharedText.setPadding(50, 0, 0, 0);

            menu.addView(sharedText);
        }
        if (!list.fromUser.isEmpty() && list.toUser.equals(Login.username)) {
            TextView sharedText = new TextView(ctx);
            sharedText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            sharedText.setText("Shared From: " + list.toUser);
            sharedText.setPadding(50, 0, 0, 0);

            menu.addView(sharedText);
        }

        if (!list.toUser.equals(Login.username)) {
            TextView editText = new TextView(ctx);
            editText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            editText.setTextSize(18);
            editText.setText("EDIT");
            editText.setPadding(0, 0, 50, 0);
            editText.setGravity(Gravity.RIGHT);
            editText.isClickable();
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedList = list;
                    Intent i = new Intent(ctx, UpdateList.class);
                    ctx.startActivity(i);
                }
            });

            menu.addView(editText);
        }
        return (menu);
    }

    public static void onCheckBoxChecked(int index, boolean isChecked, int indexsub) {
        index = index - 9000000;
        //for (int item = 0; item < Menu.toDoListArray.listArray[index].listItemArray.length; item++) {
        ToDoListItem updateStatus = new ToDoListItem();
        updateStatus.listItemId = Menu.toDoListArray.listArray[index].listItemArray[indexsub].listItemId;
        updateStatus.userId = Login.userid;

        int difficulty = updateStatus.difficulty = Menu.toDoListArray.listArray[index].listItemArray[indexsub].difficulty;

        if (isChecked) {
            updateStatus.completed = 1;
            Menu.toDoListArray.listArray[index].listItemArray[indexsub].completed = 1;
            Login.points = Login.points + difficulty;
        } else {
            updateStatus.completed = 0;
            Menu.toDoListArray.listArray[index].listItemArray[indexsub].completed = 0;
            Login.points = Login.points + difficulty;

        }

        updateStatus.difficulty = Login.points;

        new UpdateListCompletionStatus(updateStatus).execute();
        //}
    }
}