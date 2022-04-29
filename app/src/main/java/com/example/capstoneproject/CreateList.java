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
import android.widget.AdapterView;
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
    public ArrayList<ToDoListItem> items = new ArrayList<ToDoListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create List");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.listView);
        TextAdapter adapter = new TextAdapter();

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
                        .setNeutralButton("Add task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToDoListItem item = new ToDoListItem();
                                item.itemName = taskInput.getText().toString();
                                item.completed = 0;
                                dialogInterface.dismiss();
                                SeekBar seekBar = new SeekBar(CreateList.this);
                                seekBar.setMax(4);
                                seekBar.setProgress(2);
                                AlertDialog difDialog = new AlertDialog.Builder(CreateList.this)
                                        .setTitle("Difficulty")
                                        .setView(seekBar)
                                        .setPositiveButton("Add Difficulty", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                item.difficulty = seekBar.getProgress() + 1;
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .create();
                                difDialog.show();
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
        //SeekBar seekbar = new SeekBar(CreateList.this);

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
            textView.setText(list.get(position).itemName);
            return (rowView);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createOnClick(View view) {
        ToDoList newList = new ToDoList();
        newList.userId = Login.userid;
        newList.title = ((EditText) findViewById(R.id.listTitle)).getText().toString();
        newList.group = ((Spinner) findViewById(R.id.groupListSpinner)).getSelectedItem().toString();
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
        Menu.toDoListArray = (ToDoListArray)ApiManagement.PostWithReturn("todolist/lists", json, new ToDoListArray(), ToDoListArray.class);

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