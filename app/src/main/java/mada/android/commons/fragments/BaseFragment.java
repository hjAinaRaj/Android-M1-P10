package mada.android.commons.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mada.android.R;

public class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    public void startNewActivity(View v, Class activityClass){
        Intent intent = new Intent(v.getContext(), activityClass);
        startActivity(intent);
    }

    public void replaceFragment(int idFragmentContainer, Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(idFragmentContainer, fragment.getClass(), null)
                .commit();
    }
}