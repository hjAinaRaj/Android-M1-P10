package mada.android.visitor.fragments.destination;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.internal.maps.zzaa;
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

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import mada.android.BuildConfig;
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
    private MapView mapView;
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

        this.initMap(view);

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
                    webView.loadDataWithBaseURL(
                            null,content, "text/html", "UTF-8", null);

                    setMap(destination.getLocalisation());
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

    private void initMap(View view){
        // Configuration osmdroid (optionnelle mais recommandée)
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        // Récupérer la vue MapView du layout
        mapView = view.findViewById(R.id.mapView);

        // Configurer la source de tuiles (les tuiles sont les images de la carte)
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        // Permettre le zoom par drag
        mapView.setMultiTouchControls(true);

        // Configurer le zoom maximal et minimal de la carte
        mapView.setMinZoomLevel(4.0);
        mapView.setMaxZoomLevel(19.0);

        // Centrer la carte sur une position spécifique (par exemple, Paris)
        GeoPoint center = new GeoPoint(48.8566, 2.3522);
        mapView.getController().setCenter(center);

        // Définir le niveau de zoom initial
        mapView.getController().setZoom(12.0);

        // Ajouter un marqueur à la position spécifiée
        GeoPoint startPoint = new GeoPoint(48.8566, 2.3522);
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);
    }

    private void setMap(String localisation){
        String [] splitedLocalisation = localisation.split(",");
        float latitude = new Float(splitedLocalisation[0]).floatValue();
        float longitute = new Float(splitedLocalisation[1]).floatValue();
        GeoPoint center = new GeoPoint(latitude, longitute);
        mapView.getController().setCenter(center);

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(center);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}