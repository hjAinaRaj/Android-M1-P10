package mada.android.administrator.fragments.destination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mada.android.R;
import mada.android.commons.fragments.BaseFragment;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.services.DestinationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationCreateFragment extends BaseFragment {
    private DestinationService service;
    private EditText editTextImage;
    private EditText editTextVideo;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContent;
    private EditText editTextLocalisation;
    private Button buttonSave;

    public DestinationCreateFragment() {
        // Required empty public constructor
        if(service == null){
            service = new DestinationService();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_create, container, false);
        this.initWidget(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initWidget(View view){
        editTextImage = (EditText) view.findViewById(R.id.editTextImage);
        editTextVideo = (EditText) view.findViewById(R.id.editTextVideo);
        editTextTitle = (EditText) view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);
        editTextContent = (EditText) view.findViewById(R.id.editTextContent);
        editTextLocalisation = (EditText) view.findViewById(R.id.editTextLocalisation);
        buttonSave = (Button) view.findViewById(R.id.buttonSaveDestination);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Destination destination = new Destination();
                destination.setImage(editTextImage.getText().toString());
                destination.setVideo(editTextVideo.getText().toString());
                destination.setTitle(editTextTitle.getText().toString());
                destination.setDescription(editTextDescription.getText().toString());
                destination.setContent(editTextContent.getText().toString());
                destination.setLocalisation(editTextLocalisation.getText().toString());

                save(v, destination);
            }
        });
    }

    public void save(View view, Destination destination){
        try {
            Call<MessageResponse> call = service.post(destination);
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if(response.code() != 200)
                        Toast.makeText(view.getContext(), "Destination server error"
                                , Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(view.getContext(), "Destination created"
                                , Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}