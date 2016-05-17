package com.example.efaa.iee;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by EFAA on 09/04/2016.
 * Esta clase es un poco confusa dado que su nombre es "Clase"
 * y tiene dos arrays, un PORcURSAR que contiene la lista de materias que estarian disponibles de
 * aprobarse ésta materia. Y ARRAYdEPENDENCIAS que contiene un Array de Clas con las materias que
 * estarian disponibles tambien. La diferencia es que PORcURSAR contiene solamente los códigos de
 * las materias, y ARRAYdEPENDENCIAS contiene las instancias de la clase Clas con las materias en sí.
 */
public class Clase extends Clas{
    public String CODIGO = null;
    public String NOMBRE = null;
    public String PORcURSAR[] = null;
    public ClassFragment fragmento;
    public ArrayList<Clas> ARRAYdEPENDENCIAS;
    public int UV;
    public int INDICE;
    public boolean CURSADA;

    public int position = 0;

//    private dataSource dtsrc = new dataSource();
//    private SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);

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

    public Clase(String nombre, String codigo, ArrayList<Clas> clasArrayList, int uv, int indice, boolean cursada) {
        CODIGO = codigo;
        NOMBRE = nombre;
        ARRAYdEPENDENCIAS = clasArrayList;
        UV = uv;
        INDICE = indice;
        CURSADA = cursada;
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
        return NOMBRE;
    }
}
