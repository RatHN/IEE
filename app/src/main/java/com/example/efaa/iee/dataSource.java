package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.efaa.iee.Materias.Clas;
import com.example.efaa.iee.Materias.Clase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by EFAA on 26/04/2016.
 */
public class dataSource extends SQLiteOpenHelper{

    //Metainformación de la base de datos
    public static final String TABLE = "clases";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    private static final String LOCAL_DATABASE_NAME = "data.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final CharSequence UPGRADE_DATABASE = "";
    private static final int BUFFER_SIZE = 1024;
    private final Context mContext;
    private SQLiteDatabase db;

    public dataSource(Context context) throws IOException {
        super(context, LOCAL_DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        if (!isDatabaseFileStored(context)) {
            copyDatabaseFromAssets(context);
        }
    }

    public void open(){
        this.db = this.getWritableDatabase();

    }


    public String getDatabaseFilePath(Context context, String databaseName) {
        return context.getDatabasePath(LOCAL_DATABASE_NAME).getPath();
    }

    private boolean isDatabaseFileStored(Context context) {
        return new File(getDatabaseFilePath(context, LOCAL_DATABASE_NAME)).exists();
    }

    private void copyDatabaseFromAssets(Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.data);
        String localDatabasePath = getDatabaseFilePath(context, LOCAL_DATABASE_NAME);
        new File(localDatabasePath.replace("/"+LOCAL_DATABASE_NAME, UPGRADE_DATABASE)).mkdirs();
        OutputStream outputStream = new FileOutputStream(localDatabasePath);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            int length = inputStream.read(buffer);
            if (length > 0) {
                outputStream.write(buffer, 0, length);
            } else {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                return;
            }
        }
    }



    /**
     * Hace una lista de las clases que son requisitos para una clase que depende de una o más
     *
     * @param dependenciaCode Codigo de la dependencia a usar para realizar la busqueda.
     * @param context         Contexto de la aplicacion o de la actividad
     * @return Una lista de Clas con las clases requsito para dependenciaCode
     */
    public Clas queryDependencias(String dependenciaCode, Context context) {
        String codeName = dependenciaCode;
        String columns[] = new String[]{Columnas.CODIGO};
        String selection = Columnas.PORcURSAR + " LIKE '%" + codeName + "%' ";

        return new Clas(dependenciaCode, db, context);
    }

    public ArrayList<Clas> crearListaDependenciasAndParse(String porcursar, Context context) {
        String array[] = porcursar.split(", ");

        ArrayList<Clas> lista = new ArrayList<>();

        for (String i :
                array) {
            lista.add(queryDependencias(i, context));
        }
        return lista;
    }

    /**
     *
     * @param columna  Columna a evaluar
     * @param ceroOuno Dato a evaluar
     * @param context  Contexto de la aplicacion o actividad que llama esta funcion
     * @return Una lista con las Clases que resultan del resultan del query
     */
    public ArrayList<Clase> queryPasadasODisponibles(String columna, String ceroOuno, Context context) {
        String columns[] = new String[]{Columnas.NOMBRE, Columnas.CODIGO, Columnas.PORcURSAR,
                Columnas.CURSADA, Columnas.DISPONIBLE, Columnas.UV, Columnas.INDICE};

        String selection = columna + " = " + ceroOuno + " ";//WHERE author = ?
        boolean cursada = false;
        switch (columna.compareTo(Columnas.CURSADA)) {
            case 0:
                cursada = true;
        }

        return CrearListaClases(
                db.query(
                        TABLE,
                        columns,
                        selection,
                        null,
                        null,
                        null,
                        null
                ), context, cursada);
    }

    private  ArrayList<Clase> CrearListaClases(Cursor c, Context context, boolean aprovada) {
        ArrayList<Clase> listaClases = new ArrayList<>();
        String porCursar = null;
        String clase = null;
        String codigo = null;
        int uv = 0;
        int indice = 0;
        String cursada = null;
        String disponible = null;


        while (c.moveToNext()) {
            try {
                clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
                porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
                uv = c.getInt(c.getColumnIndexOrThrow(Columnas.UV));
                try {
                    indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("IEI-UNAH_VS", "Hubo un problema con el indice de la clase: " + codigo);
                    indice = 80;
                }
                cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
                disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            ArrayList<Clas> porcursar = crearListaDependenciasAndParse(porCursar, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();


            listaClases.add(new Clase(clase, codigo, porcursar, uv, indice, aprovada));

        }
//        db.close();
        c.close();
        return listaClases;
    }

    /**
     * Hace una lista de las clases que son requisitos para una clase que depende de una o más
     *
     * @param NombreDeLaDependencia Codigo de la dependencia a usar para realizar la busqueda.
     * @param context               Contexto de la aplicacion o de la actividad
     * @return Una lista de Clas con las clases requsito para NombreDeLaDependencia
     */
    public ArrayList<Clas> queryRequisitos(String NombreDeLaDependencia, Context context) {
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
     * Crear Clase evaluando una columna (nombre o codigo) con una DATO
     *
     * @param codigo  Dato a evaluar
     * @param context Contexto de la aplicacion o actividad
     * @return Clase que resulta del resulta del query
     */
    public Clase queryCrearClase(String codigo, Context context) {
        String columns[] = new String[]{Columnas.NOMBRE, Columnas.CODIGO, Columnas.PORcURSAR,
                Columnas.CURSADA, Columnas.DISPONIBLE, Columnas.INDICE, Columnas.UV};
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
                ), context);
    }

    private Clase CrearClase(Cursor c, Context context) {

        ArrayList<Clas> porcursar = new ArrayList<>();
        String porCursar = null;
        String clase = null;
        String codigo = null;
        int uv = 0;
        int indice = 0;
        String cursada = null;
        String disponible = null;

        while (c.moveToNext()) {
            try {
                clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
                codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
                porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
                uv = c.getInt(c.getColumnIndex(Columnas.UV));
                try {
                    indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("IEI-UNAH_VS (UNA CLASE)", "Hubo un problema con el indice de la clase: " + codigo);
                    indice = 80;
                }
                cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
                disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Toast.makeText(context, " COMUNICARSE CON DESARROLADOR \n " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            porcursar = crearListaDependenciasAndParse(porCursar, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();
            break;

        }
//        db.close();
        c.close();
        return new Clase(clase, codigo, porcursar, uv, indice, false);
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
        c.close();
        return lista;
    }

    public Cursor queryPorCursar(@Nullable String OneOrZero) {
        if (OneOrZero == null) {
            OneOrZero = "1";
        }
        String columns[] = new String[]{Columnas.ID_CLASE, Columnas.NOMBRE, Columnas.CODIGO, Columnas.UV, Columnas.INDICE, Columnas.DISPONIBLE};
        String selection = Columnas.CURSADA + " = " + OneOrZero + " ";//WHERE author = ?


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

    /**
     * Inserta un uno o un cero para marcar como pasada o disponible. Hace las revisiones pertinentes
     * para evitar errores.
     *
     * @param codigo  Codigo de la clase
     * @param columna Que columna será modificada
     * @param dato    El cero o uno que se ingresará
     * @param clase   La Clase que se usará
     * @param context El contexto necesario para hacer mensajes de error.
     * @return Codigo de la clase que será eliminada de la lista, o algun codigo de error.
     */
    public String insertarUnoOCero(String codigo, String columna, String dato, Clase clase, Context context) {
        String columna2 = null;
        String result = "";

        // Busqueda de errores antes de seleccionar una clase como no cursada nuevamente....
        if (dato.compareTo("0") == 0) {
            for (Clas clas : clase.ARRAYdEPENDENCIAS) {

                Cursor cursor = db.rawQuery("SELECT codigo FROM clases WHERE codigo = \"" + clas.CODIGO + "\" AND cursada = \"1\"", null);
                if (cursor.getCount() > 0) {
//                    Toast.makeText(context, "EXISTEN CONFLICTOS\n" +
//                            "Esta asignatura es requisito de una asignatura que ya ha sido cursada," +
//                            " por favor desmarque primero asignaturas dependientes y luego sus requisitos", Toast.LENGTH_LONG).show();
                    return "-1";
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


        // Preparando datos a actualizar
        if (columna.equals(Columnas.CURSADA)) {
            columna2 = Columnas.DISPONIBLE;
        }
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(columna, dato);
        String NoDato;
        if (dato.equals( "1" )){
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

            if (dato.compareTo("1") == 0) {                 // Si se esta marcando como pasada, revisar status de cada clase
                if (clas.ARRAY_REQUISITOS.size() == 1) {    // Para marcar como disponible
                    insertarUnoOCeroEnClas(clas.CODIGO, Columnas.DISPONIBLE, "1", context);
                } else if (clas.ARRAY_REQUISITOS.size() > 1) {
                    Cursor cursor = clas.marccarDisponible(db, context);
                    if (cursor.getCount() == clas.ARRAY_REQUISITOS.size()) {
                        String codigos[] = new String[clas.ARRAY_REQUISITOS.size()];
                        for (int i = 0; i < clas.ARRAY_REQUISITOS.size(); i++) {
                            codigos[i] = clas.ARRAY_REQUISITOS.get(i).toString();
                        }
                        insertarUnoOCeroEnClas(clas.CODIGO, Columnas.DISPONIBLE, "1", context);
                        result = "0";
                    }
                }
            } else { // Si es una desmarcion, marcar sus dependientes como NO disponibles
                insertarUnoOCeroEnClas(clas.CODIGO, Columnas.DISPONIBLE, "0", context);
                result = clase.CODIGO;
            }

        }
        return result;
    }


    private void insertarUnoOCeroEnClas(String codigo, String columna, String dato, Context context) {
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
        if (dato.equals("1")) {
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
     * @param columna  Columna a evaluar
     * @param ceroOuno Dato a evaluar
     * @param context  Contexto de la aplicacion o actividad
     * @return Una lista con las Clases que resultan del resultan del query
     */
    public String[] queryClasesString(String columna, String ceroOuno, Context context) {
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
        String listaClases[] = new String[100];
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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //Campos de la tabla Quotes
    public static class Columnas {
        public static final String ID_CLASE = BaseColumns._ID;
        public final static String NOMBRE = "nombre";
        static public final String CODIGO = "codigo";
        static public final String CURSADA = "cursada";
        public final static String PORcURSAR = "porcursar";
        public final static String DISPONIBLE = "disponible";
        public final static String UV = "uv";
        public final static String INDICE = "indice";
        public final static String[] COLUMNAS = {DISPONIBLE, CURSADA};
        public final static String[] COMLUMNAS_INDEX1 = {null, DISPONIBLE, CURSADA};
        public static final int DISP_INT = 0;
        public static final int CURS_INT = 1;
    }

    public static class Estados {
        public static final String DISPONIBLES = "Cursar";
        public static final String CURSADA = "Cursada";

    }


//    public boolean


}
