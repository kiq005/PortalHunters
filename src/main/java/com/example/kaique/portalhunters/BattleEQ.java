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

public class BattleEQ extends AppCompatActivity {

    private final int SWORD = 1001;
    private final int AXE = 1010;
    private final int SHIELD = 1100;

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
            setContentView(R.layout.activity_battle_eq_landscape);
        }
        else {
            setContentView(R.layout.activity_battle_eq);
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

    public void BattleChoiceSword(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_sword);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(SWORD);
        }
    }

    public void BattleChoiceAxe(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_axe);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(AXE);
        }
    }

    public void BattleChoiceShild(View view) {
        if(!wait){
            wait = true;
            imgPlayerSelection.setImageResource(R.mipmap.ic_shild);
            txtBattleState.setText(" -- Waiting enemy choice -- ");
            Battle(SHIELD);
        }
    }

    public void Battle(int choice){
        if(Singleton.BATTLE_OPPONENT == Singleton.BATTLE_OPPONENT_AI){
            // Make AI choice
            int r = (new Random()).nextInt(3);
            int eChoice;
            if(r==0){
                eChoice = SWORD;
                imgEnemySelection.setImageResource(R.mipmap.ic_sword);
            }
            else if(r==1){
                eChoice = AXE;
                imgEnemySelection.setImageResource(R.mipmap.ic_axe);
            }
            else{
                eChoice = SHIELD;
                imgEnemySelection.setImageResource(R.mipmap.ic_shild);
            }

            // Battle
            if(choice == SWORD){
                if(eChoice == SWORD){
                    //SWORD X SWORD: 0
                    txtBattleState.setText(" -- Draw, make another choice! -- ");
                }
                else if(eChoice==AXE){
                    //SWORD X AXE: 1
                    txtBattleState.setText(" -- Win, make another choice! -- ");
                    enemyLife--;
                }
                else{
                    //SWORD X SHIELD: -1
                    txtBattleState.setText(" -- Lose, make another choice! -- ");
                    playerLife--;
                }
            }
            else if(choice==AXE){
                if(eChoice == SWORD){
                    //AXE X SWORD: -1
                    txtBattleState.setText(" -- Lose, make another choice! -- ");
                    playerLife--;
                }
                else if(eChoice==AXE){
                    //AXE X AXE: 0
                    txtBattleState.setText(" -- Draw, make another choice! -- ");
                }
                else{
                    //AXE X SHIELD: 1
                    txtBattleState.setText(" -- Win, make another choice! -- ");
                    enemyLife--;
                }
            }
            else{
                if(eChoice == SWORD){
                    //SHIELD X SWORD: 1
                    txtBattleState.setText(" -- Win, make another choice! -- ");
                    enemyLife--;
                }
                else if(eChoice==AXE){
                    //SHIELD X AXE: -1
                    txtBattleState.setText(" -- Lose, make another choice! -- ");
                    playerLife--;
                }
                else{
                    //SHIELD X SHIELD: 0
                    txtBattleState.setText(" -- Draw, make another choice! -- ");
                }
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
                    //imgEnemySelection.setImageResource(0);
                    //imgPlayerSelection.setImageResource(0);
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
