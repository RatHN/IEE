package com.example.efaa.iee.Materias;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.efaa.iee.dataSource;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Neri Ortez on 25/02/2017.
 */
public abstract class CustomActivities extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Clase>>{
    public dataSource DataSource;

    protected static final int LOADER_DISPONIBLES = 987;
    protected static final int LOADER_CURSADAS = 0000;
    protected static final java.lang.String COLUMN_ARG = "columna_en_el_argumento_de_Loader";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            DataSource = new dataSource(this);
            DataSource.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataSource.close();
    }

    @Override
    protected void onResume() {
        super.onStart();
        DataSource.open();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataSource.open();
    }

    public dataSource getBD(){
        return this.DataSource;
    }

    void setDB(dataSource DataSource){
        this.DataSource = DataSource;
    }

}
