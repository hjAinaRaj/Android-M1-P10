package mada.android.commons;

import android.app.Application;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import mada.android.tools.ConfigUtilities;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        this.initGlobalPreferences();
    }

    public void initGlobalPreferences() {
        /*String languagePref = SharedPreferencesUtilities.loadData(
                this,
                SettingUnknownVisitorFragment.LANGUAGE_PREF_KEY,
                "fr");*/
        boolean nightModeChecked = SharedPreferencesUtilities.loadDataBoolean(
                getApplicationContext(),
                SettingUnknownVisitorFragment.NIGHT_MODE_KEY,
                false);


        if(nightModeChecked){
            // Activate night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        //ConfigUtilities.updateTheme(this, nightModeChecked);
        //ConfigUtilities.switchLanguage(this, languagePref);
    }
}
