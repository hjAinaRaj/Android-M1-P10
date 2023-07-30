package mada.android.visitor.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mada.android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationActionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_INIT_IS_FAVORITE = "ARG_INIT_IS_FAVORITE";
    private static final String ARG_DESTINATION_ID = "ARG_DESTINATION_ID";

    // TODO: Rename and change types of parameters
    private boolean initIsFavorite;
    private String destinationId;

    private boolean isFavorite;

    public DestinationActionFragment() {
        // Required empty public constructor
    }



    public static DestinationActionFragment newInstance(boolean initIsFavorite, String destinationId) {
        DestinationActionFragment fragment = new DestinationActionFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_INIT_IS_FAVORITE, initIsFavorite);
        args.putString(ARG_DESTINATION_ID, destinationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initIsFavorite = getArguments().getBoolean(ARG_INIT_IS_FAVORITE);
            isFavorite = initIsFavorite;
            destinationId = getArguments().getString(ARG_DESTINATION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_destination_action, container, false);
        ImageView favoriteImgView = v.findViewById(R.id.destinationFavImg);
        favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_heart));
        favoriteImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if(isFavorite)
                    favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_favorite_heart));
                else
                    favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_heart));
            }
        });
        return v;
    }
}