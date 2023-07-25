package mada.android.visitor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.Destination;
import mada.android.visitor.activities.home.HomeVisitorActivity;


public class DestinationListFragment extends Fragment  implements DestinationAdapter.OnItemClickListener{

    private List<Destination> destinationList;
    private RecyclerView recyclerView;
    private DestinationAdapter destinationAdapter;

    public DestinationListFragment() {
        // Required empty public constructor
    }
    public static DestinationListFragment newInstance(){
        return new DestinationListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the list of destinations (you can get this list from your data source)
        destinationList = getDestinations();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_list, container, false);

        RecyclerView recyclerView = (RecyclerView)  view.findViewById(R.id.destinationRecyclerView);

        recyclerView.setHasFixedSize(true);


        recyclerView = view.findViewById(R.id.destinationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        destinationAdapter = new DestinationAdapter(destinationList, this);
        recyclerView.setAdapter(destinationAdapter);

        return view;
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

 class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    private List<Destination> destinationList;
    private OnItemClickListener clickListener;

    public DestinationAdapter(List<Destination> destinationList, OnItemClickListener clickListener) {
        this.destinationList = destinationList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_destination_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.titleTextView.setText(destination.getTitle());
        holder.descriptionTextView.setText(destination.getDescription());

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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.destinationTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.destinationDescriptionTextView);
        }
    }
    interface OnItemClickListener {
        void onItemClick(int position);
    }
}