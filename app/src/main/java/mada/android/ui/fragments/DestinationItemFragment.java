package mada.android.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mada.android.R;
import mada.android.models.Destination;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationItemFragment extends Fragment {
    private Destination destination;

    public DestinationItemFragment() {
        // Required empty public constructor
    }

    public static DestinationItemFragment newInstance(Destination destination) {
        DestinationItemFragment fragment = new DestinationItemFragment();
        fragment.setDestination(destination);
        return fragment;
    }

    private void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_item, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        // Set the data to the views
        titleTextView.setText(destination.getTitle());
        descriptionTextView.setText(destination.getDescription());

        return view;
    }
}
