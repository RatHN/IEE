package com.example.efaa.iee;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Neri Ortez on 01/03/2017.
 */

public class EspecificUtils {
    public static boolean onNavigationItemSelected(int id, Context context) {
        if (id == R.id.informacion) {
            PDFOpen(id, context);
            return true;
        } else if (id == R.id.plan) {
            PDFOpen(id, context);
            return true;
        }
        return false;
    }

    public static void PDFOpen(int id, Context context) {
        int ID = 0;
        File fileBrochure = null;
        String filename = null;

        if (id == R.id.informacion) {
            filename = "electrica.pdf";
            fileBrochure = new File(context.getExternalFilesDir(null), filename);
            ID = R.raw.electrica;
        } else if (id == R.id.plan) {
            filename = "plan.pdf";
            fileBrochure = new File(context.getExternalFilesDir(null), filename);
            ID = R.raw.plan;
        }

        if (!fileBrochure.exists()) {
            CopyRaw(ID, filename, context);
        }

        /* PDF reader code */
//        File file = new File("/sdcard/UNAH_IEE/" + filename);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(fileBrochure.getAbsoluteFile()), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No hay aplicacion para archivos PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    /**
     * Metdo para copiar archivos RAW
     **/
    private static void CopyRaw(int idRaw, String filename, Context context) {
        InputStream in = null;
        try {
            in = context.getResources().openRawResource(idRaw);
        } catch (Exception e) {
            Toast.makeText(context, "Error IO", Toast.LENGTH_SHORT).show();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(context.getExternalFilesDir(null), filename));
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
