package com.example.kaique.portalhunters.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kaique on 12/07/16.
 */
public class CharacterBDDataSource extends SQLiteOpenHelper {

    public static final String  DB_NAME = "CHARACTERS_DB";
    public static final int     DB_VERS = 2;
    public static final String  TB_NAME = "tbCharacters";

    public CharacterBDDataSource(Context ctx,
                                 String dbName,
                                 SQLiteDatabase.CursorFactory cursor,
                                 int version){
        super(ctx, dbName, cursor, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String SQL = "CREATE TABLE " + TB_NAME + " (" +
                    "name varchar(100), " +
                    "location varchar(100), " +
                    "sex varchar(1), " +
                    "knowLocation varchar(1000), " +
                    "HP int, " + "MP int, " + "CR int, " + "XP int, " + "level int, " +
                    "STR int, " + "MAG int, " + "AGI int, " + "RES int, " + "LCK int )";
            db.execSQL(SQL);
        }
        catch(Exception exc){
            Log.d("CHAR_DB", exc.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
