package mada.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mada.android.R;
import mada.android.models.destination.Destination;

public class SeparatedDestinationAdapter extends RecyclerView.Adapter<SeparatedDestinationAdapter.Holder> {
    private ArrayList<Destination> destinationDataList;

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
    }

    @Override
    public int getItemCount() {
        return this.destinationDataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.textViewDestinationHomeItem);
        }


    }
}
