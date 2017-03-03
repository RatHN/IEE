package com.example.efaa.iee;

import android.support.v4.app.Fragment;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.FragmentWelcomePage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by Neri Ortez on 23/02/2017.
 */
public class Tutorial extends WelcomeActivity {
    /*@Override
    protected void cancelWelcomeScreen() {
        super.cancelWelcomeScreen();
        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    protected void completeWelcomeScreen() {
        super.completeWelcomeScreen();
        startActivity(new Intent(this, Main2Activity.class));
    }
*/
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.ic_launcher,
                        getString(R.string.app_name)).parallax(true)
                )
                .page(new BasicPage(R.drawable.tres_clases,
                        "Vista rápida",
                        "Tus clases se representan en una lista con los datos necesarios. Tambien" +
                                " puedes modificar tu indice por clase."))
                .page(new BasicPage(R.drawable.bottom_bar,
                        "Orden",
                        "La barra inferior te permite ver por separado tus clases cursadas y las " +
                                "que estan disponibles. (Cuando indicas que una clase ha sido cursada" +
                                " apareceran más clases en \"Disponibles\")")
                        .background(R.color.colorPrimary)
                )
                .page(new FragmentWelcomePage() {
                          @Override
                          protected Fragment fragment() {
                              return WelcomeSlidingFragment.newInstance(null, null);
                          }
                      }
                )
                .page(new BasicPage(R.drawable.ic_menu_info,
                        "Planéa tu futuro",
                        "Con la herramienta de planificacion puedes prepararte para tu próximo " +
                                "periodo y conocer cuantas clases estarían disponibles para ti dependiendo de tu índice academico"))
                .swipeToDismiss(true)
                .build();
    }
}
