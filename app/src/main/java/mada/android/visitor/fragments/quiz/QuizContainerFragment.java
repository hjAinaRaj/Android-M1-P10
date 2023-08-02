package mada.android.visitor.fragments.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

import mada.android.R;
import mada.android.models.quiz.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizContainerFragment extends Fragment {
    private QuizViewModel viewModel;



    private static final String ARG_QUIZ_ID = "quizId";
    private Button previousButton;
    private Button nextButton;
    private String quizId;
    private ViewPager2 viewPager;
    private Space prevSpace;
    private Space nextSpace;

    public QuizContainerFragment() {

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
        List<Fragment> fragments = new ArrayList<Fragment>(){{
            add(QuizIntroFragment.newInstance());
            add(QuizQuestionFragment.newInstance(0));
        }};

        prevSpace  = v.findViewById(R.id.quiz_prev_space);
        nextSpace  = v.findViewById(R.id.quiz_next_space);
        viewPager = v.findViewById(R.id.quiz_view_pager);
        viewPager.setAdapter(new QuizPagerAdapter(this, fragments));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateButtonVisibility(position);
            }
        });
        previousButton = v.findViewById(R.id.quizQuestionPrevBtn);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem > 0) {
                    viewPager.setCurrentItem(currentItem - 1, true);
                }
            }
        });

        nextButton = v.findViewById(R.id.quizQuestionNextBtn);
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
        return v;
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