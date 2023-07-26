package mada.android.administrator.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mada.android.R;
import mada.android.commons.fragments.BaseFragment;
import mada.android.visitor.activities.home.HomeVisitorActivity;

public class SettingAdminFragment extends BaseFragment {
    private Button buttonLogout;

    public SettingAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_admin, container, false);
        initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void initWidget(View view){
        this.buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        this.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(v, new HomeVisitorActivity());
            }
        });
    }
}