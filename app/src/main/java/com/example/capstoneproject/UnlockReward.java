package com.example.capstoneproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class UnlockReward extends AppCompatActivity {
    private ImageView plantImage;
    private static Spinner plantSelection;
    private Button unlockButton;
    public static TextView plantLabel;
    public TextView pointTotal;
    public static int pointCost;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_reward);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Unlock Rewards");
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;

        plantSelection = (Spinner) findViewById(R.id.rewardUnlockSpinner);
        plantImage = (ImageView) findViewById(R.id.rewardPlantImage);
        unlockButton = (Button) findViewById(R.id.rewardUnlockButton);
        plantLabel = (TextView) findViewById(R.id.rewardUnlockLabelText);
        pointTotal = (TextView) findViewById(R.id.rewardPointTotal);

        pointTotal.setText("You Have " + Login.points + " Points");

        plantSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        plantImage.setImageResource(R.mipmap.sprout_plant_foreground);
                        pointCost = 10;
                        plantLabel.setText("Sprout");
                        break;
                    case 1:
                        plantImage.setImageResource(R.mipmap.aloe_plant_foreground);
                        pointCost = 15;
                        plantLabel.setText("Aloe");
                        break;
                    case 2:
                        plantImage.setImageResource(R.mipmap.flower_plant_foreground);
                        pointCost = 20;
                        plantLabel.setText("Flowers");
                        break;
                    case 3:
                        plantImage.setImageResource(R.mipmap.cactus_plant_foreground);
                        pointCost = 25;
                        plantLabel.setText("Cactus");
                        break;
                    case 4:
                        plantImage.setImageResource(R.mipmap.lily_plant_foreground);
                        pointCost = 30;
                        plantLabel.setText("Lilies");
                        break;
                }

                unlockButton.setText("Unlock for " + pointCost + " Points");
                if (pointCost > Login.points){
                    unlockButton.setBackgroundColor(Color.rgb(169, 169, 169));
                }
                else{
                    unlockButton.setBackgroundColor(getResources().getColor(R.color.middle_green));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void unlockOnClick(View view){
        RewardItem rewardInfo = new RewardItem();

        rewardInfo.userId = Login.userid;
        rewardInfo.plantId = plantSelection.getSelectedItemPosition();
        rewardInfo.label = plantLabel.getText().toString();
        rewardInfo.points = pointCost;

        new UnlockRewardTask(context, rewardInfo).execute();
    }
}

class UnlockRewardTask extends AsyncTask<String, Void, Void> {
    RewardItem rewardItem;
    Context context;
    ProgressDialog progress;


    UnlockRewardTask(Context ctx, RewardItem rewardItem) {
        this.rewardItem = rewardItem;
        this.context = ctx;
    }

    @Override
    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage("Unlocking...");
        progress.setIndeterminate(true);
        progress.show();
    }

    protected Void doInBackground(String... urls){
        Gson gson = new Gson();
        String json = gson.toJson(rewardItem);

        Login.points = Login.points - rewardItem.points;

        ApiManagement.PostNoReturn("rewards/unlock", json);
        Menu.rewardArray = (RewardArray) ApiManagement.PostWithReturn("rewards", json, new RewardArray(), RewardArray.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void temp){
        progress.hide();
        Intent i = new Intent(context, RewardsMain.class);
        context.startActivity(i);
    }

}