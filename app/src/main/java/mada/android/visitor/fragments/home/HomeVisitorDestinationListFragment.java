package mada.android.visitor.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.adapter.SeparatedDestinationAdapter;
import mada.android.commons.fragments.BaseFragment;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.models.users.UserToken;
import mada.android.services.DestinationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeVisitorDestinationListFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private DestinationService service;

    public HomeVisitorDestinationListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home_visitor_destination_list, container, false);
        this.initWidget(view);
        this.getList(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initWidget(View view){
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHomeVisitorDestinationList);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void getList(View view){
        try {
            Call<DestinationList> call = service.get();
            call.enqueue(new Callback<DestinationList>() {
                @Override
                public void onResponse(Call<DestinationList> call, Response<DestinationList> response) {
                    DestinationList list = response.body();
                    generateList(list.getData());
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

    public void generateList(List<Destination> list){
        SeparatedDestinationAdapter adapter = new SeparatedDestinationAdapter((ArrayList<Destination>) list);
        View view = getView();
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHomeVisitorDestinationList);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Get recycler view
        this.recyclerView.setAdapter(adapter);
    }
}