package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by EFAA on 26/04/2016.
 */
public class dataSource {

    //Metainformación de la base de datos
    public static final String TABLE = "clases";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //Ingresar nuevas filas //NO NECESARIO
    public void saveQuoteRow(SQLiteDatabase db, String nombre, String codigo) {
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(Columnas.NOMBRE, nombre);
        values.put(Columnas.CODIGO, codigo);

        //Insertando en la base de datos
        db.insert(TABLE, null, values);
    }


    /**
     *  Hace una lista de las clases que son requisitos para una clase que depende de una o más
     *
     * @param db Base de Datos a usar.
     * @param dependenciaCode Codigo de la dependencia a usar para realizar la busqueda.
    * @param context Contexto de la aplicacion o de la actividad
    * @return Una lista de Clas con las clases requsito para dependenciaCode
    */
    public Clas queryDependencias(SQLiteDatabase db, String dependenciaCode, Context context) {
        String codeName = dependenciaCode;
        String columns[] = new String[]{Columnas.CODIGO};
        String selection = Columnas.PORcURSAR + " LIKE '%" + codeName + "%' ";

        return new Clas(dependenciaCode, db, context);
    }


    /**
     * Hace una lista de las clases que son requisitos para una clase que depende de una o más
     *
     * @param db Base de Datos a usar.
     * @param NombreDeLaDependencia Codigo de la dependencia a usar para realizar la busqueda.
     * @param context Contexto de la aplicacion o de la actividad
     * @return Una lista de Clas con las clases requsito para NombreDeLaDependencia
     */
    public ArrayList<Clas> queryRequisitos(SQLiteDatabase db, String NombreDeLaDependencia, Context context) {
        String codeName = NombreDeLaDependencia;
        String columns[] = new String[]{Columnas.CODIGO};
        String selection = Columnas.PORcURSAR + " LIKE '%" + codeName + "%' ";

        return parseRequisitos(
                db.query(
                        TABLE,
                        columns,
                        selection,
                        null,
                        null,
                        null,
                        null
                ),
                context);
    }

    /**
     * @param db       Base de Datos a usar
     * @param columna  Columna a evaluar
     * @param ceroOuno Dato a evaluar
     * @param context  Contexto de la aplicacion o actividad
     * @return Una lista con las Clases que resultan del resultan del query
     */
    public ArrayList<Clase> queryPasadasODisponibles(SQLiteDatabase db, String columna, String ceroOuno, Context context) {
        String columns[] = new String[]{Columnas.NOMBRE, Columnas.CODIGO, Columnas.PORcURSAR, Columnas.CURSADA, Columnas.DISPONIBLE};
        String selection = columna + " = " + ceroOuno + " ";//WHERE author = ?


        return CrearListaClases(
                db.query(
                        TABLE,
                        columns,
                        selection,
                        null,
                        null,
                        null,
                        null
                ), db,
                context);
    }

    private ArrayList<Clase> CrearListaClases(Cursor c, SQLiteDatabase db, Context context) {
        ArrayList<Clase> listaClases = new ArrayList<>();
        String porCursar = null;
        String clase = null;
        String codigo = null;
        String cursada = null;
        String disponible = null;

        while (c.moveToNext()) {
            try {
                clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
                porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
                cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
                disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            ArrayList<Clas> porcursar = crearListaDependenciasAndParse(porCursar, db, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();


            listaClases.add(new Clase(clase, codigo, porcursar));

        }
        return listaClases;
    }

    private ArrayList<Clas> parseRequisitos(Cursor c, Context context) {
        ArrayList<Clas> lista = new ArrayList<>();

        String codigo = null;

        while (c.moveToNext()) {
            try {
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            lista.add(new Clas(codigo));

        }
        return lista;
    }

    private ArrayList<Clas> crearListaDependenciasAndParse(String porcursar, SQLiteDatabase db, Context context) {
        String array[] = porcursar.split(", ");

        ArrayList<Clas> lista = new ArrayList<>();

        for (String i :
                array) {
            lista.add(queryDependencias(db, i, context));
        }
        return lista;
    }


    public Cursor queryPorCursar(SQLiteDatabase db, String codigo) {
        String columns[] = new String[]{Columnas.PORcURSAR};
        String selection = Columnas.CODIGO + " = " + codigo + " ";//WHERE author = ?


        return db.query(
                TABLE,
                columns,
                selection,
                null,
                null,
                null,
                null
        );
    }

    public void insertarUnoOCero(SQLiteDatabase db, String codigo, String columna, String dato) {
        String columna2 = null;
        if (columna == Columnas.CURSADA) {
            columna2 = Columnas.DISPONIBLE;
        }
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(columna, dato);
        switch (dato) {
            case "1":
                values.put(columna2, "0");
            case "0":
                values.put(columna2, "1");
        }

        //Clausula WHERE
        String selection = Columnas.CODIGO + " = ?";
        String[] selectionArgs = {codigo};

        //Actualizando
        db.update(TABLE, values, selection, selectionArgs);
    }


    /**
     * @param db       Base de Datos a usar
     * @param columna  Columna a evaluar
     * @param ceroOuno Dato a evaluar
     * @param context  Contexto de la aplicacion o actividad
     * @return Una lista con las Clases que resultan del resultan del query
     */
    public String[] queryClasesString(SQLiteDatabase db, String columna, String ceroOuno, Context context) {
        String columns[] = new String[]{Columnas.CODIGO};
        String selection = columna + " = " + ceroOuno + " ";//WHERE author = ?


        return CrearListaClasesString(
                db.query(
                        TABLE,
                        columns,
                        selection,
                        null,
                        null,
                        null,
                        null
                ),
                context);
    }

    private String[] CrearListaClasesString(Cursor c, Context context) {
        String listaClases[] = new String[60];
        String codigo = null;
        int i = 0;
        while (c.moveToNext()) {
            try {
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            listaClases[i] = codigo;
            i++;
        }
        return listaClases;
    }


    //Campos de la tabla Quotes
    public static class Columnas {
        public static final String ID_CLASE = BaseColumns._ID;
        public final static String NOMBRE = "nombre";
        static public final String CODIGO = "codigo";
        static public final String CURSADA = "cursada";
        public final static String PORcURSAR = "porcursar";
        public final static String DISPONIBLE = "disponible";
    }



}
