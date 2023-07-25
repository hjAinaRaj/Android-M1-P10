package mada.android.visitor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mada.android.R;
import mada.android.models.Destination;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationDetailsFragment extends Fragment {


    public DestinationDetailsFragment() {
        // Required empty public constructor
    }


    public static DestinationDetailsFragment newInstance(Destination destination) {
        DestinationDetailsFragment fragment = new DestinationDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_details, container, false);
    }
}