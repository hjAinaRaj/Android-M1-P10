package mada.android.visitor.fragments.destination;


import android.graphics.Bitmap;
import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mada.android.R;
import mada.android.models.destination.Destination;
import mada.android.services.DestinationService;
import mada.android.tools.Base64Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationDetailsFragment extends Fragment {
    private WebView webView;
    private YouTubePlayerView youtubePlayerView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView destinationImageView;
    private String destinationId;
    private DestinationService service;
    private static final String ARG_DESTINATION_ID = "ARG_DESTINATION_ID";

    public DestinationDetailsFragment() {
        // Required empty public constructor
        service = new DestinationService();    }


    public static  Fragment newInstance(String destinationId) {
        DestinationDetailsFragment fragment = new DestinationDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESTINATION_ID, destinationId);

        fragment.setArguments(args);
        return fragment;
    }
    private void loadActionFragment(Boolean initIsFavorite, String destinationId){
        if(initIsFavorite == null) return;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.destinationDetailsActionContainer, DestinationActionFragment.newInstance(initIsFavorite, destinationId));
        fragmentTransaction.commit();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           this.destinationId = getArguments().getString(ARG_DESTINATION_ID);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_destination_details, container, false);
        webView =  view.findViewById(R.id.destinationDetailsWebView);
        this.titleTextView =  view.findViewById(R.id.destinationDetailsTitle);
        this.descriptionTextView = view.findViewById(R.id.destinationDetailsDescription);
        this.destinationImageView = view.findViewById(R.id.destinationItemImage);

        this.youtubePlayerView = view.findViewById(R.id.quizIntroVideoView);
        DestinationDetailsFragment fragment = this;

        // TODO: Use the destinationId to fetch the destination details
try{
        Call<Destination> call = this.service.get(this.destinationId);
        call.enqueue(new Callback<Destination>() {
            @Override
            public void onResponse(Call<Destination> call, Response<Destination> response) {
                if(response.code() != 200)
                    Toast.makeText(view.getContext(), "Destination server error", Toast.LENGTH_SHORT).show();
                else{
                    Destination destination = response.body();
                    fragment.descriptionTextView.setText(destination.getDescription());
                    fragment.titleTextView.setText(destination.getTitle());
                    loadActionFragment(destination.isFavorite(), destinationId);
                    Bitmap decodedBitmap = Base64Helper.decodeBase64ToBitmap(destination.getImage());
                    fragment.destinationImageView.setImageBitmap(decodedBitmap);


                    getLifecycle().addObserver(youtubePlayerView);

                    youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            youTubePlayer.cueVideo(destination.getVideo(), 0);
                        }
                    });
                    String content = destination.getContent();
                    webView.getSettings().setJavaScriptEnabled(false);
                    webView.loadDataWithBaseURL(null,"<h2>Discover the Diverse Wildlife</h2><p>Join us on a wildlife expedition like no other. Andasibe-Mantadia National Park is a haven for unique and diverse species. Marvel at the playful ring-tailed lemurs as they swing through the trees, spot colorful chameleons blending into their surroundings, and witness the elusive fossa, Madagascar's largest predator, roaming the forest floor.</p><h2>Immerse in the Lush Rainforest</h2><p>Explore the lush rainforest filled with towering trees and enchanting foliage. Your journey will take you through mist-covered trails, where the haunting calls of the indri lemurs will resonate through the canopy, creating an otherworldly atmosphere.</p><h2>An Unforgettable Experience</h2><p>Prepare to be captivated by the unique flora and fauna that call this park their home. Our experienced guides will share their knowledge, providing you with insights into the delicate balance of this ecosystem.</p>", "text/html", "UTF-8", null);

                }

            }

            @Override
            public void onFailure(Call<Destination> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
}catch (Exception e){
    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
}


        //Handle URL clicks within the WebView
        webView.setWebViewClient(new WebViewClient());
        return view;
    }
}