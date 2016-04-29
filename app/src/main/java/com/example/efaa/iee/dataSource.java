package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        return parseDependencias(
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


        return parsePorCursar(
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

    private ArrayList<Clase> parsePorCursar(Cursor c, Context context) {
        ArrayList<Clase> lista = new ArrayList<>();
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

            String[] porcursar = parsePorCursarString(porCursar);
            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();
            for (String clas :
                    porcursar) {
                
            }

            lista.add(new Clase(clase, codigo, porcursar));

        }
        return lista;
    }

    private ArrayList<Clas> parseDependencias(Cursor c, Context context) {
        ArrayList<Clas> lista = new ArrayList<>();

        String clase = null;
        String codigo = null;

        while (c.moveToNext()) {
            try {
//                clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            lista.add(new Clas(codigo));

        }
        return lista;
    }

    private String[] parsePorCursarString(String porcursar) {
        return porcursar.split(", ");
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
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(columna, dato);
        switch (dato) {
            case "1":
                values.put(columna2, "0");
            case "0":
                values.put(columna2, "1");
        }


//        values.put(QuotesDataSource.ColumnQuotes.AUTHOR_QUOTES, "Nuevo Autor");

        //Clausula WHERE
        String selection = Columnas.CODIGO + " = ?";
        String[] selectionArgs = {codigo};

        //Actualizando
        db.update(TABLE, values, selection, selectionArgs);
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

    //Script de Creación de la tabla Quotes
//    public static final String CREATE_QUOTES_SCRIPT =
//            "create table "+QUOTES_TABLE_NAME+"(" +
//                    ColumnQuotes.ID_QUOTES+" "+INT_TYPE+" primary key autoincrement," +
//                    ColumnQuotes.BODY_QUOTES+" "+STRING_TYPE+" not null," +
//                    ColumnQuotes.AUTHOR_QUOTES+" "+STRING_TYPE+" not null)";


    //Scripts de inserción por defecto
//    public static final String INSERT_QUOTES_SCRIPT =
//            "insert into "+ TABLE +" values(" +
//                    "null," +
//                    "\"El ignorante afirma, el sabio duda y reflexiona\"," +
//                    "\"Aristóteles\")," +
//                    "(null," +
//                    "\"Hay derrotas que tienen mas dignidad que la victoria\"," +
//                    "\"Jorge Luis Borges\")," +
//                    "(null," +
//                    "\"Si buscas resultados distintos, no hagas siempre lo mismo\"," +
//                    "\"Albert Einstein\")," +
//                    "(null," +
//                    "\"Donde mora la libertad, allí está mi patria\"," +
//                    "\"Benjamin Franklin\")," +
//                    "(null," +
//                    "\"Ojo por ojo y todo el mundo acabará ciego\"," +
//                    "\"Mahatma Gandhi\")";

//    private QuotesReaderDbHelper openHelper;
//    private SQLiteDatabase database;
//
//    public QuotesDataSource(Context context) {
//        //Creando una instancia hacia la base de datos
//        openHelper = new QuotesReaderDbHelper(context);
//        database = openHelper.getWritableDatabase();
//    }

}
