package com.example.efaa.iee;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    final String dbPath = "/sdcard/UNAH_IEE/data.sqlite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File fileDataBase = new File(dbPath);
        if (!fileDataBase.exists()) {
            int ID = R.raw.data;
            File o = new File("/sdcard/UNAH_IEE/");
            o.mkdirs();
            CopyRaw(ID, "data.sqlite");
        }

//        mActionBarToolbar = (Toolbar) findViewById(R.id.);
//        setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setTitle("My title");
    }

    private void CopearBaseDatos() {

    }

    public void PDFOpen(View view) {
        int ID = 0;
        File fileBrochure = null;
        String filename = null;

        if (view.getId() == R.id.bot) {
            fileBrochure = new File("/sdcard/UNAH_IEE/electrica.pdf");
            filename = "electrica.pdf";
        } else if (view.getId() == R.id.bot2) {
//            Toast.makeText(this, "UJN", Toast.LENGTH_SHORT).show();
            fileBrochure = new File("/sdcard/UNAH_IEE/plan.pdf");
            filename = "plan.pdf";
        }


        if (!fileBrochure.exists()) {
//			Toast.makeText(this, "archivo no existe.... creando", Toast.LENGTH_SHORT).show();
//			CopyAssetsbrochure();

            if (view.getId() == R.id.bot) ID = R.raw.electrica;
            else if (view.getId() == R.id.bot2) ID = R.raw.plan;
            CopyRaw(ID, filename);
        }

        /** PDF reader code */
        File file = new File("/sdcard/UNAH_IEE/" + filename);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay aplicacion para archivos PDF", Toast.LENGTH_SHORT).show();
        }
    }


    //metodo para RAW
    private void CopyRaw(int idRaw, String filename) {
        InputStream in = null;
        try {
            in = getResources().openRawResource(idRaw);
        } catch (Exception e) {
            Toast.makeText(this, "Error IO", Toast.LENGTH_SHORT).show();
        }
        OutputStream out = null;
        try {
//            Toast.makeText(this, "entrando try", Toast.LENGTH_SHORT).show();
            out = new FileOutputStream("/sdcard/UNAH_IEE/" + filename);
//            Toast.makeText(this, "listo para copiar", Toast.LENGTH_SHORT).show();
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

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure(String filename) {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");

        } catch (IOException e) {
            Toast.makeText(this, "assets no encontrado", Toast.LENGTH_SHORT).show();
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
//			Toast.makeText(this, "un archivo "+files[i], Toast.LENGTH_SHORT).show();
            String fStr = files[i];
            if (fStr.equalsIgnoreCase("electrica.pdf")) {

                InputStream in = null;
                OutputStream out = null;
                try {
                    Toast.makeText(this, "archivo encontrado", Toast.LENGTH_SHORT).show();
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream("/sdcard/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    public void openFacebook(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/123808777743723/?__mref=message_bubble")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
        }

    }

    public void openCalc(View view) {
//        Toast.makeText(this, "El visor logaritmico automatizado manualmente super avanzado de clases: No se ha implementado aún", Toast.LENGTH_SHORT).show();
        Intent intento = new Intent(this, ActividadCalculador.class);
        intento.putExtra("CurPas", Estados.DISPONIBLES);

        startActivity(intento);

    }
}

