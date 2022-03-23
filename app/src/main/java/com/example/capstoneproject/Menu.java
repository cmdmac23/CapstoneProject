package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
    }

    public void plannerClick (View view){
        Intent intent = new Intent(this, PlannerMain.class);
        startActivity(intent);
    }

    public void toDoClick (View view){
        Intent intent = new Intent(this, ToDoMain.class);
        startActivity(intent);
    }

    public void rewardsClick (View view){
        Intent intent = new Intent(this, RewardsMain.class);
        startActivity(intent);
    }

    public void settingsClick (View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}