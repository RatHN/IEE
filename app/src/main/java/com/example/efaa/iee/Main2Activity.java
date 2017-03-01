package com.example.efaa.iee;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.efaa.iee.Materias.Clase;
import com.example.efaa.iee.Materias.CustomActivities;
import com.example.efaa.iee.adaptadores.ClaseRecyclerAdaptador;
import com.example.efaa.iee.ux.SwipeHellper;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.roughike.bottombar.BottomBar;
import com.stephentuso.welcome.WelcomeHelper;
import com.vansuita.materialabout.builder.AboutBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class Main2Activity extends CustomActivities
        implements NavigationView.OnNavigationItemSelectedListener,
        ClaseRecyclerAdaptador.InterfaceEscuchador, ClaseRecyclerAdaptador.InterfaceSetearIndice,
        PlaceHolderFragment.getPermisos {


    private final String PERMISO = "PERMISO";
    public Loader<ArrayList<Clase>> loaderDisponibles;
    Loader<ArrayList<Clase>> loaderCursadas;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    public boolean Permisos = false;


//    public dataSource DataSource;
    public PlaceHolderFragment placeHolderFragment;
    ArrayList<String> clasesString = new ArrayList<>();
    ArrayList<Clase> clasesClases = new ArrayList<>();
    int uv[] = {12, 14, 16, 20, 22, 24};

    SharedPreferences pref;
    Intent i;
    Bundle b;
    /**
     * RecyclerView que se usará para la lista
     * Agregado por el BottomBar
     */
    public BottomBar mBottomBar;
    RecyclerView mRecycler;
    RecyclerView.LayoutManager mLManager;
    FloatingActionButton fab;
    int unread = 0;


    private AlertDialog dialog1;
    private int totalUV;
    private int totalIndice;
    private DrawerLayout drawer;
    private WelcomeHelper welcomeScreen;
    private boolean reprobadas_aparecen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcomeScreen = new WelcomeHelper(this, Tutorial.class);

//        welcomeScreen.show(savedInstanceState);
//        welcomeScreen.forceShow();


        mRecycler = ((RecyclerView) findViewById(R.id.recicler));
        mLManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLManager);
        mRecycler.setAdapter(null);
        new ItemTouchHelper(new SwipeHellper()).attachToRecyclerView(mRecycler);

//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) || !fileDataBase.exists()) {
//            this.Permisos = false;
//            pref.edit().putBoolean(getString(R.string.permiso), this.Permisos).apply();
////            copiarBD();
//        } else{
//            this.Permisos = true;
//            pref.edit().putBoolean(getString(R.string.permiso), this.Permisos).apply();
//        }


//        this.Permisos =  (fileDataBase.exists());
//        if(!pref.getBoolean(PERMISO, false)) {
//            copiarBD();
//        } else this.Permisos = true;
/*
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar = BottomBar.attachShy((CoordinatorLayout)findViewById(R.id.main_content),
                savedInstanceState);
*/

        mBottomBar = ((BottomBar) findViewById(R.id.bottomBar));
//        mBottomBar.setMaxFixedTabs(1);

//        mBottomBar.noTopOffset();

/*        BottomBarTab disponibles = new BottomBarTab(R.drawable.ic_check_box_outline_blank_black_24dp, "Disponibles");
        BottomBarTab cursadas = new BottomBarTab(R.drawable.ic_check_box_black_24dp, "Cursadas");
        BottomBarTab calc = new BottomBarTab(R.drawable.ic_menu_info, "Calc");
        mBottomBar.setItems(
                disponibles,
                cursadas,
                calc);
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.cardview_dark_background));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");*/

//        unreadClases = mBottomBar.makeBadgeForTabAt(1, "#FF0000", unread);

//        mLManager = new LinearLayoutManager(this);
//        mRecycler.setLayoutManager(mLManager);
//        mRecycler.setAdapter(null);

        /*
         */
        /*placeHolderFragment = new PlaceHolderFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.f, placeHolderFragment);
        fragmentTransaction.commit();
*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle bundle = new Bundle();
        bundle.putInt(COLUMN_ARG, dataSource.Columnas.DISP_INT);
        loaderDisponibles = getSupportLoaderManager().initLoader(LOADER_DISPONIBLES, bundle, Main2Activity.this);
        loaderCursadas = getSupportLoaderManager().initLoader(LOADER_CURSADAS, null, Main2Activity.this);

        mBottomBar.setOnTabSelectListener(tabId -> {
                switch (tabId) {
                    case R.id.tab_disponibles:
                        loaderDisponibles.forceLoad();
                        break;
                    case R.id.tab_cursadas:
                        unread = 0;
                        mBottomBar.getTabWithId(tabId).removeBadge();
                        loaderCursadas.forceLoad();
//                        bundle.putInt(COLUMN_ARG, dataSource.Columnas.CURS_INT);
//                        getSupportLoaderManager().initLoader(LOADER_DISPONIBLES, bundle, Main2Activity.this);
                        break;
                    /*case R.id.tab_calc:
                        BottomBarTab tab = mBottomBar.getTabAtPosition(2);
                        tab.setActiveColor(0xddd);
                        tab.setActiveAlpha((float) 0.6);
                        mBottomBar.selectTabAtPosition(0, true);
                        fab.callOnClick();
                        break;*/
                }
        });

        /**
         * Aqui empieza el incrustamiento masivo de datos masivos que contienen datos mas masivos
         * que la masa de baleadas
         */

        //<editor-fold desc="OLD CODE">
        /*
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
        */
        //</editor-fold>


        //Empieza la customizacion del FAB */
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setBackground(getDrawable(R.drawable.refre));
            fab.setImageDrawable(getDrawable(R.drawable.refre));
        }
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {

            void eSO(String s) {
                Snackbar.make(mRecycler, s,
                        Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onClick(View view) {
//                SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
                final Cursor cursor = DataSource.queryPorCursar(null);
                while (cursor.moveToNext()) {
                    String a = cursor.getString(cursor.getColumnIndex(dataSource.Columnas.NOMBRE));
                    Log.i("TAG", a);
                }

                clasesString.clear();
                clasesClases.clear();
                totalIndice = totalUV = 0;
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder
                        .setMultiChoiceItems(cursor, dataSource.Columnas.DISPONIBLE,
                                dataSource.Columnas.NOMBRE,
                                (dialog, which, isChecked) -> {
                                    Clase clase;
                                    if (cursor.moveToPosition(which)) {
                                        clase = new Clase(cursor.getString(cursor.getColumnIndex(dataSource.Columnas.NOMBRE)),
                                                cursor.getString(cursor.getColumnIndex(dataSource.Columnas.CODIGO)),
                                                null,
                                                cursor.getInt(cursor.getColumnIndex(dataSource.Columnas.UV)),
                                                cursor.getInt(cursor.getColumnIndex(dataSource.Columnas.INDICE)),
                                                true);
                                        if (isChecked) {
                                            if (!clasesString.contains(clase.CODIGO)) {

                                                clasesString.add(clase.CODIGO);
                                                clasesClases.add(clase);

                                                totalIndice += clase.INDICE;
                                                totalUV += clase.UV;

                                            }
//                                                clase.position = clasesString.lastIndexOf(clase);
                                            return;
                                        } else {
                                            for (String clase1 : clasesString) {
                                                if (clase1.compareTo(clase.CODIGO) == 0) {
                                                    int index = clasesString.lastIndexOf(clase1);
                                                    clasesString.remove(index);
                                                    clasesClases.remove(clase);
                                                    totalUV -= clase.UV;
                                                    totalIndice -= clase.INDICE;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                })
                        .setPositiveButton(R.string.go_ahead,
                                (dialog, which) -> {
                                    if (clasesString.size() == 0) {
                                        eSO(getString(R.string.no_classes_selected_warning));
                                        return;
                                    } else if (totalUV > 24) {
                                        eSO(getString(R.string.too_many_classes_selected_warning));
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), clasesString.toString(),
                                            Toast.LENGTH_SHORT).show();
                                    totalIndice = totalIndice / clasesString.size();

                                    if (totalIndice < 40) totalUV = uv[0];      //12
                                    else if (totalIndice < 60) totalUV = uv[1]; //14
                                    else if (totalIndice < 70) totalUV = uv[2]; //16
                                    else if (totalIndice < 80) totalUV = uv[4]; //22
                                    else totalUV = uv[5];                       //24

                                    b = new Bundle();
                                    b.putStringArrayList("Array", clasesString);
                                    b.putInt("totalUV", totalUV);
                                    b.putInt("totalIndice", totalIndice);

                                    reprobadas_aparecen = false;
                                    i = new Intent(getApplicationContext(), ChuncheActivity.class);

                                    DialogInterface.OnClickListener op = (dialog2, which1) -> {
                                        b.putBoolean("reprobadas_aparecen", which1 == BUTTON_POSITIVE);
                                        i.putExtras(b);

                                        startActivity(i);
                                    };
                                    AlertDialog p = new AlertDialog.Builder(dialog1.getContext()).setMessage(getString(R.string.q_show_reprobadas))
                                            .setTitle(R.string.almost_in_chunche_title)
                                            .setNegativeButton(R.string.no, op)
                                            .setPositiveButton(R.string.si, op).create();

                                    p.show();

//                                        b.putBoolean("reprobadas_aparecen", reprobadas_aparecen);

                                })
                        .setTitle(getResources().getString(R.string.mensaje_calculador))
                        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
                if (cursor.getCount() == 0) {
                    builder.setMessage(R.string.no_approved_classes_msg);
                }

                dialog1 = builder.create();
                dialog1.setIcon(R.mipmap.ic_launcher);
//                dob.releaseReference();

                DialogInterface.OnClickListener op = (dialog, which) -> dialog1.show();
                AlertDialog p1 = new AlertDialog.Builder(view.getContext()).setMessage(getString(R.string.introduce_grades_reminder))
                        .setTitle(R.string.cancel_no_grades_title)
                        .setNegativeButton(R.string.cancel_no_grades, null)
                        .setPositiveButton(R.string.no_grades_ignore, op).create();
                p1.show();

            }
        });
        fab.setVisibility(View.INVISIBLE);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        runTutorial(0);
    }

    private void runTutorial(final int index) {
                ViewTarget bottomBarTarget = new ViewTarget(mBottomBar);
        ViewTarget placeHolderTarget = new ViewTarget(mRecycler);
        ViewTarget rectTarget = new ViewTarget(fab);

        final Target[] targets = {bottomBarTarget, placeHolderTarget, rectTarget};
        String[] texts = {getString(R.string.welcome_bottomBar),
                getString(R.string.welcome_listaDeClases),
                getString(R.string.welcome_calc)};
        int[] stilosShowcase = {R.style.ShowcaseUNAH, R.style.ShowcaseLista, R.style.ShowcaseUNAH};

        Button button = new Button(getApplicationContext());
        button.setVisibility(View.GONE);
        new ShowcaseView.Builder(this)
                .setTarget(targets[index])
                .setContentTitle(getString(R.string.welcome_welcome))
                .setContentText(texts[index])
                .hideOnTouchOutside()
                .replaceEndButton(button)
                .setStyle(stilosShowcase[index])
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                        try {
                            runTutorial(index + 1);
                        } catch (Exception ignored) {
                        }

                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                    }
                })
                .build();
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
        MenuItem item = menu.getItem(0);
        Drawable icon = item.getIcon();
        icon.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        item.setIcon(icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editar) {
            ((ClaseRecyclerAdaptador) mRecycler.getAdapter()).cambiarLista(
                    ((ClaseRecyclerAdaptador) mRecycler.getAdapter())
                            .LISTA_EDITADA);
            mRecycler.getAdapter().notifyDataSetChanged();

            return true;
        }

        if (id == R.id.no_editar) {
            ((ClaseRecyclerAdaptador) mRecycler.getAdapter()).cambiarLista(
                    ((ClaseRecyclerAdaptador) mRecycler.getAdapter())
                            .LISTA_NO_EDITADA);
            mRecycler.getAdapter().notifyDataSetChanged();

            return true;
        }

        if (id == R.id.calc){
            fab.callOnClick();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.informacion) {
            PDFOpen(id);
        } else if (id == R.id.plan) {
            PDFOpen(id);
        } else if (id == R.id.face) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/123808777743723/?__mref=message_bubble")));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.pdf_otro) {
//            PDFOpen(id);
            Snackbar.make(drawer, "Falta implementar... Esperando el PDF de Emilson",
                    Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.bug_report) {
            try {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "neryortez@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comentarios");

                startActivity(Intent.createChooser(emailIntent, "Enviar comentarios por correo"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
        } else if (id == R.id.about) {
            View view = AboutBuilder.with(this)
                    .setPhoto(R.mipmap.profile_picture)
                    .setCover(R.mipmap.profile_cover)
                    .setName(getString(R.string.aboutMe_name))
                    .setSubTitle(getString(R.string.aboutMe_title))
                    .setBrief(getString(R.string.aboutMe_brief))

                    .setAppIcon(R.mipmap.ic_launcher)
                    .setAppName(R.string.app_name)
                    .addFiveStarsAction()
                    .setVersionAsAppTitle()
                    .addShareAction(R.string.app_name)

                    .addGooglePlayStoreLink("8002078663318221363")
                    .addGitHubLink("RatHN")
                    .addFacebookLink("abinadiortez")
                    .addEmailLink("neryortez@gmail.com", "UNAH APLICACIONES", "Me gusta esta aplicacion porque.....")


//                    .setWrapScrollView(true)
                    .setLinksAnimated(true)
                    .setShowDivider(false)
                    .build();

            new AlertDialog.Builder(this).setView(view).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void Escuchador(boolean actualizarCursada) {
//        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public void Escuchador(boolean actualizarCursada, int pos) {
//        mSectionsPagerAdapter.update(actualizarCursada, pos);
        Clase clase1;

        clase1 = ((ClaseRecyclerAdaptador) mRecycler.getAdapter()).LISTA.get(pos);
        clase1.CURSADA = true;
        ((ClaseRecyclerAdaptador) mRecycler.getAdapter()).LISTA.remove(pos);
        mRecycler.getAdapter().notifyItemRemoved(pos);

        if (!actualizarCursada) {
            if (mBottomBar.getCurrentTabPosition() == 0) {
                unread += 1;
                /*unreadClases.setCount(unread);
                unreadClases.setAnimationDuration(200);
                unreadClases.show();*/
                mBottomBar.getTabAtPosition(1).setBadgeCount(unread);
//                getSupportLoaderManager().initLoader(LOADER_DISPONIBLES, bundle, Main2Activity.this).forceLoad();
                loaderDisponibles.forceLoad();
            }
        }

//        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void EsconderTeclado() {

    }

    @Override
    public void setearIndice(final String selection, final String[] selectionArgs, final ContentValues values, final View v) {
        Runnable runnable = new Runnable() {
            public void run() {
                DataSource.getWritableDatabase().update(dataSource.TABLE, values, selection, selectionArgs);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()).setMessage(
                        "Guardado exitosamente")
                        .setPositiveButton("OK", null);
                dialog1 = builder.create();
                dialog1.show();
            }
        };
        new Thread(runnable).run();
//        Snackbar.make(findViewById(R.id.drawer_layout)/*this.placeHolderFragment.getView()*/, "Guardado exitosamente", Snackbar.LENGTH_SHORT).show();
    }

//    private boolean copiarBD() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Permisos = false;
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
//                    123);
//        } else {
//            pref.edit().putBoolean(getString(R.string.permiso), true).apply();
//            File fileDataBase = new File(dbPath);
//            if (!fileDataBase.exists()) {
//                int ID = R.raw.data;
//                File o = new File("/sdcard/UNAH_IEE/");
//                o.mkdirs();
//                CopyRaw(ID, "data.sqlite");
//                Log.d("DB", "Database created");
//                this.Permisos = true;
//            } else {
//                try {
//                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
//                    Cursor c = db.rawQuery("SELECT indice FROM clases", null);
//                    if (c.getCount() < 2) {
//                        db.execSQL("ALTER TABLE clases ADD COLUMN \"indice\" INTEGER NOT NULL DEFAULT 0");
//                        Log.d("DB", "Database altered");
//                        this.Permisos = true;
//                    }
//                    c.close();
//                    db.close();
//                } catch (SQLiteException e) {
//                    e.printStackTrace();
//                    (SQLiteDatabase.openOrCreateDatabase(dbPath, null))
//                            .execSQL("ALTER TABLE clases ADD COLUMN \"indice\" INTEGER NOT NULL DEFAULT 0");
//                    Log.d("DB", "Database altered");
//                    this.Permisos = true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }

    public void PDFOpen(int id) {
        int ID = 0;
        File fileBrochure = null;
        String filename = null;

        if (id == R.id.informacion) {
            fileBrochure = new File("/sdcard/UNAH_IEE/electrica.pdf");
            filename = "electrica.pdf";
            fileBrochure = new File(getExternalFilesDir(null), filename);
            ID = R.raw.electrica;
        } else if (id == R.id.plan) {
            filename = "plan.pdf";
            fileBrochure = new File("/sdcard/UNAH_IEE/plan.pdf");
            fileBrochure = new File(getExternalFilesDir(null), filename);
            ID = R.raw.plan;
        }

        if (!fileBrochure.exists()) {
            CopyRaw(ID, filename);
        }

        /* PDF reader code */
//        File file = new File("/sdcard/UNAH_IEE/" + filename);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(fileBrochure.getAbsoluteFile()), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay aplicacion para archivos PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Metdo para copiar archivos RAW
     **/
    private void CopyRaw(int idRaw, String filename) {
        InputStream in = null;
        try {
            in = getResources().openRawResource(idRaw);
        } catch (Exception e) {
            Toast.makeText(this, "Error IO", Toast.LENGTH_SHORT).show();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(getExternalFilesDir(null), filename));
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    pref.edit().putBoolean(getString(R.string.permiso), true).apply();
//                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
//                    finish();
//                    copiarBD();
                } else {
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    // 2. Chain together various setter methods to set the dialog characteristics

                    builder.setMessage("La aplicacion no puede continuar sin permisos de leer Y " +
                            "escribir en memoria").setPositiveButton("Intentar Nuevamente",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    copiarBD();
                                }
                            })
                            .setTitle("ERROR")
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Toast.makeText(getApplicationContext(), "La aplicacion no puede continuar sin permisos de leer Y " +
                                            "escribir en memoria", Toast.LENGTH_LONG).show();
                                }
                            });
                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                break;
        }
    }

    @Override
    public boolean getPermisos() {
        return Permisos;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Necessary to the Tutorial activity

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
//        mBottomBar.onSaveInstanceState();
    }


    @Override
    public Loader<ArrayList<Clase>> onCreateLoader(int id, final Bundle args) {
        Log.i("Llegamos", "About to create Loader");
        switch (id) {
            case LOADER_DISPONIBLES:
                Log.i("Llegamos", "Loader Created!");
                return new AsyncTaskLoader<ArrayList<Clase>>(this) {
                    @Override
                    public ArrayList<Clase> loadInBackground() {
                        ArrayList<Clase> array;
                        if (!DataSource.isOpen()) {
                            DataSource.open();
                        }
                        array = DataSource
                                .queryPasadasODisponibles(dataSource.Columnas.DISPONIBLE, "1", Main2Activity.this);
                        return array;
                    }
                };
            case LOADER_CURSADAS:
                Log.i("Llegams", "Loader Cursadas Created!");
                return new AsyncTaskLoader<ArrayList<Clase>>(this) {
                    @Override
                    public ArrayList<Clase> loadInBackground() {
                        ArrayList<Clase> array;
                        if (!DataSource.isOpen()) {
                            DataSource.open();
                        }
                        array = DataSource
                                .queryPasadasODisponibles(dataSource.Columnas.CURSADA, "1", Main2Activity.this);
                        return array;
                    }
                };
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Clase>> loader, ArrayList<Clase> data) {
        Log.e("Recibido", "Loader fully loaded");
        mRecycler.setAdapter(new ClaseRecyclerAdaptador(data, this.DataSource));
        mRecycler.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Clase>> loader) {
        mRecycler.swapAdapter(null, true);
    }

    public boolean isInDisponibles() {
        return mBottomBar.getCurrentTabPosition() != 1;
    }

    public void runLoader(boolean inDisponibles) {
        if (inDisponibles) loaderDisponibles.forceLoad();
        else loaderCursadas.forceLoad();
    }
}