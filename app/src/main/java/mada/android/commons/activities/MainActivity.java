package mada.android.commons.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import mada.android.R;
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

        //this.initGlobalPreferences();
    }

    /*private void initGlobalPreferences() {
        String languagePref = SharedPreferencesUtilities.loadData(
                this,
                SettingUnknownVisitorFragment.LANGUAGE_PREF_KEY,
                "fr");
        boolean nightModeChecked = SharedPreferencesUtilities.loadDataBoolean(
                this,
                SettingUnknownVisitorFragment.NIGHT_MODE_KEY,
                false);

        ConfigUtilities.updateTheme(this, nightModeChecked);
        //ConfigUtilities.switchLanguage(this, languagePref);
    }*/


}