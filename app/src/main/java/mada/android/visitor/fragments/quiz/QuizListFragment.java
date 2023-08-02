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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.quiz.Quiz;
import mada.android.visitor.activities.home.HomeVisitorActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizListFragment extends Fragment implements QuizAdapter.OnItemClickListener{
    private List<Quiz> quizzes;


    public QuizListFragment() {

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
        RecyclerView recyclerView = v.findViewById(R.id.quizListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        Quiz dummyQuiz = new Quiz();
        dummyQuiz.setTitle("An example of quiz");
        quizzes = new ArrayList<Quiz>(){{
            add(dummyQuiz);
        }};
        QuizAdapter adapter = new QuizAdapter(quizzes, this);
        recyclerView.setAdapter(adapter);
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