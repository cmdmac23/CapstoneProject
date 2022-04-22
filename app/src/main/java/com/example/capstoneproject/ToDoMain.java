package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ToDoMain extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Context context = this;
    public static ArrayList<ArrayList<String>> toDolists = new ArrayList<ArrayList<String>>();

//    public static LinearLayout mainListLayout;
//    public static ToDoList selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("To-Do");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        ArrayList<String> newList = new ArrayList<String>();
//        newList.add("item1");
//        newList.add("item2");
//        newList.add("item3");

//        mainListLayout = (LinearLayout) findViewById(R.id.listMainLinearLayout);
//        mainListLayout.removeAllViews();
//        populateScreen(mainListLayout, newList);

        final ListView listsView = findViewById(R.id.listsView);
        final ListAdapter adapter = new ListAdapter();

        adapter.setData(toDolists);
        listsView.setAdapter(adapter);

        final Button displayListButton = findViewById(R.id.displayListButton);

        displayListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskInput = new EditText(ToDoMain.this);
                toDolists.add((ArrayList<String>) taskInput.getText());
                adapter.setData(toDolists);
            }
        });

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

//    private void populateScreen(View view, ArrayList<String> newList) {
//        int length = Menu.tdListArray.toDoListArray.length;
//        for (int i = 0; i < length; i++) {
//            //getListMainLayout(this, view, Menu.plannerEntryArray.entryArray[i], i);
//        }
//    }

//    getListLayout(Context ctx, View view, ArrayList<String> testList, int index) {
//        LinearLayout subListLayout = getListSubLayout(ctx, testList);
//
//    }

    class ListAdapter extends BaseAdapter {
        public ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        void setData(ArrayList<ArrayList<String>> newList) {
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
            LayoutInflater inflater = (LayoutInflater) ToDoMain.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listitem, viewGroup, false);
            TextView textView = rowView.findViewById(R.id.listItem);
            textView.setText(list.get(position).toString());
            return(rowView);
        }
    }


    public void CreateClick (View view){
        Intent intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}