package com.example.efaa.iee;

/**
 * Created by EFAA on 09/04/2016.
 */
public class Clase {
    public String CODIGO = null;
    public String NOMBRE = null;
    public String PORcURSAR[] = null;
    public ClassFragment fragmento;

    public Clase(String nombre, String codigo, String[] porCursar) {
        CODIGO = codigo;
        NOMBRE = nombre;
        PORcURSAR = porCursar;
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
}
