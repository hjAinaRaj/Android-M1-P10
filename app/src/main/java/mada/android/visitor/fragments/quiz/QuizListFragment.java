package mada.android.visitor.fragments.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mada.android.R;
import mada.android.models.quiz.QuizList;
import mada.android.models.quiz.Quiz;
import mada.android.services.QuizService;
import mada.android.visitor.activities.home.HomeVisitorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizListFragment extends Fragment implements QuizAdapter.OnItemClickListener{
    private List<Quiz> quizzes;
    private QuizService service;
    private LinearLayout quizListProgressBarLayout;

    public QuizListFragment() {
        service = new QuizService();
    }


    public static QuizListFragment newInstance( ) {
        QuizListFragment fragment = new QuizListFragment();
        Bundle args = new Bundle();
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

        View v = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        this.quizListProgressBarLayout = v.findViewById(R.id.quizListProgressBarLayout);
        QuizListFragment f = this;
        try{
            Call<QuizList> call = service.get();
            call.enqueue(new Callback<QuizList>() {
                @Override
                public void onResponse(Call<QuizList> call, Response<QuizList> response) {
                    if(response.code() != 200)
                        Toast.makeText(v.getContext(), "Quiz server error", Toast.LENGTH_SHORT).show();
                    else{
                        f.quizListProgressBarLayout.setVisibility(View.GONE);
                        RecyclerView recyclerView = v.findViewById(R.id.quizListRecyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        quizzes =  response.body().getData();
                        QuizAdapter adapter = new QuizAdapter(quizzes, f);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<QuizList> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        return v;
    }


    @Override
    public void onItemClick(int position) {
        if (getActivity() instanceof HomeVisitorActivity) {
            FragmentTransaction ft = ((HomeVisitorActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(
                    R.id.fragmentContainerViewHomeVisitor,
                    QuizContainerFragment.newInstance(this.quizzes.get(position).get_id()));
            ft.commit();
        }
    }
}

class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Quiz> quizzes;
    private QuizAdapter.OnItemClickListener clickListener;

    public QuizAdapter(List<Quiz> quizzes,  QuizAdapter.OnItemClickListener clickListener) {
        this.quizzes = quizzes;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_quiz_item, parent, false);
        return new QuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        holder.titleTxtView.setText(quiz.getTitle());

        holder.openQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTxtView;
        public Button openQuizBtn;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxtView = itemView.findViewById(R.id.quizItemTitle);
            openQuizBtn = itemView.findViewById(R.id.openQuizBtn);
        }
    }
}