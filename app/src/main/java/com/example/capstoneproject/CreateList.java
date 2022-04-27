package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CreateList extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public static ArrayList<ToDoListItem> items = new ArrayList<ToDoListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("To-Do");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView listView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();

        adapter.setData(items);
        listView.setAdapter(adapter);

        final Button newTaskButton = findViewById(R.id.newTaskButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskInput = new EditText(CreateList.this);
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(CreateList.this)
                        .setTitle("Add a New Task")
                        .setMessage("What is your new task?")
                        .setView(taskInput)
                        .setPositiveButton("Add task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToDoListItem item = new ToDoListItem();
                                item.itemName = taskInput.getText().toString();
                                item.difficulty = 2;
                                item.completed = 0;
                                items.add(item);
                                adapter.setData(items);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        context = this;

        Switch shareSwitch = (Switch) findViewById(R.id.shareListSwitch);
        LinearLayout shareLayout = (LinearLayout) findViewById(R.id.shareLayout);
        shareSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    shareLayout.setVisibility(View.VISIBLE);
                } else {
                    shareLayout.setVisibility(View.GONE);
                }
            }
        });

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.create_list_nav);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void popupMessage(String message, Context context){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    class TextAdapter extends BaseAdapter {
        public ArrayList<ToDoListItem> list = new ArrayList<ToDoListItem>();

        void setData(ArrayList<ToDoListItem> newList) {
            list.clear();
            list.addAll(newList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) CreateList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listitem, viewGroup, false);
            TextView textView = rowView.findViewById(R.id.listItem);
            textView.setText(list.get(position).toString());
            return (rowView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createOnClick(View view) {
        ToDoList newList = new ToDoList();
        newList.userId = Login.userid;
        newList.title = ((EditText) findViewById(R.id.eventTitle)).getText().toString();
        newList.group = ((Spinner) findViewById(R.id.groupSpinner)).getSelectedItem().toString();
        newList.listItem = ((TextView) findViewById(R.id.listItem)).getText().toString();
        newList.fromUser = Login.username;
        newList.toUser = ((EditText) findViewById(R.id.shareText)).getText().toString();
        newList.completed = 0;
        newList.listItemArray = items.toArray(new ToDoListItem[items.size()]);
        if (newList.title.isEmpty()) {
            popupMessage("Please enter a title",this);
        }
        else {
            new CreateToDoList(this, this, view, newList).execute();
        }
    }
}

class CreateToDoList extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    ToDoList list;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    CreateToDoList(Context ctx, Activity act, View vw, ToDoList list) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.list = list;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Creating List...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        apiResponse = (ApiResponse)ApiManagement.PostWithReturn("todolist/lists/add", json, new ApiResponse(), ApiResponse.class);
        //Menu.toDoListArray = (ToDoListArray)ApiManagement.PostWithReturn("todolist/lists", json, new ToDoListArray(), ToDoListArray.class);

        return (null);
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        Intent i = new Intent(context, ToDoMain.class);
        context.startActivity(i);
        activity.finish();
    }
}