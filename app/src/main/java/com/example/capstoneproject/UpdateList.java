package com.example.capstoneproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UpdateList extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public ArrayList<ToDoListItem> editItems = new ArrayList<ToDoListItem>();

    //nearly identical to CreateList.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create List");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //create and initialize list view variable and adapter
        ListView listView = findViewById(R.id.listView);
        UpdateList.TextAdapter adapter = new UpdateList.TextAdapter();

        //sends existing items list to adapter to edit
        adapter.setData(editItems);
        listView.setAdapter(adapter);

        //prompts user to add a new item to their list
        final Button newTaskButton = findViewById(R.id.newTaskButton);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creates a pop-up dialog that asks the user to input an item
                EditText taskInput = new EditText(UpdateList.this);
                taskInput.setSingleLine();
                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(UpdateList.this)
                        .setTitle("Add a New Item")
                        .setView(taskInput)
                        .setNeutralButton("Add Item", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //creates new to-do list item
                                ToDoListItem item = new ToDoListItem();
                                item.itemName = taskInput.getText().toString();
                                item.completed = 0;
                                dialogInterface.dismiss();
                                //creates a second pop-up that prompts user to select a difficulty for their list item
                                SeekBar seekBar = new SeekBar(UpdateList.this);
                                seekBar.setMax(4);
                                seekBar.setProgress(2);
                                androidx.appcompat.app.AlertDialog difDialog = new androidx.appcompat.app.AlertDialog.Builder(UpdateList.this)
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
                                editItems.add(item);
                                adapter.setData(editItems);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        context = this;

        //populates existing to-do list items on screen for user to see what they've already input
        for (int i = 0; i < ToDoMain.selectedList.listItemArray.length; i++) {
            editItems.add(ToDoMain.selectedList.listItemArray[i]);
        }
        adapter.setData(editItems);
        listView.setAdapter(adapter);

        //share switch on interface (prompts user to enter a username to share to-do list if switched)
        Switch shareListSwitch = (Switch) findViewById(R.id.shareListSwitch);
        LinearLayout shareListLayout = (LinearLayout) findViewById(R.id.shareListLayout);

        Button submit = (Button) findViewById(R.id.createListButton);
        submit.setText("Update");

        shareListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    shareListLayout.setVisibility(View.VISIBLE);
                else
                    shareListLayout.setVisibility(View.GONE);
            }
        });

        //populates existing fields (list title, group, shared to user)
        populateBlanks();
    }

    //user can exit update list page by hitting "back" arrow
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //populates existing fields (list title, group, shared to user)
    private void populateBlanks(){
        ToDoList list = ToDoMain.selectedList;

        ((EditText) findViewById(R.id.listTitle)).setText(list.title);

        Spinner spinner = ((Spinner) findViewById(R.id.groupListSpinner));
        if (list.group == "Personal")
            spinner.setSelection(1);
        if (list.group == "Other")
            spinner.setSelection(2);

        ((EditText) findViewById(R.id.shareListText)).setText(list.toUser);

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

    //adapter to display list items
    class TextAdapter extends BaseAdapter {
        public ArrayList<ToDoListItem> list = new ArrayList<ToDoListItem>();

        //called in first lines, but clears lists then adds items in "items" list to adapter
        void setData(ArrayList<ToDoListItem> newList) {
            //list.clear();
            list.addAll(newList);
            notifyDataSetChanged();
        }

        //total size of items list
        @Override
        public int getCount() {
            return list.size();
        }

        //gets item in list
        @Override
        public Object getItem(int position) {
            return null;
        }

        //gets item ID in list
        @Override
        public long getItemId(int position) {
            return 0;
        }

        //displays list items
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) UpdateList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listitem, viewGroup, false);
            TextView textView = rowView.findViewById(R.id.listItem);
            textView.setText(list.get(position).itemName);
            return (rowView);
        }

    }

    //API function call, pulls values input by user to send to database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createOnClick (View view){
        ToDoList newList = new ToDoList();
        newList.listId = ToDoMain.selectedList.listId;
        newList.userId = Login.userid;
        newList.title = ((EditText) findViewById(R.id.listTitle)).getText().toString();
        newList.group = ((Spinner) findViewById(R.id.groupListSpinner)).getSelectedItem().toString();
        newList.fromUser = Login.username;
        newList.toUser = ((EditText) findViewById(R.id.shareListText)).getText().toString();
        newList.completed = 0;
        newList.listItemArray = editItems.toArray(new ToDoListItem[editItems.size()]);

        if (newList.title.isEmpty()){
            popupMessage("Please enter a title", this);
        }
        else{
            Log.e("Update?", newList.title);
            new UpdateToDoList(this, this, view, newList).execute();
        }
    }
}

//sends updated list input from user to the endpoint "todolists/lists/update" so list is updated in database
class UpdateToDoList extends AsyncTask<String, Void, Void> {
    Context context;
    Activity activity;
    View view;
    ToDoList list;
    ApiResponse apiResponse = null;
    ProgressDialog progress;

    UpdateToDoList(Context ctx, Activity act, View vw, ToDoList list) {
        this.context = ctx;
        this.activity = act;
        this.view = vw;
        this.list = list;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Updating List...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(list);

        ApiManagement.PostNoReturn("todolist/lists/update", json);
        Menu.toDoListArray = (ToDoListArray) ApiManagement.PostWithReturn("todolist/lists", json, new ToDoListArray(), ToDoListArray.class);

        return  null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        Intent i = new Intent(context, ToDoMain.class);
        context.startActivity(i);
        activity.finish();
    }
}