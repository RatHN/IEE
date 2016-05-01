package com.example.efaa.iee;

import android.app.Fragment;

import com.example.efaa.iee.dataSource;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.ActionBarContainer;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.example.efaa.iee.dataSource.Columnas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActividadCalculador extends AppCompatActivity implements ClassFragment.OnFragmentInteractionListener {


    public String ESTADO = null;
    public String ColumnaAUSAR = null;
    SQLiteDatabase dob = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public void onFragmentInteraction(Uri uri) {
        int op = 10;

    }


    public void checkClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        LinearLayout e = (LinearLayout) view.getParent();
        TextView codigo = (TextView) e.findViewById(R.id.Codigo);
        String cod = (String) codigo.getText();
        String dato = null;

        if (checkBox.isChecked()) {
            dato = "1";
        } else {
            dato = "0";
        }
        if (new dataSource().insertarUnoOCero(dob, cod, Columnas.CURSADA, dato,
                new dataSource().queryCrearClase(dob, cod, this), this) < 0) {
            checkBox.setChecked(true);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String TABLE = getString(R.string.TABLE);
        String NOMBRE = getString(R.string.cNombre);
        String CODIGO = getString(R.string.codigo);
        String CURSADA = getString(R.string.cursada);
        String PORcURSAR = getString(R.string.porCursar);
        dataSource data = new dataSource();
        dataSource.Columnas colum = new dataSource.Columnas();

        setContentView(R.layout.activity_actividad_calculador);
        ESTADO = getIntent().getExtras().getString("CurPas");
        Button botn = (Button) findViewById(R.id.button2);
        String textoBoton = "IR A";
        if (ESTADO.compareTo("Cursar") == 0) {
            ColumnaAUSAR = Columnas.DISPONIBLE;
            botn.setText("IR A CURSADAS");
        } else {
            ColumnaAUSAR = Columnas.CURSADA;
            botn.setText("IR A DISPONIBLES");
        }


        //Intento de abrir una base de datos//
        dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
        final String ListaClases[] = data.queryClasesString(dob, ColumnaAUSAR, "1", this);
        final ArrayList<Clase> listaClases = data.queryPasadasODisponibles(dob, ColumnaAUSAR, "1", this);


        int[] lista;

        lista = framesListBuilder();
        final int[] lst = lista;

        new Thread(new Runnable() {
            public void run() {

                FragmentTransaction trans = fragmentInflater(lst, listaClases);
                trans.commit();

            }
        }).start();

        botn.setFocusableInTouchMode(true);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public int[] framesListBuilder() {
        int[] p = new int[100];

        p[0] = R.id.fragment;
        p[1] = R.id.frameLayout;
        p[2] = R.id.frameLayout2;
        p[3] = R.id.frameLayout3;
        p[4] = R.id.frameLayout4;
        p[5] = R.id.frameLayout5;
        p[6] = R.id.frameLayout6;
        p[7] = R.id.frameLayout7;
        p[8] = R.id.frameLayout8;
        p[9] = R.id.frameLayout9;
        p[10] = R.id.frameLayout10;
        p[11] = R.id.frameLayout11;
        p[12] = R.id.frameLayout12;
        p[13] = R.id.frameLayout13;
        p[14] = R.id.frameLayout14;
        p[15] = R.id.frameLayout15;
        p[16] = R.id.frameLayout16;
        p[17] = R.id.frameLayout17;
        p[18] = R.id.frameLayout18;
        p[19] = R.id.frameLayout19;
        p[20] = R.id.frameLayout20;
        p[21] = R.id.frameLayout21;
        p[22] = R.id.frameLayout22;
        p[23] = R.id.frameLayout23;
        p[24] = R.id.frameLayout24;
        p[25] = R.id.frameLayout25;
        p[26] = R.id.frameLayout26;
        p[27] = R.id.frameLayout27;
        p[28] = R.id.frameLayout28;
        p[29] = R.id.frameLayout29;
        p[30] = R.id.frameLayout30;
        p[31] = R.id.frameLayout31;
        p[32] = R.id.frameLayout32;
        p[33] = R.id.frameLayout33;
        p[34] = R.id.frameLayout34;
        p[35] = R.id.frameLayout35;
        p[36] = R.id.frameLayout36;
        p[37] = R.id.frameLayout37;
        p[38] = R.id.frameLayout38;
        p[39] = R.id.frameLayout39;
        p[40] = R.id.frameLayout40;
        p[41] = R.id.frameLayout41;
        p[42] = R.id.frameLayout42;
        p[43] = R.id.frameLayout43;
        p[44] = R.id.frameLayout44;
        p[45] = R.id.frameLayout45;
        p[46] = R.id.frameLayout46;
        p[47] = R.id.frameLayout47;
        p[48] = R.id.frameLayout48;
        p[49] = R.id.frameLayout49;
        p[50] = R.id.frameLayout50;
        p[51] = R.id.frameLayout51;
        p[52] = R.id.frameLayout52;
        p[53] = R.id.frameLayout53;
        p[54] = R.id.frameLayout54;
        p[55] = R.id.frameLayout55;
        p[56] = R.id.frameLayout56;
        p[57] = R.id.frameLayout57;
        p[58] = R.id.frameLayout58;
        p[59] = R.id.frameLayout59;
        p[60] = R.id.frameLayout60;
        p[61] = R.id.frameLayout61;
        p[62] = R.id.frameLayout62;
        p[63] = R.id.frameLayout63;
        p[64] = R.id.frameLayout64;
        p[65] = R.id.frameLayout65;
        p[66] = R.id.frameLayout66;
        p[67] = R.id.frameLayout67;
        p[68] = R.id.frameLayout68;
        p[69] = R.id.frameLayout69;
        p[70] = R.id.frameLayout70;
        p[71] = R.id.frameLayout71;
        p[72] = R.id.frameLayout72;
        p[73] = R.id.frameLayout73;
        p[74] = R.id.frameLayout74;
        p[75] = R.id.frameLayout75;
        p[76] = R.id.frameLayout76;
        p[77] = R.id.frameLayout77;
        p[78] = R.id.frameLayout78;
        p[79] = R.id.frameLayout79;
        p[80] = R.id.frameLayout80;
        p[81] = R.id.frameLayout81;
        p[82] = R.id.frameLayout82;
        p[83] = R.id.frameLayout83;
        p[84] = R.id.frameLayout84;
        p[85] = R.id.frameLayout85;
        p[86] = R.id.frameLayout86;
        p[87] = R.id.frameLayout87;
        p[88] = R.id.frameLayout88;


        return p;
    }

    public FragmentTransaction fragmentInflater(int[] lista, ArrayList<Clase> listaClases) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int firstDay = 0;

        for (int o = 0; firstDay < listaClases.size(); firstDay++) {
            int frame = lista[firstDay];
            ClassFragment newFragment = new ClassFragment(listaClases.get(firstDay));
            Bundle bundle1 = new Bundle();

            bundle1.putString(ClassFragment.CODIGO_CLASE, listaClases.get(firstDay).CODIGO);
            bundle1.putString(ClassFragment.NOMBRE_DE_CLASE, listaClases.get(firstDay).NOMBRE);
            if (ESTADO.compareTo("Cursar") == 0) {
                bundle1.putBoolean(ClassFragment.ACTIVADO, false);
            } else {
                bundle1.putBoolean(ClassFragment.ACTIVADO, true);
            }

            newFragment.setArguments(bundle1);
            transaction.replace(frame, newFragment);
            transaction.addToBackStack(null);
        }
        return transaction;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, //
                "ActividadCalculador Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.efaa.iee/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActividadCalculador Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.efaa.iee/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void MostrarCursadas(View view) {
        Intent intento = new Intent(this, ActividadCalculador.class);
        if (ESTADO.compareTo("Cursar") == 0) {
            intento.putExtra("CurPas", "Cursada");
        } else {
            intento.putExtra("CurPas", "Cursar");
        }

        finish();
        startActivity(intento);
    }

    public void Actualizar(View view) {
        Intent intento = new Intent(this, ActividadCalculador.class);
        intento.putExtra("CurPas", "Cursar");
        finish();
        onStop();
        startActivity(intento);
    }
}
