package com.example.kaique.portalhunters;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaique.portalhunters.dao.CharacterDAO;

import java.util.Random;

import game.Singleton;

public class BattleEL extends AppCompatActivity {

    private final int EARTH = 3001;
    private final int FIRE = 3002;
    private final int FOREST = 3003;
    private final int ICE = 3004;
    private final int THUNDER = 3005;
    private final int WATER = 3006;
    private final int WIND = 3007;

    private final int[][] damageMatrix = {
            {0,1,-2,0,2,-1,0},
            {0,0,2,-1,0,-2,1},
            {2,-2,-1,0,0,1,0},
            {0,-1,0,1,-2,0,2},
            {-2,0,1,2,0,0,-1},
            {1,2,0,0,-1,0,-2},
            {-1,0,0,-2,1,2,0}
    };

    public boolean wait = false;
    ImageView imgPlayerSelection;
    ImageView imgEnemySelection;
    TextView txtBattleState;

    TextView txtPlayerName;
    TextView txtEnemyName;
    TextView txtPlayerHP;
    TextView txtEnemyHP;

    int playerLife;
    int enemyLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_battle_el_landscape);
        }
        else {
            setContentView(R.layout.activity_battle_el);
        }


        playerLife = Singleton.GAME_CHAR.HP;

        imgPlayerSelection = (ImageView)findViewById(R.id.imgPlayerSelection);
        imgEnemySelection = (ImageView)findViewById(R.id.imgEnemySelection);
        txtBattleState = (TextView)findViewById(R.id.txtBattleState);

        imgPlayerSelection.setImageResource(0);
        imgEnemySelection.setImageResource(0);

        txtPlayerName = (TextView)findViewById(R.id.txtPlayerName);
        txtEnemyName = (TextView)findViewById(R.id.txtEnemyName);
        txtPlayerHP = (TextView)findViewById(R.id.txtPlayerHP);
        txtEnemyHP = (TextView)findViewById(R.id.txtEnemyHP);

        txtPlayerName.setText(Singleton.GAME_CHAR.getName());
        txtPlayerHP.setText("HP: " + playerLife);

        if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_AI){
            enemyLife = Singleton.GAME_CHAR.HP;
            txtEnemyName.setText("ROBOT");
            txtEnemyHP.setText("HP: " + enemyLife);
        }
        else if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_PLAYER){
            // TODO: Implement multiplayer battle
        }
        else{
            finish();
        }
    }

    public void BattleChoiceEarth(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_earth);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(EARTH);
        }
    }

    public void BattleChoiceFire(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_fire);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(FIRE);
        }
    }

    public void BattleChoiceForest(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_forest);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(FOREST);
        }
    }

    public void BattleChoiceIce(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_ice);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(ICE);
        }
    }

    public void BattleChoiceThunder(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_thunder);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(THUNDER);
        }
    }

    public void BattleChoiceWater(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_water);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(WATER);
        }
    }

    public void BattleChoiceWind(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_el_wind);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(WIND);
        }
    }

    public void Battle(int choice){
        if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_AI){
            // Make AI choice
            int r = (new Random()).nextInt(7);
            int eChoice;
            switch (r){
                case 0:
                    eChoice=EARTH;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_earth);
                    break;
                case 1:
                    eChoice=FIRE;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_fire);
                    break;
                case 2:
                    eChoice=FOREST;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_forest);
                    break;
                case 3:
                    eChoice=ICE;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_ice);
                    break;
                case 4:
                    eChoice=THUNDER;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_thunder);
                    break;
                case 5:
                    eChoice=WATER;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_water);
                    break;
                case 6:
                    eChoice=WIND;
                    imgEnemySelection.setImageResource(R.mipmap.ic_el_wind);
                    break;
                default:
                    eChoice=0;
                    break;
            }
            // Battle Logic

            // Battle Logic
            int dmg = damageMatrix[choice-EARTH][eChoice-EARTH];
            switch (dmg){
                case -2:
                    txtBattleState.setText(" -- Lose, make another choice! -- ");
                    playerLife-=2;
                    enemyLife-=0;
                    break;
                case -1:
                    txtBattleState.setText(" -- Lose, make another choice! -- ");
                    playerLife-=1;
                    enemyLife-=0;
                    break;
                case 0:
                    txtBattleState.setText(" -- Draw, make another choice! -- ");
                    playerLife-=0;
                    enemyLife-=0;
                    break;
                case 1:
                    txtBattleState.setText(" -- Win, make another choice! -- ");
                    playerLife-=0;
                    enemyLife-=1;
                    break;
                case 2:
                    txtBattleState.setText(" -- Win, make another choice! -- ");
                    playerLife-=0;
                    enemyLife-=2;
                    break;
            }

            // Post Battle
            txtPlayerHP.setText("HP: " + playerLife);
            txtEnemyHP.setText("HP: " + enemyLife);

            if(playerLife<=0){
                txtBattleState.setText(" -- You lost this battle... -- ");
            }
            else if(enemyLife<=0){
                txtBattleState.setText(" -- Congratulations, you're the winner! -- ");
                Singleton.GAME_CHAR.XP++;
                CharacterDAO cDAO = new CharacterDAO(this);
                cDAO.update(Singleton.GAME_CHAR);
            }
            else{
                try{
                    synchronized(this){
                        wait(500);
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                wait = false;
            }
        }
        else if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_PLAYER){
            // TODO: Implement multiplayer battle
        }
        else{
            finish();
        }
    }
}
