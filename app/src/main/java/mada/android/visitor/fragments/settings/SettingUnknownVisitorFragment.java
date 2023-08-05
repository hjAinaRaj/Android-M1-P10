package mada.android.visitor.fragments.settings;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import mada.android.R;
import mada.android.commons.activities.AuthActivity;
import mada.android.commons.fragments.BaseFragment;
import mada.android.services.external.MyFirebaseMessagingService;
import mada.android.tools.ConfigUtilities;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.tools.token.TokenUtilities;
import mada.android.visitor.activities.home.HomeVisitorActivity;

public class SettingUnknownVisitorFragment extends BaseFragment {
    public final static String NOTIF_KEY = "notif_key";
    public final static String NIGHT_MODE_KEY = "night_mode_key";
    public final static String LANGUAGE_PREF_KEY = "language_key";
    private Button buttonToLogin;
    private Button buttonToLogout;
    private Switch switchNotification;
    private Switch switchNightMode;
    private Spinner spinnerLanguage;
    private Button buttonToEn;
    private Button buttonToFr;
    private Button buttonToLight;
    private Button buttonToDark;
    private MyFirebaseMessagingService myFirebaseMessagingService;
    private boolean isUpdatingTheme = false;

    public SettingUnknownVisitorFragment() {
        // Required empty public constructor
        if(myFirebaseMessagingService == null){
            myFirebaseMessagingService = new MyFirebaseMessagingService();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_unknown_visitor, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void initWidget(View view){
        this.initAuthButton(view);
        this.initNotificationSwitch(view);
        this.initNightMode(view);
        this.initLanguageButton(view);
    }

    private void initAuthButton(View view) {
        this.buttonToLogin = (Button) view.findViewById(R.id.buttonToLogin);
        this.buttonToLogout = (Button) view.findViewById(R.id.buttonToLogoutVisitor);

        String token = SharedPreferencesUtilities.loadData(
                getContext(),
                TokenUtilities.USER_TOKEN_KEY,
                ""
        );
        if(!token.isEmpty()){
            this.buttonToLogin.setVisibility(View.INVISIBLE);
            this.buttonToLogout.setVisibility(View.VISIBLE);
        }
        this.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new AuthActivity());
            }
        });
        this.buttonToLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtilities.saveData(
                        getContext(),
                        TokenUtilities.USER_TOKEN_KEY,
                        ""
                );
                startNewActivity(v, new HomeVisitorActivity());
            }
        });
    }

    private void initLanguageButton(View view) {
        this.buttonToEn = (Button) view.findViewById(R.id.buttonToEn);
        this.buttonToFr = (Button) view.findViewById(R.id.buttonToFr);
        this.buttonToEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage("en");
            }
        });
        this.buttonToFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage("fr");
            }
        });
    }

    public void initNotificationSwitch(View view){
        this.switchNotification = (Switch) view.findViewById(R.id.switchNotif);
        boolean defaultValue = SharedPreferencesUtilities.
                loadDataBoolean(getContext(), NOTIF_KEY, false);
        this.switchNotification.setChecked(defaultValue);
        this.switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked){
                        myFirebaseMessagingService.saveSavedToken();
                    }else{
                        myFirebaseMessagingService.unsubscribeSavedToken();
                    }
                    SharedPreferencesUtilities.saveDataBoolean(getContext(), NOTIF_KEY, isChecked);
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initNightMode(View view){
        this.buttonToDark = (Button) view.findViewById(R.id.buttonToDark);
        this.buttonToLight = (Button) view.findViewById(R.id.buttonToLight);

        this.buttonToDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = true;
                updateTheme(isChecked);

                SharedPreferencesUtilities.saveDataBoolean(
                        getContext(),
                        NIGHT_MODE_KEY,
                        isChecked
                );

                /*
                String currentLanguage = SharedPreferencesUtilities.loadData(
                        getContext(),
                        SettingUnknownVisitorFragment.LANGUAGE_PREF_KEY,
                        "fr");
                Log.d("debug", "-----------------------------CURRENT LANGUAGE "+currentLanguage );
                switchLanguage(currentLanguage);

                 */
            }
        });

        this.buttonToLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = false;
                updateTheme(isChecked);

                SharedPreferencesUtilities.saveDataBoolean(
                        getContext(),
                        NIGHT_MODE_KEY,
                        isChecked
                ); /*
                String currentLanguage = SharedPreferencesUtilities.loadData(
                        getContext(),
                        SettingUnknownVisitorFragment.LANGUAGE_PREF_KEY,
                        "fr");
                Log.d("debug", "-----------------------------CURRENT LANGUAGE "+currentLanguage );
                switchLanguage(currentLanguage);
                */

            }
        });
        /*this.switchNightMode = (Switch) view.findViewById(R.id.switchNightMode);
        boolean nightModeChecked = SharedPreferencesUtilities.loadDataBoolean(
                getActivity(), NIGHT_MODE_KEY, false
        );
        this.switchNightMode.setChecked(nightModeChecked);
        updateTheme(view, nightModeChecked);
        this.switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isUpdatingTheme) {
                    return;
                }
                isUpdatingTheme = true;
                updateTheme(view, isChecked);
                SharedPreferencesUtilities.saveDataBoolean(
                        getActivity(),
                        NIGHT_MODE_KEY,
                        isChecked
                );
                isUpdatingTheme = false;
            }
        });*/
    }

    private void switchLanguage(String languageCode) {
        SharedPreferencesUtilities.saveData(getContext(), LANGUAGE_PREF_KEY, languageCode);
        ConfigUtilities.switchLanguage(getContext(), languageCode);
    }


    public void updateTheme(boolean isChecked){
        ConfigUtilities.updateThemeAndRecreateActivity(getContext(), isChecked);
    }
}