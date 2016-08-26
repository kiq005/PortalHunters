package com.example.kaique.portalhunters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaique.portalhunters.dao.CharacterDAO;

import java.util.ArrayList;

import game.GameChar;
import game.Singleton;
import game.SoundFx;

public class CharSelectionActivity extends AppCompatActivity {

    public static final int R_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_selection);

    }

    @Override
    public void onStart(){
        super.onStart();
        //TODO: Should load char

        TextView txtCharName = (TextView)findViewById(R.id.txtCharName);

        ArrayList<GameChar> list;

        CharacterDAO cDAO = new CharacterDAO(this);//CharacterDAO to get all characters
        list = cDAO.readAll();

        if(list!=null && list.size()>0){
            Singleton.GAME_CHAR = list.get(list.size()-1);
            txtCharName.setText(Singleton.GAME_CHAR.getName());
            //Log.d("MAINMENU.ONSTART", "Hero found...");
        }
        else{
            txtCharName.setText("Hero not Selected");
            //Log.d("MAINMENU.ONSTART", "Hero not selected...");
        }


    }

    public void clickPlay(View view){
        // TODO: the game should only begin when a character is selected

        if(Singleton.GAME_CHAR!=null){
            Intent intent = new Intent(this, GameMenuActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Please, select a character, or create a new one!", Toast.LENGTH_LONG).show();
        }

    }

    public void clickNewChar(View view){
        // TODO: Should return a new char
        Intent intent = new Intent(this, CharCreationActivity.class);
        startActivityForResult(intent, R_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == R_ID){
            if(resultCode==100){//Success

            }
        }

    }
}
