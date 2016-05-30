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
    int totalIndice;
    int totalUV;
    TextView tu;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*for (Clase clase1 :
                dAdapter.LISTA) {
            arra
        }
        outState.putStringArray("Array", dAdapter.LISTA.toArray(array));*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        array = getIntent().getExtras().getStringArrayList("Array");
        totalIndice = getIntent().getExtras().getInt("totalIndice");
        totalUV = getIntent().getExtras().getInt("totalUV");


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
//        Disponibles.setHasFixedSize(true);

//        lManager = new LinearLayoutManager(this);
        lManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        Disponibles.setLayoutManager(lManager);

        dAdapter = new AdaptadorDeDisponibles(new ArrayList<Clase>());
        Disponibles.setAdapter(dAdapter);
//        ListView expan = (ListView) findViewById(R.id.expan);
//        expan.setAdapter(new ArrayAdapter<String>(array));
//        expan.setAdapter(new ListaAdaptador(getApplicationContext(), array));

        /*TextView ti = (TextView) findViewById(R.id.t);
        ti.setText("Indice= " + totalIndice );*/
        tu = (TextView) findViewById(R.id.tu);
        tu.setText(R.string.UVDisponibles);
        tu.setText(String.valueOf(tu.getText()) + "\n" + String.valueOf(totalUV));
        tu.setTextSize(28);
        if (Info.getAdapter() == null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Loader<ArrayList<Clase>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Clase>>(this) {
            @Override
            public void onStartLoading() {
                if (Info.getAdapter() != null) return;
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
        if (clase.UV <= totalUV) {
            totalUV -= clase.UV;
            AdaptadorDeDisponibles adapter = (AdaptadorDeDisponibles) Disponibles.getAdapter();
            Boolean t = adapter.LISTA.remove(clase);
            Disponibles.getAdapter().notifyItemRemoved(pos);


            tu.setText(R.string.UVDisponibles);
            tu.setText(String.valueOf(tu.getText()) + "\n" + String.valueOf(totalUV));

            for (Clase clase1 :
                    adapter.LISTA) {
    //            if (clase1.UV > totalUV) clase1.CURSADA = true;
            }

            if (Info.getAdapter() != null) {
                ((AdaptadorDeInfo) Info.getAdapter()).LISTA.add(clase);
                Info.getAdapter().notifyItemInserted(((AdaptadorDeInfo) Info.getAdapter()).LISTA.lastIndexOf(clase));
            } else {
                ArrayList<Clase> array = new ArrayList<>();
                array.add(clase);
                Info.setAdapter(new AdaptadorDeInfo(array));
            }
        } else Snackbar.make(tu, "No se puede che", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void agregarADisponibles(Clase clase, int pos) {
        if (true) {
            Boolean t = ((AdaptadorDeInfo) Info.getAdapter()).LISTA.remove(clase);
            Info.getAdapter().notifyItemRemoved(pos);

            totalUV += clase.UV;
            tu.setText(R.string.UVDisponibles);
            tu.setText(String.valueOf(tu.getText()) + "\n"
                    + String.valueOf(totalUV));

            for (Clase clase1 :
                    ((AdaptadorDeInfo) Info.getAdapter()).LISTA) {
    //            if (clase1.UV > totalUV) clase1.CURSADA = true;
            }

            if (Disponibles.getAdapter() != null) {
                ((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.add(clase);
                Disponibles.getAdapter().notifyItemInserted(((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.lastIndexOf(clase));
            } else {
                ArrayList<Clase> array = new ArrayList<>();
                array.add(clase);
                Disponibles.setAdapter(new AdaptadorDeDisponibles(array));
            }
        }
    }
}
