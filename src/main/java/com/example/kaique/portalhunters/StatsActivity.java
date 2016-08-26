package com.example.kaique.portalhunters;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import game.Singleton;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_stats_landscape);
        }
        else {
            setContentView(R.layout.activity_stats);
        }



        TextView StatsName = (TextView)findViewById(R.id.StatsName);
        TextView StatsHP = (TextView)findViewById(R.id.StatsHP);
        TextView StatsMP = (TextView)findViewById(R.id.StatsMP);
        TextView StatsCR = (TextView)findViewById(R.id.StatsCR);
        TextView StatsSTR = (TextView)findViewById(R.id.StatsSTR);
        TextView StatsMAG = (TextView)findViewById(R.id.StatsMAG);
        TextView StatsAGI = (TextView)findViewById(R.id.StatsAGI);
        TextView StatsRES = (TextView)findViewById(R.id.StatsRES);
        TextView StatsLCK = (TextView)findViewById(R.id.StatsLCK);

        String sex;
        if(Singleton.GAME_CHAR.getSex()=='m')
            sex = "Male";
        else
            sex = "Female";

        StatsName.setText(
                "Name: " + Singleton.GAME_CHAR.getName()
                + "\nSex: " + sex
                + "\nLevel: " + Singleton.GAME_CHAR.level
                + "\nXP: " + Singleton.GAME_CHAR.XP + "/999"
        );

        StatsHP.setText("HP: " + Singleton.GAME_CHAR.HP);
        StatsMP.setText("MP: " + Singleton.GAME_CHAR.MP);
        StatsCR.setText("CR: " + Singleton.GAME_CHAR.CR);
        StatsSTR.setText("STR: " + Singleton.GAME_CHAR.STR);
        StatsMAG.setText("MAG: " + Singleton.GAME_CHAR.MAG);
        StatsAGI.setText("AGI: " + Singleton.GAME_CHAR.AGI);
        StatsRES.setText("RES: " + Singleton.GAME_CHAR.RES);
        StatsLCK.setText("LCK: " + Singleton.GAME_CHAR.LCK);




    }
}
