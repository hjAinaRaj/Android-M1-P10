package mada.android.visitor.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mada.android.R;
import mada.android.commons.fragments.BaseFragment;

public class SettingConnectedVisitorFragment extends BaseFragment {

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
        // Inflate the layout for this fragment
        return view;
    }
}