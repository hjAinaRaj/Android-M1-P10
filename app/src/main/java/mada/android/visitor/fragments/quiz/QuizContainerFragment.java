package mada.android.visitor.fragments.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.quiz.Quiz;
import mada.android.models.quiz.QuizQuestion;
import mada.android.models.quiz.QuizViewModel;
import mada.android.services.QuizService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizContainerFragment extends Fragment {
    private QuizViewModel viewModel;
    private QuizService service;
    private LinearLayout quizContainerProgressBarLayout;
    private LinearLayout quizContainerPaginLayout;


    private static final String ARG_QUIZ_ID = "quizId";
    private Button previousButton;
    private Button nextButton;
    private String quizId;
    private ViewPager2 viewPager;
    private Space prevSpace;
    private Space nextSpace;

    public QuizContainerFragment() {
        service = new QuizService();
    }


    public static QuizContainerFragment newInstance(String quizId) {
        QuizContainerFragment fragment = new QuizContainerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUIZ_ID, quizId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizId = getArguments().getString(ARG_QUIZ_ID);
        }
        viewModel = new ViewModelProvider(getActivity()).get(QuizViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quizz_container, container, false);
        quizContainerProgressBarLayout = v.findViewById(R.id.quizContainerProgressBarLayout);
        quizContainerPaginLayout =  v.findViewById(R.id.quizContainerPaginLayout);

        prevSpace  = v.findViewById(R.id.quiz_prev_space);
        nextSpace  = v.findViewById(R.id.quiz_next_space);
        nextButton = v.findViewById(R.id.quizQuestionNextBtn);
        previousButton = v.findViewById(R.id.quizQuestionPrevBtn);
        previousButton.setVisibility( View.GONE );
        prevSpace.setVisibility( View.GONE );
        nextButton.setVisibility (View.GONE );
        nextSpace.setVisibility( View.GONE );
        try{
            Call<Quiz> call = service.get(quizId);
            call.enqueue(new Callback<Quiz>() {
                @Override
                public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                    if(response.code() != 200)
                        Toast.makeText(v.getContext(), "Quiz server error", Toast.LENGTH_SHORT).show();
                    else{
                        quizContainerProgressBarLayout.setVisibility(View.GONE);
                        viewModel.setCurrentQuiz(response.body());
                      loadQuiz(v);
                    }
                }

                @Override
                public void onFailure(Call<Quiz> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private void loadQuiz(View v){
        List<Fragment> fragments = new ArrayList<Fragment>(){{
            add(QuizIntroFragment.newInstance());

        }};
        int i = 0;
        for(QuizQuestion q : viewModel.getCurrentQuiz().getQuestions()){
            fragments.add(QuizQuestionFragment.newInstance(i));
            i++;
        }
        fragments.add(QuizResultsFragment.newInstance());


        viewPager = v.findViewById(R.id.quiz_view_pager);
        viewPager.setAdapter(new QuizPagerAdapter(this, fragments));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateButtonVisibility(position);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem > 0) {
                    viewPager.setCurrentItem(currentItem - 1, true);
                }
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < viewPager.getAdapter().getItemCount() - 1) {
                    viewPager.setCurrentItem(currentItem + 1, true);
                }
            }
        });
        viewPager.setUserInputEnabled(false);
        updateButtonVisibility(0);
        viewModel.setDisplayAnswers(false);
    }
    private void updateButtonVisibility(int currentPage) {

        previousButton.setVisibility(currentPage == 0 ? View.GONE : View.VISIBLE);
        prevSpace.setVisibility(currentPage == 0 ? View.GONE : View.VISIBLE);
        nextButton.setVisibility(currentPage == viewPager.getAdapter().getItemCount() - 1 ? View.GONE : View.VISIBLE);
        nextSpace.setVisibility(currentPage == viewPager.getAdapter().getItemCount() - 1 ? View.GONE : View.VISIBLE);
    }
    public class QuizPagerAdapter extends FragmentStateAdapter {
        private List<Fragment> fragments;

        public QuizPagerAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
            super(fragment);
            this.fragments = fragments;
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("debub", "--------------------------"+position);
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {

            return fragments.size();
        }
    }
}