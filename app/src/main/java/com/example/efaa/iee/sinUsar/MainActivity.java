package com.example.efaa.iee.sinUsar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.efaa.iee.Main2Activity;
import com.example.efaa.iee.Tutorial;
import com.stephentuso.welcome.WelcomeHelper;

public class MainActivity extends AppCompatActivity {

    private WelcomeHelper welcomeScreen;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(this, Main2Activity.class));
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        welcomeScreen = new WelcomeHelper(this, Tutorial.class);
        if(!welcomeScreen.show(savedInstanceState)) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
            finish();
        }

    }
}