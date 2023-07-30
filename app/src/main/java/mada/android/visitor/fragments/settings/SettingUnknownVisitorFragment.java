package mada.android.visitor.fragments.settings;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

import mada.android.R;
import mada.android.commons.activities.AuthActivity;
import mada.android.commons.fragments.BaseFragment;
import mada.android.services.external.MyFirebaseMessagingService;
import mada.android.tools.token.SharedPreferencesUtilities;

public class SettingUnknownVisitorFragment extends BaseFragment {
    public final static String NOTIF_KEY = "notif_key";
    public final static String NIGHT_MODE_KEY = "night_mode_key";
    private Button buttonToLogin;
    private Switch switchNotification;
    private Switch switchNightMode;
    private Spinner spinnerLanguage;
    private Button buttonToEn;
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
        this.buttonToLogin = (Button) view.findViewById(R.id.buttonToLogin);
        this.initNotificationSwitch(view);
        this.initNightMode(view);
        this.spinnerLanguage = (Spinner) view.findViewById(R.id.spinnerLanguage);
        this.buttonToEn = (Button) view.findViewById(R.id.buttonToEn);

        this.buttonToEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage("en");
            }
        });
        this.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new AuthActivity());
            }
        });

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
                R.array.language_arrays,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        // Apply the adapter to the spinner
        /*spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setSelection(0);*/

        /*spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérez la langue sélectionnée
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                selectedLanguage = "en";

                // Changez la langue de l'application en fonction de la sélection
                //switchLanguage(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void initNotificationSwitch(View view){
        this.switchNotification = (Switch) view.findViewById(R.id.switchNotif);
        boolean defaultValue = SharedPreferencesUtilities.
                loadDataBoolean(getActivity(), NOTIF_KEY, false);
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
                    SharedPreferencesUtilities.saveDataBoolean(getActivity(), NOTIF_KEY, isChecked);
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initNightMode(View view){
        this.switchNightMode = (Switch) view.findViewById(R.id.switchNightMode);
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
        });
    }

    private void switchLanguage(String languageCode) {
        // Changez la configuration de la langue en fonction du code de langue sélectionné
        Locale locale = new Locale(languageCode);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        this.getActivity().recreate();
    }

    public void updateTheme(View view, boolean isChecked){
        try {
            if(isChecked){
                // Activate night mode
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                getActivity().setTheme(R.style.Theme_Mada_dark);
            }else{
                getActivity().setTheme(R.style.Theme_Mada);
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            //getActivity().recreate();
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}