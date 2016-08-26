package com.example.kaique.portalhunters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import game.Singleton;

public class GameMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_game_menu_landscape);
        }
        else {
            setContentView(R.layout.activity_game_menu);
        }

    }

    public void onResume(){
        super.onResume();

        TextView GameTextXP = (TextView)findViewById(R.id.GameTextXP);
        TextView GameTextName = (TextView)findViewById(R.id.GameTextName);
        ImageView GameHeroImage = (ImageView)findViewById(R.id.GameHeroImage);

        GameTextName.setText(Singleton.GAME_CHAR.getName());
        GameTextXP.setText(Singleton.GAME_CHAR.XP + "/999");

        if(Singleton.GAME_CHAR.getSex()=='m')
            GameHeroImage.setImageResource(R.mipmap.ic_hero);
        else if(Singleton.GAME_CHAR.getSex()=='f')
            GameHeroImage.setImageResource(R.mipmap.ic_heroine);
        else
            GameHeroImage.setImageResource(R.mipmap.ic_airship);
    }

    public void clickStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void clickStore(View view) {
        Toast.makeText(this, "This section will be implemented soon...", Toast.LENGTH_SHORT).show();
    }

    public void clickFight(View view) {
        Intent intent = new Intent(this, BattleSelectionActivity.class);
        startActivity(intent);
    }

    public void clickMap(View view) {
        Intent intent = new Intent(this, CastleSelectionActivity.class);
        startActivity(intent);
    }
}
