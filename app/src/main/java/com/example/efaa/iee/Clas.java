package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 29/04/2016.
 */
public class Clas{
    public String CODIGO = null;
    public String NOMBRE = null;
    public String REQUISITOS[] = null;
    public ClassFragment fragmento;

    Clas(){
    }

    Clas(String nombre, String codigo, String requisitos[]) {
        CODIGO = codigo;
        NOMBRE = nombre;
        REQUISITOS = requisitos;

    }

    Clas(String code){
        CODIGO = code;
    }

    Clas(String nombre, String codigo, String[] porCursar, ClassFragment frag) {
        CODIGO = codigo;
        NOMBRE = nombre;
//        PORcURSAR = porCursar;
        fragmento = frag;
        return;
    }

    public String toString(){
        return "Clas";
    }
}
