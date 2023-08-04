package mada.android.commons;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import mada.android.tools.ConfigUtilities;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;

public class MyApplication extends Application {
    public static MyApplication getInstance() {
        return instance;
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initGlobalPreferences();
        instance = this;
    }

    public void initGlobalPreferences() {
        String languagePref = SharedPreferencesUtilities.loadData(
                getApplicationContext(),
                SettingUnknownVisitorFragment.LANGUAGE_PREF_KEY,
                "fr");
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
        ConfigUtilities.updateTheme(getApplicationContext(), nightModeChecked);
        setLocale("fr");
        //ConfigUtilities.switchLanguage(getApplicationContext(), languagePref);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale("fr");
    }

    public void setLocale(String lang) {
        /*Configuration config = getResources().getConfiguration();
        config.setLocale(new java.util.Locale(lang));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());*/

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

}
