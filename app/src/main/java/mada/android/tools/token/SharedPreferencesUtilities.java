package mada.android.tools.token;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtilities {
    public static void saveData(Activity activity, String key, String value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String loadData(Activity activity, String key, String defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static void saveDataBoolean(Activity activity, String key, boolean value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean loadDataBoolean(Activity activity, String key, boolean defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
