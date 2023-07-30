package mada.android.visitor.fragments.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import mada.android.R;
import mada.android.commons.activities.AuthActivity;
import mada.android.commons.fragments.BaseFragment;
import mada.android.services.external.MyFirebaseMessagingService;

public class SettingUnknownVisitorFragment extends BaseFragment {
    private Button buttonToLogin;
    private Switch switchNotification;
    private Switch switchNightMode;
    private MyFirebaseMessagingService myFirebaseMessagingService;

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
        this.switchNotification = (Switch) view.findViewById(R.id.switchNotif);
        this.switchNightMode = (Switch) view.findViewById(R.id.switchNightMode);
        this.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new AuthActivity());
            }
        });

        this.switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked){
                        myFirebaseMessagingService.saveSavedToken();
                    }else{
                        myFirebaseMessagingService.unsubscribeSavedToken();
                    }
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTheme(view, isChecked);
            }
        });
    }

    public void updateTheme(View view, boolean isChecked){
        try {
            if(isChecked){
                // Activate night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            // Recréez le fragment pour appliquer immédiatement le nouveau thème
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.detach(this);
            fragmentTransaction.attach(this);
            fragmentTransaction.commit();
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}