package mada.android.visitor.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mada.android.R;
import mada.android.commons.activities.AuthActivity;
import mada.android.commons.fragments.BaseFragment;

public class SettingUnknownVisitorFragment extends BaseFragment {
    private Button buttonToLogin;

    public SettingUnknownVisitorFragment() {
        // Required empty public constructor
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
        this.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new AuthActivity());
            }
        });
    }
}