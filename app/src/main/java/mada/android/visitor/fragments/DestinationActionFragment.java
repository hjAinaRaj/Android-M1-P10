package mada.android.visitor.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import mada.android.R;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.DestinationList;
import mada.android.services.DestinationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationActionFragment extends Fragment {


    private static final String ARG_INIT_IS_FAVORITE = "ARG_INIT_IS_FAVORITE";
    private static final String ARG_DESTINATION_ID = "ARG_DESTINATION_ID";
    private DestinationService service;

    private boolean initIsFavorite;
    private String destinationId;

    private boolean isFavorite;

    public DestinationActionFragment() {
        if(service == null) {
            service = new DestinationService();
        }
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
        favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), isFavorite? R.drawable.ic_favorite_heart : R.drawable.ic_heart));
        favoriteImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    isFavorite = !isFavorite;


                    if(isFavorite)
                        favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_favorite_heart));
                    else
                        favoriteImgView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_heart));
                    Call<MessageResponse> call = isFavorite ? service.addFavorite(destinationId): service.removeFavorite(destinationId);

                    call.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if(response.code() != 200)
                                Toast.makeText(v.getContext(), "FavoriteDestination server error", Toast.LENGTH_SHORT).show();
                            else{
                                String successTxt = isFavorite? "Added to favorites": "Removed from favorites";
                                Toast.makeText(v.getContext(),successTxt,Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }


}