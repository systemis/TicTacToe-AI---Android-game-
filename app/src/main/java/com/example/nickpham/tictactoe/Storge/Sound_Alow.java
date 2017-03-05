package com.example.nickpham.tictactoe.Storge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickpham on 17/11/2016.
 */

public class Sound_Alow extends SQLiteOpenHelper {
    Context context;

    private final String Table_Name   = "datat_alow_sound_background_app";
    private final String Dataf_ID     = "ID_from_player";
    private final String Dataf_Player = "action_from_player";

    public Sound_Alow(Context context)
    {
        super(context, "datat_alow_sound", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "Create table if not exists " + Table_Name
                + " ( " + Dataf_ID + " Integer primary key, "
                + Dataf_Player + " Integer" + ")";
        sqLiteDatabase.execSQL(sql);
    }

    public void insert_data_sound_fPlayer(int datat_sound, int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Dataf_Player, datat_sound);
        value.put(Dataf_ID,     id);
        db.insert(Table_Name, null, value);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int get_datat_from_player()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * from " + Table_Name;
        Cursor c = db.rawQuery(sql, null, null);
        if (c.moveToFirst())
        {
            List<Integer> sound_data = new ArrayList<Integer>();
            do
            {
                sound_data.add(c.getInt(c.getColumnIndex(Dataf_Player)));
            }while (c.moveToNext());

            return sound_data.get(0);

        }else
        {
            return 0;
        }
    }

    public int change_data_alow_sound (int ID,Integer Name_)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Dataf_Player, Name_);
        db.update(Table_Name, contentValues, Dataf_ID + " = ? ", new String[] { Integer.toString(ID) } );

        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
