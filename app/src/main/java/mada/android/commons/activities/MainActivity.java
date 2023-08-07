package mada.android.commons.activities;

import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import mada.android.R;
import mada.android.administrator.activities.home.HomeAdminActivity;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.tools.token.TokenUtilities;
import mada.android.visitor.activities.home.HomeVisitorActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        

    }


}