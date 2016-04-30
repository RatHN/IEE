package com.example.efaa.iee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EFAA on 09/04/2016.
 */
public class Clase extends Clas{
    public String CODIGO = null;
    public String NOMBRE = null;
    public String PORcURSAR[] = null;
    public ClassFragment fragmento;
    public ArrayList<Clas> ARRAYdEPENDENCIAS;
    private dataSource dtsrc = new dataSource();
    private SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);

    Clase(){
        return;
    }

    public Clase(String nombre, String codigo, String[] porCursar, Context contextt) {
        CODIGO = codigo;
        NOMBRE = nombre;
        PORcURSAR = porCursar;
        String da;

//        for (String i :
//             porCursar) {
//            da = dtsrc.queryDependencias(dob, i, contextt)
//        }
    }

    public Clase(String nombre, String codigo, ArrayList<Clas> clasArrayList) {
        CODIGO = codigo;
        NOMBRE = nombre;
        ARRAYdEPENDENCIAS = clasArrayList;

        return;
    }

    public Clase(String nombre, String codigo, String[] porCursar, ClassFragment frag) {
        CODIGO = codigo;
        NOMBRE = nombre;
        PORcURSAR = porCursar;
        fragmento = frag;
        return;
    }

    public void pasada(boolean bo, int per100) {
        //Marcar dependencias como pasadas


        //¿Eliminar fragmento?
    }

    public String toString(){
        return "Clase";
    }
}
