package mada.android.visitor.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.services.DestinationService;
import mada.android.tools.Base64Helper;
import mada.android.visitor.activities.home.HomeVisitorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DestinationListFragment extends Fragment  implements DestinationAdapter.OnItemClickListener{

    private List<Destination> destinationList;
    private RecyclerView recyclerView;
    private DestinationAdapter destinationAdapter;
    private DestinationService service;

    public DestinationListFragment() {

        if(service == null){
            service = new DestinationService();
        }
    }
    public static DestinationListFragment newInstance(){
        return new DestinationListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the list of destinations (you can get this list from your data source)
        //destinationList = getDestinations();
        destinationList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_list, container, false);

        getList(view);

        return view;
    }

    private void getList(View view){
        try {
            Call<DestinationList> call = service.get();
            DestinationListFragment fragment = this;
            call.enqueue(new Callback<DestinationList>() {
                @Override
                public void onResponse(Call<DestinationList> call, Response<DestinationList> response) {
                    if(response.code() != 200)
                        Toast.makeText(view.getContext(), "Destination server error", Toast.LENGTH_SHORT).show();
                    else{
                        DestinationList list = response.body();
                        fragment.destinationList = list.getData();
                        RecyclerView recyclerView = (RecyclerView)  view.findViewById(R.id.destinationRecyclerView);

                        recyclerView.setHasFixedSize(true);
                        recyclerView = view.findViewById(R.id.destinationRecyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                        destinationAdapter = new DestinationAdapter(fragment.destinationList, fragment);
                        recyclerView.setAdapter(destinationAdapter);

                    }

                }

                @Override
                public void onFailure(Call<DestinationList> call, Throwable t) {
                    Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    // Dummy method to provide a list of destinations for demonstration purposes
    private List<Destination> getDestinations() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination("1", "Destination 1", "Description for Destination 1"));
        destinations.add(new Destination("2", "Destination 2", "Description for Destination 2"));
        destinations.add(new Destination("3", "Destination 3", "Description for Destination 3"));


        return destinations;
    }


    @Override
    public void onItemClick(int position) {

        if (getActivity() instanceof HomeVisitorActivity) {
            ((HomeVisitorActivity) getActivity()).replaceFragment(
                    R.id.fragmentContainerViewHomeVisitor,
                    DestinationDetailsFragment.newInstance(this.destinationList.get(position)));
        }
    }
}

 class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    private List<Destination> destinationList;
    private OnItemClickListener clickListener;

    public DestinationAdapter(List<Destination> destinationList, OnItemClickListener clickListener) {
        this.destinationList = destinationList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_destination_item, parent, false);
        return new DestinationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.bindData(destination);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    class DestinationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        DestinationViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.destinationTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.destinationDescriptionTextView);
            imageView = itemView.findViewById(R.id.destinationItemImage);
        }
        void bindData(Destination destination) {
            this.titleTextView.setText(destination.getTitle());
            this.descriptionTextView.setText(destination.getDescription());
            Log.d("debug", ""+destination.getImage().length());
            Bitmap decodedBitmap = Base64Helper.decodeBase64ToBitmap(destination.getImage());
            this.imageView.setImageBitmap(decodedBitmap);

        }
    }
    interface OnItemClickListener {
        void onItemClick(int position);
    }
}