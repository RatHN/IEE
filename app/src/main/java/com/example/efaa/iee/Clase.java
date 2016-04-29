package com.example.efaa.iee;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by EFAA on 09/04/2016.
 */
public class Clase extends Clas{
    public String CODIGO = null;
    public String NOMBRE = null;
    public String PORcURSAR[] = null;
    public ClassFragment fragmento;
    public List<Clas> DEPEN;

    Clase(){
        return;
    }

    public Clase(String nombre, String codigo, String[] porCursar) {
        CODIGO = codigo;
        NOMBRE = nombre;
        PORcURSAR = porCursar;
        String da;
        dataSource dtsrc = new dataSource();
        SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
        for (String i :
             porCursar) {
            da = dtsrc.queryPasadasODisponibles(dob,)
        }
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

public class Dependencia extends Clase{

}
