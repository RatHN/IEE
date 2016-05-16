package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 09/05/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Clase>> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
//    public ClaseRecyclerAdaptador.EscuchadorDeInteraccion meEscucha;

    //    AdaptadorArrayClase adaptador;
//    ListView lista;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lManager;
    Bundle ARGS;
    ClaseRecyclerAdaptador cAdaptador;
    public Boolean flag = false;
    private static final String FLAG = "flag";

    SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
    public View.OnClickListener checkBoxClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.Nombre_de_Clase);
            LinearLayout e = (LinearLayout) view.getParent();
            TextView codigo = (TextView) view.findViewById(R.id.Codigo);
            String cod = (String) codigo.getText();
            String dato = null;

            if (checkBox.isChecked()) {
                dato = "1";
            } else {
                dato = "0";
            }
            String result = new dataSource().insertarUnoOCero(dob, cod, dataSource.Columnas.CURSADA, dato,
                    new dataSource().queryCrearClase(dob, cod, view.getContext()), view.getContext());
            if (result == "-1") {
                checkBox.setChecked(true);
            }

//        ClassFragment fra = getShe
            else if ((result != "0" || result != "1")
                    && getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            }
            dob.releaseReference();


        }
    };

    String COLUMNA;
//    ClaseRecyclerAdaptador recyclerAdaptador;



    public PlaceHolderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceHolderFragment newInstance(int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        Boolean bol = null;
        switch (sectionNumber) {
            case 1:
                bol = true;
                break;
            case 2:
                bol = false;
                break;
        }
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBoolean(FLAG, bol);
        fragment.setArguments(args);

        return fragment;
    }

    private View ROOTVIEW;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Inflar la pestaña con el fragmento que contiene el ListView o el RecyclerView
//        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        View rootView = inflater.inflate(R.layout.reciclador_tab, container, false);

        //Conseguir el listView o RecyclerView
//        lista = (ListView) rootView.findViewById(R.id.listViewFragment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recyclerView.setHasFixedSize(true);

//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //Establecer el tipo de vista: Cursadas, Disponibles o especial
        String columna = dataSource.Columnas.DISPONIBLE;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                columna = dataSource.Columnas.CURSADA;
        }
        COLUMNA = columna;
        flag = getArguments().getBoolean(FLAG);

//        String columns[] = new String[]{"_id", dataSource.Columnas.NOMBRE, dataSource.Columnas.CODIGO,
//                dataSource.Columnas.PORcURSAR, CURSADA, DISPONIBLE,
//                dataSource.Columnas.UV, dataSource.Columnas.INDICE};

//        String selection = columna + " = " + "1" + " ";//WHERE author = ?

        //Creamos un adaptador vacio para poder habilitar el bucle y el Loader funcione en otro Thread
        ArrayList<Clase> ar = new ArrayList<>();
        ar.add(new Clase("NADA", "NADA", null, getContext()));
        cAdaptador = new ClaseRecyclerAdaptador(ar);

        //Establecemos adaptadores
//        lista.setAdapter(adapter);
//        lManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        lManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(cAdaptador);

        //Creamos los listener para clicks
        RecyclerView.OnItemTouchListener escucha = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                if (e.getAction() == MotionEvent.ACTION_DOWN) {
//                    Snackbar.make(rv, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    return;
//                                }
//                            }).show();
//                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                return;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        //Seteamos esos escuchas
//        lista.setOnItemClickListener(listener);
        recyclerView.addOnItemTouchListener(escucha);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        ARGS = new Bundle();
        ARGS.putString("COLUMNA", columna);

        //Establecemos el flag en negativo para mostrar la no-seleccion
//        flag = false;
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, ARGS, this);
//        getSupportLoaderManager();
    }


    // Called when a new Loader needs to be created

    @Override
    public Loader<ArrayList<Clase>> onCreateLoader(int id, Bundle args) {
        final String columna = args.getString("COLUMNA");

//        return new ClaseLoader(getContext(), COLUMNA);
        return new android.support.v4.content.AsyncTaskLoader<ArrayList<Clase>>(getActivity()) {
            @Override
            public void onStartLoading() {
                forceLoad();
            }
            @Override
            public ArrayList<Clase> loadInBackground() {
                ArrayList array;
                array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());
                return array;
            }
        };

    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Clase>> loader,
                               ArrayList<Clase> data) {
//        if (cAdaptador.getItemCount() != adaptador1.getItemCount()){
        int fin = recyclerView.getAdapter().getItemCount();
//        cAdaptador = new ClaseRecyclerAdaptador(data);

        recyclerView.setAdapter(new ClaseRecyclerAdaptador(data));

//        recyclerView.getAdapter().notifyItemRangeChanged(0, fin);
//        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Clase>> loader) {
        recyclerView.setAdapter(null);
    }

    public void update() {
//        getLoaderManager().initLoader(0, ARGS, this).forceLoad();

//        ArrayList array = new ArrayList();
//        array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());
        while (recyclerView.isAnimating()) {
            ;
        }
        getLoaderManager().initLoader(0, ARGS, this);
//        cAdaptador.cambiarLista(array);
//        cAdaptador.notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return COLUMNA;
    }
}

