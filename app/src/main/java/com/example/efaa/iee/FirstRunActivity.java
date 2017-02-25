package com.example.efaa.iee;

import android.app.Activity;
import android.os.Bundle;

import com.stephentuso.welcome.WelcomeHelper;

public class FirstRunActivity extends Activity {

    private WelcomeHelper welcomeScreen;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Necessary to the Tutorial activity
        welcomeScreen.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        welcomeScreen = new WelcomeHelper(this, Tutorial.class);

//        welcomeScreen.show(savedInstanceState);
        welcomeScreen.forceShow();
    }
}
