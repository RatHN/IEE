package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
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


    /**
     * Hace una lista de las clases que son requisitos para una clase que depende de una o más
     *
     * @param db              Base de Datos a usar.
     * @param dependenciaCode Codigo de la dependencia a usar para realizar la busqueda.
     * @param context         Contexto de la aplicacion o de la actividad
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
     * @param db                    Base de Datos a usar.
     * @param NombreDeLaDependencia Codigo de la dependencia a usar para realizar la busqueda.
     * @param context               Contexto de la aplicacion o de la actividad
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


    /**
     * Crear Clase evaluando una columna (nombre o codigo) con una DATO
     *
     * @param db      Base de Datos a usar
     * @param codigo  Dato a evaluar
     * @param context Contexto de la aplicacion o actividad
     * @return Clase que resulta del resulta del query
     */
    public Clase queryCrearClase(SQLiteDatabase db, String codigo, Context context) {
        String columns[] = new String[]{Columnas.NOMBRE, Columnas.CODIGO, Columnas.PORcURSAR, Columnas.CURSADA, Columnas.DISPONIBLE};
        String selection = Columnas.CODIGO + " = \"" + codigo + "\" ";//WHERE author = ?
//        db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + Columnas.CODIGO + " = " + codigo, null);


        return CrearClase(
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

    private Clase CrearClase(Cursor c, SQLiteDatabase db, Context context) {

        ArrayList<Clas> porcursar = new ArrayList<>();
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
                Toast.makeText(context, " COMUNICARSE CON DESARROLADOR \n " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            porcursar = crearListaDependenciasAndParse(porCursar, db, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();
            break;

        }
        return new Clase(clase, codigo, porcursar);
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

    public int insertarUnoOCero(SQLiteDatabase db, String codigo, String columna, String dato, Clase clase, Context context) {
        String columna2 = null;

        if (dato.compareTo("0") == 0) {
            for (Clas clas : clase.ARRAYdEPENDENCIAS) {

                Cursor cursor = db.rawQuery("SELECT codigo FROM clases WHERE codigo = \"" + clas.CODIGO + "\" AND cursada = \"1\"", null);
                if (cursor.getCount() > 0) {
                    Toast.makeText(context, "EXISTEN CONFLICTOS\n" +
                            "Esta asignatura es requisito de una asignatura que ya ha sido cursada," +
                            " por favor desmarque primero asignaturas dependientes y luego sus requisitos", Toast.LENGTH_LONG).show();
                    return -1;
                }
//                cursor = clas.marccarDisponible(db, context);
//                if (cursor.getCount() > 1) {
//                        Toast.makeText(context, "EXISTEN CONFLICTOS\n" +
//                                "Esta asignatura habilita otra asignatura que tiene " +
//                                "aun más requisitos activos, por favor verifique que no existan " +
//                                "conflictos", Toast.LENGTH_LONG).show();
//                        return -1;
//                    }
                }
//                Clase clasesita = new dataSource().queryCrearClase(db, clas.CODIGO, context);
//                cursor = db.rawQuery(" SELECT codigo FROM clases WHERE porcursar LIKE \"%" + clas.CODIGO + "%\" AND cursada = \"1\"", null);
//                if (cursor.getCount() > 0) {
//                    Toast.makeText(context, "EXISTEN CONFLICTOS\n" +
//                            "Esta asignatura es requisito de una asignatura que ya ha sido cursada," +
//                            " por favor desmarque primero asignaturas dependientes y luego sus requisitos", Toast.LENGTH_LONG).show();
//                    return -1;
//                }
            }



        if (columna == Columnas.CURSADA) {
            columna2 = Columnas.DISPONIBLE;
        }
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(columna, dato);
        String NoDato;
        if (dato == "1") {
            values.put(columna2, "0");
            values.put(columna, "1");
            NoDato = "0";
        } else {
            values.put(columna, "0");
            values.put(columna2, "1");
            NoDato = "1";
        }

        //Clausula WHERE
        String selection = Columnas.CODIGO + " = ?";
        String[] selectionArgs = {codigo};

        //Actualizando
        int r = db.update(TABLE, values, selection, selectionArgs);

        for (Clas clas : clase.ARRAYdEPENDENCIAS) {

            if (dato.compareTo("1") == 0) {
                if (clas.ARRAY_REQUISITOS.size() == 1) {
                    insertarUnoOCeroEnClas(db, clas.CODIGO, Columnas.DISPONIBLE, "1", context);
                } else if (clas.ARRAY_REQUISITOS.size() > 1) {
                    Cursor cursor = clas.marccarDisponible(db, context);
                    if (cursor.getCount() == clas.ARRAY_REQUISITOS.size()) {
                        String codigos[] = new String[clas.ARRAY_REQUISITOS.size()];
                        for (int i = 0; i < clas.ARRAY_REQUISITOS.size(); i++) {
                            codigos[i] = clas.ARRAY_REQUISITOS.get(i).toString();
                        }
                        insertarUnoOCeroEnClas(db, clas.CODIGO, Columnas.DISPONIBLE, "1", context);
                    }
                }
            } else {
                insertarUnoOCeroEnClas(db, clas.CODIGO, Columnas.DISPONIBLE, "0", context);
            }

        }
        return 0;
    }


    private void insertarUnoOCeroEnClas(SQLiteDatabase db, String codigo, String columna, String dato, Context context) {
        String columna2 = null;
        if (columna == Columnas.CURSADA) {
            columna2 = Columnas.DISPONIBLE;
        } else {
            columna2 = Columnas.CURSADA;
        }

//        ContentValues values = new ContentValues();

        //Seteando body y author
        //values.put(columna, dato);
        String uno = "";
        String dos = "";
        Cursor r;
        if (dato == "1") {
            r = db.rawQuery("UPDATE clases SET " + columna + " = \"1\", " + columna2 + " = \"0\" WHERE codigo = \"" + codigo + "\"", null);

//            values.put(columna2, "0");
//            values.put(columna, "1");
        } else {

            r = db.rawQuery("UPDATE clases SET " + columna2 + " = \"0\", " + columna + " = \"0\" WHERE codigo = \"" + codigo + "\"", null);
//            values.put(columna, "0");
//            values.put(columna2, "1");
        }
        int i = r.getCount();
        //Clausula WHERE
//        String selection = Columnas.CODIGO + " = ?";
        //String[] selectionArgs = codigo;

        //Actualizando
//        db.update(TABLE, values, selection, selectionArgs);


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

    public static class Estados {
        public static final String DISPONIBLES = "Cursar";
        public static final String CURSADA = "Cursada";

    }


//    public boolean


}
