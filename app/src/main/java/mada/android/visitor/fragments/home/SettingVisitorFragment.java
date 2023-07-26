package mada.android.visitor.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mada.android.R;
import mada.android.commons.fragments.BaseFragment;
import mada.android.tools.token.TokenUtilities;
import mada.android.visitor.fragments.settings.SettingConnectedVisitorFragment;
import mada.android.visitor.fragments.settings.SettingUnknownVisitorFragment;

public class SettingVisitorFragment extends BaseFragment {

    public SettingVisitorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_visitor, container, false);
        // Inflate the layout for this fragment
        Fragment fragment = new SettingUnknownVisitorFragment();
        if(!TokenUtilities.getToken(this.getActivity()).isEmpty()){
            fragment = new SettingConnectedVisitorFragment();
        }
        replaceFragment(R.id.fragmentContainerViewSettingVisitor, fragment);
        return view;
    }

}