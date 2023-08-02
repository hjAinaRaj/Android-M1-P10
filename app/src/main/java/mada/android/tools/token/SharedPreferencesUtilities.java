package mada.android.tools.token;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtilities {

    public static SharedPreferences getDefaultSharedPreferences(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String sharedPreferencesName = PreferenceManager.getDefaultSharedPreferencesName(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    sharedPreferencesName, Context.MODE_PRIVATE);
            return sharedPreferences;
        }
        return null;
    }
    public static void saveData(Activity activity, String key, String value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveData(Context context, String key, String value){
        SharedPreferences sharedPreferences = SharedPreferencesUtilities.getDefaultSharedPreferences
                (context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String loadData(Activity activity, String key, String defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static String loadData(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = SharedPreferencesUtilities.getDefaultSharedPreferences
                (context);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void saveDataBoolean(Activity activity, String key, boolean value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void saveDataBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean loadDataBoolean(Activity activity, String key, boolean defaultValue){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }

    public static boolean loadDataBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
