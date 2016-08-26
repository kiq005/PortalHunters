package com.example.kaique.portalhunters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import game.SoundFx;

public class MainActivity extends AppCompatActivity {

    /*
     * MainActivity - This Activity is a splash screen.
     * It should create the game files, if is missing, or
     * load them. After that, the Activity automatically
     * changes to CharSelectionActivity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MAINACTIVITY","OnCreate");
        Log.d("MAINACTIVITY.ONCREATE", String.valueOf(1));



        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        loadFiles();
        new SplashThread().start();
    }

    public boolean loadFiles(){
        return true;
    }

    private class SplashThread extends Thread{

        public void run(){
            try{
                // The splash screen should stay on screen 3 seconds
                Thread.sleep(3000);
                // Change the Activity and destroy this one
                Intent intent  = new Intent(MainActivity.this, CharSelectionActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
            catch(Exception ex){
                Log.d("SPLASHTHREAD", "Error: " + ex.getMessage());
            }
        }

    }
}
