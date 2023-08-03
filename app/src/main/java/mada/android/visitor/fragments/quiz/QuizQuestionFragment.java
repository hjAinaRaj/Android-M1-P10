package mada.android.visitor.fragments.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.quiz.QuizQuestion;
import mada.android.models.quiz.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizQuestionFragment extends Fragment {
    private QuizViewModel viewModel;
    private RadioGroup radioGroup;
    private List<Integer> radioButtonIds = new ArrayList<Integer>();
    private QuizQuestion currentQuestion;


    private static final String ARG_QUIZ_QUESTION_ID = "quizQuestionId";

    private int quizQuestionId;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }


    public static QuizQuestionFragment newInstance(int quizQuestionId) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUIZ_QUESTION_ID, quizQuestionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizQuestionId = getArguments().getInt(ARG_QUIZ_QUESTION_ID);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        currentQuestion = viewModel.getCurrentQuiz().getQuestions().get(quizQuestionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quiz_question, container, false);
        QuizQuestion currentQuestion = viewModel.getCurrentQuiz().getQuestions().get(quizQuestionId);

        TextView questionView = v.findViewById(R.id.quizzQuestionQuestionTxt);
        questionView.setText(currentQuestion.getQuestion());

        TextView quizNumTxtView = v.findViewById(R.id.quizQuestionNumberTxt);
        quizNumTxtView.setText(""+(quizQuestionId+1));

        radioGroup = v.findViewById(R.id.quizzQuestionRadio);
        for(String option : currentQuestion.getOptions())
        {
            int currentBtnId = View.generateViewId();
            RadioButton btn = new RadioButton(getContext());
            btn.setId(currentBtnId);
            btn.setText(option);
            radioButtonIds.add(currentBtnId);
            radioGroup.addView(btn);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentQuestion.setCurrentAnswer(radioButtonIds.indexOf(checkedId));
            }
        });

        //When results are to be displayed
        viewModel.getDisplayAnswersLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean itemList) {
                 if(itemList){
                     displayAnswers(v);
                 }else{
                     hideAnswers(v);
                 }
            }
        });
        return v;
    }
    private void displayAnswers(View view){
        RadioButton redBtn = view.findViewById(radioButtonIds.get(currentQuestion.getCurrentAnswer()));
        redBtn.setTextColor(getResources().getColor(R.color.wrong_answer));
        RadioButton green = view.findViewById(radioButtonIds.get(currentQuestion.getRightAnswer()));
        green.setTextColor(getResources().getColor(R.color.right_answer));
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }
    private void hideAnswers(View view){

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(true);
            ((RadioButton)(radioGroup.getChildAt(i))).setTextColor(getResources().getColor(R.color.black));
        }
    }
}