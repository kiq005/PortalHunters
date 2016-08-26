package com.example.kaique.portalhunters.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import game.GameChar;

/**
 * Created by kaique on 12/07/16.
 */


public class CharacterDAO implements DAO{

    private CharacterBDDataSource dataSource;

    public CharacterDAO(Context ctx){
        dataSource = new CharacterBDDataSource(ctx, CharacterBDDataSource.DB_NAME, null, CharacterBDDataSource.DB_VERS);
    }

    @Override
    public void create(Object o) {
        try{
            GameChar c = (GameChar)o;

            ContentValues cv = new ContentValues();
            cv.put("name", c.getName());
            cv.put("location", c.getLocation());
            cv.put("sex", String.valueOf(c.getSex()));
            String locations = "";
            for(String loc:c.getKnowLocations()){
                locations += loc + ";";
            }
            cv.put("knowLocation", locations);
            cv.put("HP", c.HP);
            cv.put("MP", c.MP);
            cv.put("CR", c.CR);
            cv.put("XP", c.XP);
            cv.put("level", c.level);
            cv.put("STR", c.STR);
            cv.put("MAG", c.MAG);
            cv.put("AGI", c.AGI);
            cv.put("RES", c.RES);
            cv.put("LCK", c.LCK);

            SQLiteDatabase db = dataSource.getWritableDatabase();
            db.insert(CharacterBDDataSource.TB_NAME, null, cv);
            db.close();
        }
        catch(Exception exc){
            Log.d("CHAR_DAO.CREATE", exc.getMessage());
        }
    }

    @Override
    public Object read(Object o) {
        return null;
    }

    @Override
    public void update(Object o) {
        try{
            GameChar c = (GameChar)o;

            ContentValues cv = new ContentValues();
            cv.put("name", c.getName());
            cv.put("location", c.getLocation());
            cv.put("sex", String.valueOf(c.getSex()));
            String locations = "";
            for(String loc:c.getKnowLocations()){
                locations += loc + ";";
            }
            cv.put("knowLocation", locations);
            cv.put("HP", c.HP);
            cv.put("MP", c.MP);
            cv.put("CR", c.CR);
            cv.put("XP", c.XP);
            cv.put("level", c.level);
            cv.put("STR", c.STR);
            cv.put("MAG", c.MAG);
            cv.put("AGI", c.AGI);
            cv.put("RES", c.RES);
            cv.put("LCK", c.LCK);

            SQLiteDatabase db = dataSource.getWritableDatabase();
            db.replace(CharacterBDDataSource.TB_NAME, null, cv);
            //db.insert(CharacterBDDataSource.TB_NAME, null, cv);
            db.close();
        }
        catch(Exception exc){
            Log.d("CHAR_DAO.CREATE", exc.getMessage());
        }
    }

    @Override
    public void delete(Object o) {

    }

    public ArrayList<GameChar> readAll() {
        try{
            String col[] = {"name", "location", "sex", "knowLocation", "HP", "MP", "CR", "XP",
                    "level", "STR", "MAG", "AGI", "RES", "LCK"};

            // Recover DB readable instance
            SQLiteDatabase db = dataSource.getReadableDatabase();
            // DB consult
            Cursor cursor = db.query(false, CharacterBDDataSource.TB_NAME, col, null, null, null, null, null, null);

            ArrayList<GameChar> resultSet = new ArrayList<GameChar>();
            if(cursor.moveToFirst()){
                do{
                    Log.d("CharDAO", cursor.getString(0) + " -- " + cursor.getString(1) + " -- " + cursor.getString(2) + " -- " + cursor.getString(3) );
                    GameChar c = new GameChar(cursor.getString(0), cursor.getString(2).charAt(0), cursor.getString(1));
                    // TODO: Set know locations
                    c.HP = cursor.getInt(4);
                    c.MP = cursor.getInt(5);
                    c.CR = cursor.getInt(6);
                    c.XP = cursor.getInt(7);
                    c.level = cursor.getInt(8);
                    c.STR = cursor.getInt(9);
                    c.MAG = cursor.getInt(10);
                    c.AGI = cursor.getInt(11);
                    c.RES = cursor.getInt(12);
                    c.LCK = cursor.getInt(13);
                    resultSet.add(c);
                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return resultSet;
        }
        catch(Exception ex){
            Log.d("CharacterDAO.READALL", ex.getMessage());
        }
        return null;
    }
}
