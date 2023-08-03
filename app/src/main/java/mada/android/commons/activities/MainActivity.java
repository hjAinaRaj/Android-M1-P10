package mada.android.commons.activities;

import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import mada.android.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


}