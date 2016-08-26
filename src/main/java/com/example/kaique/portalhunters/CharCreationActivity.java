package com.example.kaique.portalhunters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaique.portalhunters.dao.CharacterDAO;

import game.GameChar;
import game.Singleton;

public class CharCreationActivity extends AppCompatActivity {

    private char sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);

        this.sex = 'n';
    }

    public void clickCastleLocation(View view) {
        //Intent intent = new Intent(this, CastleMapActivity.class);
        Intent intent = new Intent(this, CastleSelectionActivity.class);
        startActivity(intent);
    }

    public void clickMale(View view) {
        this.sex = 'm';
        TextView txtSex = (TextView)findViewById(R.id.txtSex);
        txtSex.setText("Sex: Male");
    }

    public void clickFemale(View view) {
        this.sex = 'f';
        TextView txtSex = (TextView)findViewById(R.id.txtSex);
        txtSex.setText("Sex: Female");
    }

    public void clickConfirm(View view) {
        String name = ((EditText)findViewById(R.id.EdCharName)).getText().toString();
        if(name.length()<3){
            Toast.makeText(this, "Please, set char's name", Toast.LENGTH_SHORT).show();
        }
        else if(Singleton.CASTLE_LOCATION == null){
            Toast.makeText(this, "Please, select a location", Toast.LENGTH_SHORT).show();
        }
        else if(this.sex=='n'){
            Toast.makeText(this, "Please, select sex", Toast.LENGTH_SHORT).show();
        }
        else{
            // TODO: Create and return a new character
            GameChar c = new GameChar(name, this.sex, Singleton.CASTLE_LOCATION.toString());

            CharacterDAO cDAO = new CharacterDAO(this);
            cDAO.create(c);

            setResult(100);
            finish();
        }
    }

    public void clickCancel(View view) {
        finish();
    }
}
