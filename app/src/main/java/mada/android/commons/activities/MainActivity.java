package mada.android.commons.activities;

import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import mada.android.R;
import mada.android.commons.MyApplication;
import mada.android.tools.ConfigUtilities;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.visitor.fragments.DestinationListFragment;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


}