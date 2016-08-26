package com.example.kaique.portalhunters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import game.Singleton;
import game.SoundFx;

public class BattleSelectionActivity extends AppCompatActivity {

    private TextView BattleOpponentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_selection);

        BattleOpponentText = (TextView) findViewById(R.id.BattleOpponentText);
        Singleton.BATTLE_OPPONENT = Singleton.BATTLE_NONE;
        Singleton.BATTLE_MODE = Singleton.BATTLE_NONE;
    }

    public void clickCancelBattle(View view) {
        finish();
    }

    public void clickConfirmBattle(View view) {
        if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_NONE){
            // Must select an enemy
            Toast.makeText(this, "Please, select an opponent", Toast.LENGTH_SHORT).show();
        }
        else if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_PLAYER){
            // Battle Against Human
            Toast.makeText(this, "This battle mode will be implemented soon...", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent  = new Intent(BattleSelectionActivity.this, BattleActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void clickDragon(View view) {
        BattleOpponentText.setText("-- Human Player --");
        Singleton.BATTLE_OPPONENT = Singleton.BATTLE_OPPONENT_PLAYER;
    }

    public void clickRobot(View view) {
        BattleOpponentText.setText("-- Robot Player --");
        Singleton.BATTLE_OPPONENT = Singleton.BATTLE_OPPONENT_AI;
    }
}
