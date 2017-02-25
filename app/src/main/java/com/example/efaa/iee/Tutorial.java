package com.example.efaa.iee;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.FragmentWelcomePage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by Neri Ortez on 23/02/2017.
 */
public class Tutorial extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.ic_launcher,
                        "DERECHO UNAH-VS").parallax(true)
                )
                .page(new BasicPage(R.drawable.ic_launcher,
                        "Header",
                        "More text.")
                        .background(R.color.colorPrimary)
                )
                .page(new BasicPage(R.drawable.ic_launcher,
                        "Lorem ipsum",
                        "dolor sit amet.")
                )
                .page(new FragmentWelcomePage() {
                    @Override
                    protected Fragment fragment() {
                        return new PermissionTutorialFragment();
                    }
                })
                .swipeToDismiss(true)
                .build();
    }

    public void onClickAskForPermission(View view){
        Log.i("Click", "Hubo click en el fragmento");
    }
}
