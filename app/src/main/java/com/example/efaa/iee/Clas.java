package com.example.efaa.iee;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Neri Ortez on 29/04/2016.
 */
public class Clas{
    public String CODIGO = null;
    public String NOMBRE = null;
    public String REQUISITOS[] = null;
    public ClassFragment fragmento;
    public ArrayList<Clas> ARRAY_REQUISITOS;

    Clas(){
    }

    Clas(String nombre, String codigo, String requisitos[]) {
        CODIGO = codigo;
        NOMBRE = nombre;
        REQUISITOS = requisitos;

    }

    /**
     * Crea Dependencias
     * @param nombre Nombre de la dependencia
     * @param codigo Codigo de la dependencia
     */
    Clas(String codigo, SQLiteDatabase db, Context context){
//        NOMBRE = nombre;
        CODIGO = codigo;
        ARRAY_REQUISITOS = new dataSource().queryRequisitos(db, codigo, context);
    }

    /**
     * Crea Requisitos
      * @param code Codigo del requisito
     */
    Clas(String code){
        CODIGO = code;
    }

    Clas(String nombre, String codigo, String[] porCursar, ClassFragment frag) {
        CODIGO = codigo;
        NOMBRE = nombre;
//        PORcURSAR = porCursar;
        fragmento = frag;
    }

    public String toString(){
        return CODIGO;
    }

    public Cursor marccarDisponible(SQLiteDatabase db, Context context) {
        return db.rawQuery("SELECT * FROM " + dataSource.TABLE + " WHERE cursada = \"1\" AND porcursar LIKE \"%" + CODIGO + "%\"", null);
    }

    public Cursor verificar(SQLiteDatabase db, Context context) {
        return db.rawQuery("SELECT * FROM " + dataSource.TABLE + " WHERE cursada = \"1\" AND porcursar LIKE \"%" + CODIGO + "%\"", null);
    }
}

