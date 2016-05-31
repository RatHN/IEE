package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 09/05/2016.
 */

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Clase>>, ClaseRecyclerAdaptador.InterfaceEscuchador {
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

    SQLiteDatabase dob;

    String COLUMNA;
    getPermisos permisos;


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
                bol = false;
                break;
            case 2:
                bol = true;
                break;
        }
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBoolean(FLAG, bol);
        fragment.setArguments(args);

        return fragment;
    }

    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Inflar la pestaña con el fragmento que contiene el ListView o el RecyclerView
        View rootView = inflater.inflate(R.layout.reciclador_tab, container, false);

        progressBar = ((ProgressBar) rootView.findViewById(R.id.progressBar));
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        permisos = (getPermisos) getActivity();
        //Conseguir el listView o RecyclerView
//        lista = (ListView) rootView.findViewById(R.id.listViewFragment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recyclerView.setHasFixedSize(false);

//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //Establecer el tipo de vista: Cursadas, Disponibles o especial
        String columna = dataSource.Columnas.DISPONIBLE;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 2:
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


        //Establecemos adaptadoresx
//        lista.setAdapter(adapter);
//        lManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        lManager = new LinearLayoutManager(getContext());





        /*SwipeHellper itemTouchHelper = new SwipeHellper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView);

        itemsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        itemsRecyclerView.setAdapter(adapter);*/
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHellper());
        itemTouchHelper.attachToRecyclerView(recyclerView);



        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(new ClaseRecyclerAdaptador(new ArrayList<Clase>()));

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


                while (!permisos.getPermisos()) {
                    Log.i("TAG", "ESPERando por permisos");
                }
                ArrayList array;
                dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
                array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());
                dob.releaseReference();
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
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

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
//        recyclerView.getAdapter().notifyItemRemoved(pos);
        while (recyclerView.isAnimating()) {
            ;
        }
//        getLoaderManager().initLoader(0, ARGS, this);
//        cAdaptador.cambiarLista(array);
//        cAdaptador.notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return COLUMNA;
    }

    @Override
    public void Escuchador(boolean actualizarCursada) {

    }

    @Override
    public void Escuchador(boolean actualizarCursada, int pos) {
        recyclerView.getAdapter().notifyItemRemoved(pos);
    }

    @Override
    public void EsconderTeclado() {

    }

    public interface getPermisos {
        public boolean getPermisos();
    }
}

