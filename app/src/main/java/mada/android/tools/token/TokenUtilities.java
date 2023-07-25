package mada.android.tools.token;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class TokenUtilities {
    private final static String USER_TOKEN_KEY = "user_token";

    public static void saveToken(Activity activity, String token){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN_KEY, token);
        editor.apply();
    }

    public static String getToken(Activity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TOKEN_KEY, "");
    }
}
