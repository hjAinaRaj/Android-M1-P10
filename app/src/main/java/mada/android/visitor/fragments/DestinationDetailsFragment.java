package mada.android.visitor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mada.android.R;
import mada.android.models.Destination;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationDetailsFragment extends Fragment {
    private WebView webView;
    private Destination destination;

    public DestinationDetailsFragment() {
        // Required empty public constructor
    }


    public static DestinationDetailsFragment newInstance(Destination destination) {
        DestinationDetailsFragment fragment = new DestinationDetailsFragment();
        Bundle args = new Bundle();
        fragment.destination = destination;
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

        View view =  inflater.inflate(R.layout.fragment_destination_details, container, false);
        webView =  view.findViewById(R.id.destinationDetailsWebView);

        // Enable JavaScript
        //webView.getSettings().setJavaScriptEnabled(true);

        // Load and display the HTML content
        String htmlContent = "<html>" +
                "<body>\n" +
                "    <h2>Rova of Manjakamiadana</h2>\n" +
                "    <p>\n" +
                "        The Rova of Manjakamiadana, also known as the Queen's Palace or the Royal Palace of Antananarivo,\n" +
                "        is a historic palace located in Antananarivo, Madagascar. It served as the residence of the\n" +
                "        sovereigns of the Kingdom of Madagascar during the 17th and 18th centuries.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        The palace complex is perched on the highest hill in the city, providing a commanding view\n" +
                "        of Antananarivo. It features traditional Merina architecture and is characterized by its\n" +
                "        distinctive wooden balconies and steep roofs.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        The Rova of Manjakamiadana was severely damaged by a fire in 1995, but restoration efforts\n" +
                "        have been ongoing to preserve this important historical site.\n" +
                "    </p>\n" +
                "    <p>\n" +
                "        Visitors can explore the Rova to learn about the rich history of Madagascar and its royal heritage.\n" +
                "        It remains a symbol of national identity and a cultural treasure for the people of Madagascar.\n" +
                "    </p>\n" +
                "</body>" +
                "</html>";
        webView.loadData(htmlContent, "text/html", "UTF-8");

        //Handle URL clicks within the WebView
        webView.setWebViewClient(new WebViewClient());
        return view;
    }
}