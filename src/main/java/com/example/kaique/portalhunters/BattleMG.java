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

public class BattleMG extends AppCompatActivity {

    private final int RED = 2001;
    private final int GREEN = 2002;
    private final int BLUE = 2003;
    private final int BLACK = 2004;

    private final int[][] damageMatrix = {
            {-1,2,-2,1},
            {-2,-1,1,2},
            {2,1,-1,-2},
            {1,-2,2,-1}
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
            setContentView(R.layout.activity_battle_mg_landscape);
        }
        else {
            setContentView(R.layout.activity_battle_mg);
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

    public void BattleChoiceRed(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_orb_red);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(RED);
        }
    }

    public void BattleChoiceGreen(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_orb_green);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(GREEN);
        }
    }

    public void BattleChoiceBlue(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_orb_blue);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(BLUE);
        }
    }

    public void BattleChoiceBlack(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_orb_black);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(BLACK);
        }
    }

    public void Battle(int choice){
        if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_AI){
            // Make AI choice
            int r = (new Random()).nextInt(4);
            int eChoice;
            switch (r){
                case 0:
                    eChoice=RED;
                    imgEnemySelection.setImageResource(R.mipmap.ic_orb_red);
                    break;
                case 1:
                    eChoice=GREEN;
                    imgEnemySelection.setImageResource(R.mipmap.ic_orb_green);
                    break;
                case 2:
                    eChoice=BLUE;
                    imgEnemySelection.setImageResource(R.mipmap.ic_orb_blue);
                    break;
                case 3:
                    eChoice=BLACK;
                    imgEnemySelection.setImageResource(R.mipmap.ic_orb_black);
                    break;
                default:
                    eChoice=0;
                    break;
            }
            // Battle Logic
            int dmg = damageMatrix[choice-RED][eChoice-RED];
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
