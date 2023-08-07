package mada.android.visitor.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mada.android.R;
import mada.android.commons.fragments.BaseFragment;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.tools.token.TokenUtilities;
import mada.android.visitor.activities.home.HomeVisitorActivity;

public class SettingConnectedVisitorFragment extends BaseFragment {
    private Button buttonLogout;

    public SettingConnectedVisitorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_connected_visitor, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void initWidget(View view){
        this.buttonLogout = (Button) view.findViewById(R.id.buttonLogoutVisitor);
        this.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtilities.saveData(
                        getContext(),
                        TokenUtilities.USER_TOKEN_KEY,
                        ""
                );
                SharedPreferencesUtilities.saveData(
                        getContext(),
                        TokenUtilities.USER_NAME,
                        ""
                );
                SharedPreferencesUtilities.saveData(
                        getContext(),
                        TokenUtilities.USER_ROLE,
                        ""
                );
                startNewActivity(v, new HomeVisitorActivity());
            }
        });
    }
}