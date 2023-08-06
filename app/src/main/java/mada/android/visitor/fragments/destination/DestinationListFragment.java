package mada.android.visitor.fragments.destination;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.services.DestinationService;
import mada.android.tools.Base64Helper;
import mada.android.tools.token.SharedPreferencesUtilities;
import mada.android.tools.token.TokenUtilities;
import mada.android.tools.ws.CustomCallback;
import mada.android.tools.ws.FilterItem;
import mada.android.tools.ws.Pagination;
import mada.android.visitor.activities.home.HomeVisitorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DestinationListFragment extends Fragment implements DestinationAdapter.OnItemClickListener{

    private List<Destination> destinationList;
    private RecyclerView recyclerView;
    private DestinationAdapter destinationAdapter;
    private DestinationService service;
    private FilterItem searchFilter;
    private FilterItem favoriteFilter;
    private Pagination pagination;
    private LinearLayout destinationPaginLayout;
    private LinearLayout destinationListProgressBarLayout;


    private Button prevButton;
    private Button nextButton;

    public DestinationListFragment() {

        if(service == null){
            service = new DestinationService();
        }
        if(pagination == null){
            pagination = new Pagination();
        }
    }
    public static DestinationListFragment newInstance(){
        return new DestinationListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        destinationList = new ArrayList<>();
        searchFilter = new FilterItem("title", "like", null, "string");
        favoriteFilter = new FilterItem("isFavorite", "=", null, "boolean");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination_list, container, false);
        String currentToken =  SharedPreferencesUtilities.loadData(
                getContext(),
                TokenUtilities.USER_TOKEN_KEY,
                ""
        );
        if(currentToken != null && !currentToken.equals(""))
            addFavoritesRadio(view);
        recyclerView = view.findViewById(R.id.destinationRecyclerView);
        destinationPaginLayout = view.findViewById(R.id.destinationPaginLayout);

        destinationListProgressBarLayout = view.findViewById(R.id.destinationListProgressBarLayout);
        getList(view);

        //Add search btn listener
        TextView searchTxtView = view.findViewById(R.id.destinationSearchTxt);
        Button searchBtn = view.findViewById(R.id.destinationSearchBtn);

        prevButton = view.findViewById(R.id.quizQuestionPrevBtn);
        nextButton = view.findViewById(R.id.quizQuestionNextBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence searchTxtValue = searchTxtView.getText();
                if(searchTxtValue != null && !"".equals(searchTxtValue.toString()))searchFilter.setValue(searchTxtView.getText());
                else searchFilter.setValue(null);


                reloadList(v);
            }
        });

        return view;
    }

    private void getList(View view){
        DestinationListFragment fragment = this;

        getList(view, new CustomCallback<DestinationList>() {
            @Override
            public void callback(DestinationList data, View v) throws Exception {

                DestinationList list = data;
                destinationList = list.getData();

                RecyclerView recyclerView = (RecyclerView)  view.findViewById(R.id.destinationRecyclerView);

                recyclerView.setHasFixedSize(true);
                recyclerView = view.findViewById(R.id.destinationRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                prevButton.setVisibility(View.VISIBLE);
                prevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pagination.setPage(pagination.getPage()-1);
                        reloadList(view);
                    }
                });
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pagination.setPage(pagination.getPage()+1);
                        reloadList(view);
                    }
                });

                destinationAdapter = new DestinationAdapter(getChildFragmentManager(), destinationList, fragment);
                recyclerView.setAdapter(destinationAdapter);
            }
        });
    }
    private void reloadList(View view){
        DestinationListFragment fragment = this;


        getList(view, new CustomCallback<DestinationList>() {
            @Override
            public void callback(DestinationList list, View v) throws Exception {
                destinationList.clear();
                destinationList.addAll(list.getData());

                destinationAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getList(View view, CustomCallback<DestinationList> displayCallback){
        try {
            destinationPaginLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            destinationListProgressBarLayout.setVisibility(View.VISIBLE);

            Call<DestinationList> call = service.getForConnectedUser(new ArrayList<FilterItem>(){{
                add(searchFilter);
                add(favoriteFilter);
            }}, pagination);

            call.enqueue(new Callback<DestinationList>() {
                @Override
                public void onResponse(Call<DestinationList> call, Response<DestinationList> response) {
                    try {
                        if(response.code() != 200)
                            Toast.makeText(view.getContext(), "Destination server error", Toast.LENGTH_SHORT).show();
                        else{
                            DestinationList list = response.body();
                            int destinationElmtVisibility = list.getData().size()>0 ?View.VISIBLE: View.GONE;
                            destinationListProgressBarLayout.setVisibility(destinationElmtVisibility);
                            destinationPaginLayout.setVisibility(destinationElmtVisibility);
                            recyclerView.setVisibility(View.GONE);

                            prevButton.setEnabled(pagination.hasPrev(list.getMeta().getTotalElmtCount()));
                            nextButton.setEnabled(pagination.hasNext(list.getMeta().getTotalElmtCount()));
                            displayCallback.callback(list, view);
                        }
                    }catch (Exception e){
                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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





    @Override
    public void onItemClick(int position) {

        if (getActivity() instanceof HomeVisitorActivity) {
            FragmentTransaction ft = ((HomeVisitorActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(
                    R.id.fragmentContainerViewHomeVisitor,
                    DestinationDetailsFragment.newInstance(this.destinationList.get(position).get_id()));
            ft.commit();
        }
    }
    private RadioGroup favoritesRadioGroup;
    private int allRadioBtnId;
    private int favoritesRadioBtnId;

    private void addFavoritesRadio(View view){
        Context context = getContext();
        favoritesRadioGroup = new RadioGroup(context);
        favoritesRadioGroup.setId(View.generateViewId());
        LinearLayout.LayoutParams radioGroupParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        radioGroupParams.setMargins(dpToPx(context, 8), dpToPx(context, 60), dpToPx(context, 8), dpToPx(context, 8));
        favoritesRadioGroup.setLayoutParams(radioGroupParams);
        favoritesRadioGroup.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        favoritesRadioGroup.setOrientation(RadioGroup.HORIZONTAL);

        LinearLayout.LayoutParams radioButtonParams = new LinearLayout.LayoutParams(
                dpToPx(context, 120), // Replace with the appropriate dimension in pixels
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        RadioButton radioButton0 = new RadioButton(context);
        allRadioBtnId = View.generateViewId();
        radioButton0.setId(allRadioBtnId);

        radioButton0.setLayoutParams(radioButtonParams);
        radioButton0.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_flat_selector)); // Replace with the appropriate drawable
        radioButton0.setButtonDrawable(android.R.color.transparent);
        radioButton0.setChecked(true);
        radioButton0.setPadding(dpToPx(context, 16), dpToPx(context, 3), dpToPx(context, 16), dpToPx(context, 3));
        radioButton0.setText("All");
        radioButton0.setTextColor(ContextCompat.getColorStateList(context, R.color.radio_flat_text_selector)); // Replace with the appropriate color selector


        RadioButton radioButton1 = new RadioButton(context);
        favoritesRadioBtnId = View.generateViewId();
        radioButton1.setId(favoritesRadioBtnId);
        radioButton1.setLayoutParams(radioButtonParams);
        radioButton1.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_flat_selector)); // Replace with the appropriate drawable
        radioButton1.setButtonDrawable(android.R.color.transparent);
        radioButton1.setPadding(dpToPx(context, 16), dpToPx(context, 3), dpToPx(context, 16), dpToPx(context, 3));
        radioButton1.setText("Favorites");
        radioButton1.setTextColor(ContextCompat.getColorStateList(context, R.color.radio_flat_text_selector)); // Replace with the appropriate color selector


        favoritesRadioGroup.addView(radioButton0);
        favoritesRadioGroup.addView(radioButton1);


        RelativeLayout parentLayout = view.findViewById(R.id.destinationListMainLayout);
        parentLayout.addView(favoritesRadioGroup,1);
        RecyclerView rv = view.findViewById(R.id.destinationRecyclerView);
        RelativeLayout.LayoutParams recyclerParams = (RelativeLayout.LayoutParams) rv.getLayoutParams();
        recyclerParams.topMargin = dpToPx(context, 100);
        rv.setLayoutParams(recyclerParams);

        favoritesRadioGroup.check(allRadioBtnId);

        favoritesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId == favoritesRadioBtnId){
                   favoriteFilter.setValue("true");
               }else{
                   favoriteFilter.setValue(null);
               }
               reloadList(view);
            }
        });

    }

    private int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

 class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    private List<Destination> destinationList;
    private OnItemClickListener clickListener;
    private FragmentManager fragmentManager;

    public DestinationAdapter( FragmentManager fragmentManager, List<Destination> destinationList, OnItemClickListener clickListener) {
       this.fragmentManager =  fragmentManager;
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
        holder.bindData(fragmentManager, destination);

    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    class DestinationViewHolder extends RecyclerView.ViewHolder {
        int destinationItemActionContainerId;
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        TextView viewMoreTxtView;
        boolean isFavorite = false;
        FrameLayout actionContainer ;

        private void loadActionFragment( FragmentManager fragmentManager,Boolean initIsFavorite, String destinationId){
            if(initIsFavorite == null) return;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(destinationItemActionContainerId, DestinationActionFragment.newInstance(initIsFavorite, destinationId));
            fragmentTransaction.commit();

        }
        DestinationViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.quizzQuestionQuestionTxt);
            descriptionTextView = itemView.findViewById(R.id.destinationDescriptionTextView);
            imageView = itemView.findViewById(R.id.destinationItemImage);

            viewMoreTxtView = itemView.findViewById(R.id.destinationViewMoreBtn);
            destinationItemActionContainerId = View.generateViewId();
            actionContainer = itemView.findViewById(destinationItemActionContainerId);


            actionContainer = new FrameLayout(itemView.getContext());
            actionContainer.setId(destinationItemActionContainerId);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(8, 0, 8, 8);
            actionContainer.setLayoutParams(layoutParams);
            RelativeLayout mainLayout = itemView.findViewById(R.id.destinationItemMainContainer);
            mainLayout.addView(actionContainer);

        }
        void bindData(FragmentManager fragmentManager, Destination destination) {
            try{
                this.titleTextView.setText(destination.getTitle());
                this.descriptionTextView.setText(destination.getDescription());

                loadActionFragment(fragmentManager, destination.isFavorite(), destination.get_id());

                Bitmap decodedBitmap = Base64Helper.decodeBase64ToBitmap(destination.getImage());
                this.imageView.setImageBitmap(decodedBitmap);

                viewMoreTxtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(getAdapterPosition());
                    }
                });
            }catch(Exception e){
                Log.e("custom-error","----------------------"+e.getMessage());
            }


        }
    }
    interface OnItemClickListener {
        void onItemClick(int position);
    }
}