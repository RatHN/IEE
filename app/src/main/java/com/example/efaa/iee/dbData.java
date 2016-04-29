package com.example.efaa.iee;


import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.content.*;

/**
 * Created by EFAA on 26/04/2016.
 */
public class dbData extends SQLiteOpenHelper {
    public static String TABLE = "clases";
    static String NOMBRE = "nombre";
    static String CODIGO = "codigo";
    static String CURSADA = "cursada";
    static String PORcURSAR = "porcursar";

    public dbData(Context context){
        super(context, "data", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
