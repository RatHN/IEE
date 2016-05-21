package com.example.efaa.iee;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChuncheActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Clase>>,
        AdaptadorDeDisponibles.Comm, AdaptadorDeInfo.CommInfo {
    RecyclerView Disponibles;
    AdaptadorDeDisponibles dAdapter;

    RecyclerView Info;
    AdaptadorDeInfo iAdapter;

    RecyclerView.LayoutManager lManager;
    ArrayList<String> array;
    ArrayList<Clase> arrayClases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        array = getIntent().getExtras().getStringArrayList("Array");

        setContentView(R.layout.activity_cunche);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Info = (RecyclerView) findViewById(R.id.recyclerInfo);
//        Info.setHasFixedSize(true);
        Info.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        Info.setLayoutManager(new LinearLayoutManager(this));
        iAdapter = new AdaptadorDeInfo(new ArrayList<Clase>());

        Disponibles = ((RecyclerView) findViewById(R.id.recyclerDisponibles));
        Disponibles.setHasFixedSize(true);

//        lManager = new LinearLayoutManager(this);
        lManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        Disponibles.setLayoutManager(lManager);

        dAdapter = new AdaptadorDeDisponibles(new ArrayList<Clase>());
        Disponibles.setAdapter(dAdapter);
//        ListView expan = (ListView) findViewById(R.id.expan);
//        expan.setAdapter(new ArrayAdapter<String>(array));
//        expan.setAdapter(new ListaAdaptador(getApplicationContext(), array));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<Clase>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Clase>>(this) {
            @Override
            public void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<Clase> loadInBackground() {
                ArrayList array;
                SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
                array = dataSource.queryPasadasODisponibles(dob, dataSource.Columnas.DISPONIBLE, "1", getContext());
                dob.releaseReference();
                return array;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Clase>> loader, ArrayList<Clase> data) {
        dAdapter = new AdaptadorDeDisponibles(data);
        dAdapter.notifyDataSetChanged();
        Disponibles.swapAdapter(dAdapter, true);
//        recyclerDisponibles.setAdapter(dAdapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Clase>> loader) {
        Disponibles.setAdapter(null);
    }

    @Override
    public void agregarAInfo(Clase clase, int pos) {
        Boolean t = ((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.remove(clase);
        Disponibles.getAdapter().notifyItemRemoved(pos);
        if (Info.getAdapter() != null) {
            ((AdaptadorDeInfo) Info.getAdapter()).LISTA.add(clase);
            Info.getAdapter().notifyItemInserted(((AdaptadorDeInfo) Info.getAdapter()).LISTA.lastIndexOf(clase));
        } else {

            ArrayList<Clase> array = new ArrayList<>();
            array.add(clase);
            Info.setAdapter(new AdaptadorDeInfo(array));
        }
    }

    @Override
    public void agregarADisponibles(Clase clase, int pos) {
        Boolean t = ((AdaptadorDeInfo) Info.getAdapter()).LISTA.remove(clase);
        Info.getAdapter().notifyItemRemoved(pos);
        if (Disponibles.getAdapter() != null) {
            ((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.add(clase);
            Disponibles.getAdapter().notifyItemInserted(((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.lastIndexOf(clase));
        } else {
            ArrayList<Clase> array = new ArrayList<>();
            array.add(clase);
            Disponibles.setAdapter(new AdaptadorDeInfo(array));
        }
    }
}
