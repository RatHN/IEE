package com.example.efaa.iee;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ClaseRecyclerAdaptador.InterfaceEscuchador {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;


    dataSource DataSource = new dataSource();
    SQLiteDatabase dob;
    AlertDialog dialog1;
    ArrayList<Clase> clasesParaDialog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Aqui empieza el incrustamiento masivo de datos masivos que contienen datos mas masivos
         * que la masa de baleadas
         */

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Cuando se seleccione una pestana....
            @Override
            public void onPageSelected(int position) {
                // Establecer todos los flag como negativos
                for (PlaceHolderFragment fragment :
                        mSectionsPagerAdapter.fragments) {
                    fragment.flag = false;
                }
                // Evitar IndexOutOfBoundsException
                if (mSectionsPagerAdapter.fragments.size() != 0) {
                    // Verificar en pestana nos encontramos
                    PlaceHolderFragment fragment = mSectionsPagerAdapter.fragments.get(position);
                    String titulo = (String) mSectionsPagerAdapter.getPageTitle(position);
                    switch (titulo) {
                        // Si es en la pestana de cursadas...
                        case SectionsPagerAdapter.CURSADAS:
                            // Evitar que se actualize
                            fragment.flag = true;
                            return;
                    }
                    // Si es cualquier otra pestana, hacer que se actualizen frente a cambios en las clases
                    fragment.flag = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //
        // Aqui se detiene la masividad masiva recursiva de recursos
        /*
        * Empieza la customizacion del FAB */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setBackground(getDrawable(R.drawable.refre));
            fab.setImageDrawable(getDrawable(R.drawable.refre));
        }
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {

            public void eSO() {
                Snackbar.make(getCurrentFocus(), "No ha seleccionado ninguna clase",
                        Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onClick(View view) {
                SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
                final Cursor cursor = DataSource.queryPorCursar(dob, null);
                while (cursor.moveToNext()) {
                    String a = cursor.getString(cursor.getColumnIndex(dataSource.Columnas.NOMBRE));
                    Log.i("TAG", a);
                }

                clasesParaDialog.clear();
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder
                        .setMultiChoiceItems(cursor, dataSource.Columnas.DISPONIBLE,
                                dataSource.Columnas.NOMBRE,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        Clase clase = new Clase();
                                        if (cursor.moveToPosition(which)) {
                                            clase = new Clase(cursor.getString(cursor.getColumnIndex(dataSource.Columnas.NOMBRE)),
                                                    cursor.getString(cursor.getColumnIndex(dataSource.Columnas.CODIGO)),
                                                    null,
                                                    cursor.getInt(cursor.getColumnIndex(dataSource.Columnas.UV)),
                                                    cursor.getInt(cursor.getColumnIndex(dataSource.Columnas.INDICE)),
                                                    true);
                                            if (isChecked) {
                                                clasesParaDialog.add(clase);
                                                clase.position = clasesParaDialog.lastIndexOf(clase);
                                            } else {
                                                for (Clase clase1 : clasesParaDialog) {
                                                    if (clase1.NOMBRE.compareTo(clase.NOMBRE) == 0) {
                                                        int index = clasesParaDialog.lastIndexOf(clase1);
                                                        clasesParaDialog.remove(index);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                })
//                        .setMessage(getResources().getString(R.string.mensaje_calculador))
                        .setPositiveButton("Adelante",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intento = new Intent(getApplicationContext(), ActividadCalculador.class);
//                                        intento.putExtra("CurPas", "Cursar");
//                                        onStop();
//                                        startActivity(intento);

//                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
//                                        builder1.setMessage(clasesParaDialog.toString());
//                                        AlertDialog dialog1 = builder1.create();
//                                        dialog1.show();
                                        if (clasesParaDialog.size() == 0) {
                                            eSO();
                                            return;
                                        }
                                        Toast.makeText(getApplicationContext(), clasesParaDialog.toString(),
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                                    }
                                })
                        .setTitle(getResources().getString(R.string.mensaje_calculador))
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialog1 = builder.create();
                dialog1.setIcon(R.mipmap.r04);
                dialog1.show();
                dob.releaseReference();

            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.informacion) {
            // Handle the camera action
        } else if (id == R.id.plan) {

        } else if (id == R.id.face) {

        } else if (id == R.id.pdf_otro) {

        } else if (id == R.id.bug_report) {

        } else if (id == R.id.rate) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void Escuchador(boolean actualizarCursada) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        android.support.v4.app.Fragment fragment = mSectionsPagerAdapter.getItem(0);
//        transaction.replace(R.id.reciclador, fragment).commit();
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

}
