package com.example.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

public class RewardsMain extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public LinearLayout mainLayout;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rewards");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mainLayout = (LinearLayout) findViewById(R.id.rewardMainLinearLayout);
        mainLayout.removeAllViews();

        populateScreen(null);

        // Popup side menu
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.rewards_nav);
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

    // Side menu controller
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void unlockOnClick(View view){
        Intent i = new Intent(context, UnlockReward.class);
        startActivity(i);
    }

    // Fill in unlocked plants
    public void populateScreen(View view){
        int length = Menu.rewardArray.rewardArray.length;

        for(int i = 0; i < length; i++)
        {
            mainLayout.addView(getRewardLayout(this, Menu.rewardArray.rewardArray[i]));
            mainLayout.addView(getHorizontalLine(this));
        }
    }

    // Get each row for unlocked plants
    public static LinearLayout getRewardLayout(Context ctx, RewardItem rewardInfo){
        LinearLayout row = new LinearLayout(ctx);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        row.setOrientation(LinearLayout.HORIZONTAL);

        ImageView plantImage = new ImageView(ctx);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
        plantImage.setLayoutParams(params);
        plantImage.setPadding(10,10,10,10);

        // get correct plant image
        switch (rewardInfo.plantId){
            case 0:
                plantImage.setImageResource(R.mipmap.sprout_plant_foreground);
                break;
            case 1:
                plantImage.setImageResource(R.mipmap.aloe_plant_foreground);
                break;
            case 2:
                plantImage.setImageResource(R.mipmap.flower_plant_foreground);
                break;
            case 3:
                plantImage.setImageResource(R.mipmap.cactus_plant_foreground);
                break;
            case 4:
                plantImage.setImageResource(R.mipmap.lily_plant_foreground);
                break;
        }

        TextView labelText = new TextView(ctx);
        labelText.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        labelText.setWidth(540);
        labelText.setTextSize(24);
        labelText.setText(rewardInfo.label);
        labelText.setPadding(10, 0, 10, 0);
        ((TableRow.LayoutParams)labelText.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL;
        labelText.setPadding(0,0,9,0);

        row.addView(plantImage);
        row.addView(labelText);

        return row;
    }

    // add horizontal line between each plant row
    public View getHorizontalLine(Context ctx){
        View line = new View(ctx);
        line.setLayoutParams(new LinearLayout.LayoutParams(1000, 3));
        line.setBackgroundColor(getResources().getColor(R.color.middle_green));
        ((LinearLayout.LayoutParams)line.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;

        return line;
    }
}