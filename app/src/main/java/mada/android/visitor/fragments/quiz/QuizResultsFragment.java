package mada.android.visitor.fragments.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mada.android.R;
import mada.android.models.quiz.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResultsFragment extends Fragment {

    private QuizViewModel viewModel;
    private Button displayResultsButton;
    private TextView resultsTextView;
    private TextView resultsReviewHintTxt;

    public QuizResultsFragment() {
        // Required empty public constructor
    }


    public static QuizResultsFragment newInstance() {
        QuizResultsFragment fragment = new QuizResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_results, container, false);
        displayResultsButton = view.findViewById(R.id.quizResultsViewBtn);
        resultsTextView =  view.findViewById(R.id.resultsValueTxtView);
        resultsReviewHintTxt = view.findViewById(R.id.resultsReviewHintTxt);
        resultsTextView.setVisibility(View.INVISIBLE);
        resultsReviewHintTxt.setVisibility(View.INVISIBLE);
        displayResultsButton.setOnClickListener(view1 -> {
            try{
                int score = viewModel.submitAnswers(getResources());
                resultsTextView.setText(score+"%");
                if(score > 80) resultsTextView.setTextColor(getResources().getColor(R.color.right_answer));
                else resultsTextView.setTextColor(getResources().getColor(R.color.wrong_answer));
                resultsTextView.setVisibility(View.VISIBLE);
                displayResultsButton.setVisibility(View.INVISIBLE);
                resultsReviewHintTxt.setVisibility(View.VISIBLE);
            }catch(Exception e){
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}