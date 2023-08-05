package mada.android.tools;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import mada.android.commons.activities.MainActivity;

public class ConfigUtilities {
    public static void updateTheme(View view, boolean isChecked){
        try {
            if(isChecked){
                // Activate night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateTheme(Context context, boolean isChecked){
        try {
            if(isChecked){
                // Activate night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateTheme(Activity activity, boolean isChecked){
        try {
            /*if(isChecked){
                // Activate night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }*/
            UiModeManager uiModeManager = (UiModeManager) activity.getSystemService(Context.UI_MODE_SERVICE);
            if (uiModeManager != null) {
                // Vérifier si le mode nuit est activé
                boolean isNightMode = isChecked;

                // Activer le mode nuit
                uiModeManager.setNightMode(isNightMode ? UiModeManager.MODE_NIGHT_NO : UiModeManager.MODE_NIGHT_YES);
            }
        }catch (Exception e){
            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void switchLanguage(Activity activity, String languageCode) {
        // Changez la configuration de la langue en fonction du code de langue sélectionné
        Locale locale = new Locale(languageCode);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        activity.recreate();
    }

    public static void switchLanguage(Context context, String languageCode) {
        // Changez la configuration de la langue en fonction du code de langue sélectionné
        Locale locale = new Locale(languageCode);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        getActivityFromContext(context).recreate();
    }

    public static Activity getActivityFromContext(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                return getActivityFromContext(((ContextWrapper) context).getBaseContext());
            }
        }
        return null;
    }

    public static String getCssDark(){
        return "<style>" +
                "p, h1, h2, h3 { color: #cccacb; }" +
                "body { background-color: #000000; }" +
                "</style>";
    }

    public static int getDefaultTextColor(Context context) {
        int[] attrs = new int[]{android.R.attr.textColorPrimary};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        int textColor = typedArray.getColor(0, 0);
        typedArray.recycle();
        return textColor;
    }

}
