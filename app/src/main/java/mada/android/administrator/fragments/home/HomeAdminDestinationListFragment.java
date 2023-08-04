package mada.android.administrator.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mada.android.R;
import mada.android.administrator.fragments.destination.DestinationCreateFragment;
import mada.android.commons.fragments.BaseFragment;

public class HomeAdminDestinationListFragment extends BaseFragment {
    private Button button;
    private FloatingActionButton floatingActionButton;

    public HomeAdminDestinationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_admin_destination_list, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initWidget(View view){
        /*this.button = (Button) view.findViewById(R.id.buttonCreateDestination);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(R.id.fragmentContainerViewAdmin, new DestinationCreateFragment());
            }
        });*/

        this.floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabButtonAdmin);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(R.id.fragmentContainerViewAdmin, new DestinationCreateFragment());
            }
        });
    }
}