package mada.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mada.android.R;
import mada.android.models.destination.Destination;
import mada.android.visitor.fragments.destination.DestinationDetailsFragment;
import mada.android.visitor.fragments.home.HomeVisitorDestinationListFragment;

public class SeparatedDestinationAdapter extends RecyclerView.Adapter<SeparatedDestinationAdapter.Holder> {
    private ArrayList<Destination> destinationDataList;
    private HomeVisitorDestinationListFragment fragment;

    public SeparatedDestinationAdapter(ArrayList<Destination> destinationDataList) {
        this.destinationDataList = destinationDataList;
    }

    @NonNull
    @Override
    public SeparatedDestinationAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.destination_home_item, parent, false);
        Holder viewHolder = new Holder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeparatedDestinationAdapter.Holder holder, int position) {
        Destination destinationDataList = this.destinationDataList.get(position);
        holder.textView.setText(destinationDataList.getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment()
                        .replaceFragment(
                                R.id.fragmentContainerViewHomeVisitor,
                                DestinationDetailsFragment.newInstance(destinationDataList.get_id()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.destinationDataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.textViewDestinationHomeItem);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageViewDestinationHomeItem);
        }


    }

    public HomeVisitorDestinationListFragment getFragment() {
        return fragment;
    }

    public void setFragment(HomeVisitorDestinationListFragment fragment) {
        this.fragment = fragment;
    }
}
